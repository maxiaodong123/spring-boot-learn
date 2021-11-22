package com.mxd.rabbitmq.provider.header;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitHeaderProvider {

    @Bean
    public Queue headerFirstQueue() {
        return new Queue(RabbitConstant.HEADER_FIRST_QUEUE_NAME);
    }

    @Bean
    public Queue headerSecondQueue() {
        return new Queue(RabbitConstant.HEADER_SECOND_QUEUE_NAME);
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(RabbitConstant.HEADER_EXCHANGE_NAME);
    }

    @Bean
    public Binding headerFirstQueueBindExchange() {
        Map<String, Object> headersMap = new HashMap<>(8);
        headersMap.put("matchAll", "YES");
        headersMap.put("hello", "world");

        return BindingBuilder.bind(headerFirstQueue()).to(headersExchange()).whereAll(headersMap).match();
    }

    @Bean
    public Binding headerSecondQueueBindExchange() {
        Map<String, Object> headersMap = new HashMap<>(8);
        headersMap.put("matchAll", "NO");
        headersMap.put("hello", "world");

        return BindingBuilder.bind(headerSecondQueue()).to(headersExchange()).whereAny(headersMap).match();
    }

}
