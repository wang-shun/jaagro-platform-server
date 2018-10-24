package com.jaagro.gateway.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author tony
 */
@FeignClient("auth")
public interface TokenClientService {

    /**
     * 验证token是否有效
     * @param token token
     * @return 是否有效 布尔
     */
    @PostMapping("/verifyToken")
    boolean verifyToken(@RequestParam("token") String token);

    /**
     * 延长token有效期
     * @param token
     * @return
     */
    @PostMapping("/postponeToken")
    boolean postponeToken(@RequestParam("token") String token);
}
