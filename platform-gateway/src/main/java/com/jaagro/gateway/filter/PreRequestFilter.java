package com.jaagro.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.jaagro.constant.UserInfo;
import com.jaagro.gateway.config.RabbitMqConfig;
import com.jaagro.gateway.model.UserLoginDto;
import com.jaagro.gateway.service.TokenClientService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author tony
 */
@Component
public class PreRequestFilter extends ZuulFilter {
    private static final Logger LOG = LoggerFactory.getLogger(PreRequestFilter.class);

    @Autowired
    private TokenClientService tokenClientService;
    @Autowired
    private AmqpTemplate rabbitMqTemplate;

    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        //int值来定义过滤器的执行顺序，数值越小优先级越高
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //将请求头中token放在zuulRequest中
        String token = request.getHeader("token");
        ctx.addZuulRequestHeader("token", token);
        LOG.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());
        //插入登录记录
        UserInfo userInfo = tokenClientService.getUserByToken(token);
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto
                .setLoginDate(new Date())
                .setUserId(userInfo.getId())
                .setUserName(userInfo.getName())
                .setUserType(userInfo.getUserType())
                .setLoginIp(request.getRemoteAddr());
        String userLoginJson = JSON.toJSONString(userLoginDto);
        rabbitMqTemplate.convertAndSend(RabbitMqConfig.TOPIC_EXCHANGE, "userLogin.send.queue", userLoginJson);
        return null;
    }
}
