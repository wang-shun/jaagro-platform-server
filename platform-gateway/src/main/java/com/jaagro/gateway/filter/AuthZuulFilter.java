package com.jaagro.gateway.filter;

import com.jaagro.gateway.service.TokenClientService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tony
 */
public class AuthZuulFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthZuulFilter.class);

    @Autowired
    private TokenClientService tokenClient;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getHeader("token");
        String currentURI = request.getRequestURI();
        String tokenURI = "/auth/token";
        String swaggerURI = "/v2/api-docs";
        String verificationCodeURI = "/sendMessage";

        //放行条件
        boolean isPass = currentURI.equals(tokenURI) ||
                currentURI.contains(verificationCodeURI) ||
                currentURI.contains(swaggerURI) ||
                tokenClient.verifyToken(token);
        //放行
        if(isPass){
            //对改请求进行路由
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);
            //设值，让其他filter看到这个状态
            log.info(currentURI + "验证通过");
            ctx.set("isSuccess", true);
            return null;
        }else{
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            log.warn(currentURI + "：401，令牌无效");
            ctx.setResponseBody("{\"code\":401,\"msg\":\"令牌无效\",\"data\":\"null\"}");
            //设置返回值json格式
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            ctx.set("isSuccess", false);
            return null;
        }
    }
}
