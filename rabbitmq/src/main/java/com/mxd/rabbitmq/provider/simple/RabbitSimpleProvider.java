package com.mxd.rabbitmq.provider.simple;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitSimpleProvider {

    @Bean
    public Queue simpleQueue() {
        return new Queue(RabbitConstant.SIMPLE_QUEUE_NAME);
    }

}
