package com.jaagro.gateway.service;

import com.jaagro.gateway.model.UserLoginDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author tony
 */
@FeignClient(name = "report")
public interface ReportClientService {

    /**
     * 创建登录日志记录
     * @param userLoginDto
     */
    @PostMapping("/userLogin")
    void createUserLogin(UserLoginDto userLoginDto);
}
