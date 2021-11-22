package com.mxd.rabbitmq.provider.routing;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitRoutingProvider {

    @Bean
    public Queue rabbitRoutingFirstQueue() {
        return new Queue(RabbitConstant.ROUTING_FIRST_QUEUE_NAME);
    }

    @Bean
    public Queue rabbitRoutingSecondQueue() {
        return new Queue(RabbitConstant.ROUTING_SECOND_QUEUE_NAME);
    }

    @Bean
    public Queue rabbitRoutingThirdQueue() {
        return new Queue(RabbitConstant.ROUTING_THIRD_QUEUE_NAME);
    }

    @Bean
    public DirectExchange directExchange() {
        // 创建direct类型交换机，表示与此交换机会将消息发送给 routing_key 完全相同的队列
        return new DirectExchange(RabbitConstant.ROUTING_EXCHANGE_NAME);
    }

    @Bean
    public Binding routingFirstQueueBindDirectExchange() {
        // 队列一绑定direct交换机，并设置 routing_key 为 routing_first_queue_routing_key
        return BindingBuilder.bind(rabbitRoutingFirstQueue()).to(directExchange()).with(RabbitConstant.ROUTING_FIRST_QUEUE_ROUTING_KEY_NAME);
    }

    @Bean
    public Binding routingSecondQueueBindDirectExchange() {
        // 队列二绑定direct交换机，并设置 routing_key 为 routing_second_queue_routing_key
        return BindingBuilder.bind(rabbitRoutingSecondQueue()).to(directExchange()).with(RabbitConstant.ROUTING_SECOND_QUEUE_ROUTING_KEY_NAME);
    }

    @Bean
    public Binding routingThirdQueueBindDirectExchange() {
        // 队列三绑定direct交换机，并设置 routing_key 为 routing_third_queue_routing_key
        return BindingBuilder.bind(rabbitRoutingThirdQueue()).to(directExchange()).with(RabbitConstant.ROUTING_THIRD_QUEUE_ROUTING_KEY_NAME);
    }

}
