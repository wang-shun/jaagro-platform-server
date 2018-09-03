package com.jaagro.bus.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ的配置类，用来配队列、交换器、路由等高级信息
 * @author tony
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue helloConfig(){
        return new Queue("hello");
    }
}
