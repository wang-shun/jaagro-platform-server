package com.jaagro.gateway.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tony
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 日志插入队列
     */
    public static final String USER_LOGIN_SEND_QUEUE = "userLogin.send.queue";

    public static final String TOPIC_EXCHANGE = "topic.exchange";
    public static final String FANOUT_EXCHANGE = "fanout.exchange";

    @Bean
    public Queue userLoginSendQueue() {
        return new Queue(USER_LOGIN_SEND_QUEUE, true);
    }

    /**
     * Topic模式
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    /**
     * Fanout模式
     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Binding userLoginSendBindingTopic() {
        return BindingBuilder.bind(userLoginSendQueue()).to(topicExchange()).with("location.send");
    }
}
