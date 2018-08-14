package com.jaagro.gateway.config;

import com.jaagro.gateway.filter.AuthZuulFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tony
 */
@Configuration
public class ZuulFilterConfig {

    @Bean
    public AuthZuulFilter authZuulFilter(){
        return new AuthZuulFilter();
    }
}
