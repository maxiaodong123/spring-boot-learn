package com.mxd.rabbitmq.provider.topic;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopicProvider {

    @Bean
    public Queue topicFirstQueue() {
        return new Queue(RabbitConstant.TOPICS_FIRST_QUEUE_NAME);
    }

    @Bean
    public Queue topicSecondQueue() {
        return new Queue(RabbitConstant.TOPICS_SECOND_QUEUE_NAME);
    }

    @Bean
    public Queue topicThirdQueue() {
        return new Queue(RabbitConstant.TOPICS_THIRD_QUEUE_NAME);
    }

    @Bean
    public TopicExchange topicExchange() {
        // 创建topic类型交换机，表示与此交换机会将消息发送给 routing_key 通配符匹配成功的队列
        return new TopicExchange(RabbitConstant.TOPICS_EXCHANGE_NAME);
    }

    @Bean
    public Binding topicFirstQueueBindExchange() {
        // 队列一绑定topic类型交换机，并设置 routing_key 通配符为 #.first.#
        return BindingBuilder.bind(topicFirstQueue()).to(topicExchange()).with(RabbitConstant.TOPICS_ROUTING_KEY_FIRST_WILDCARD);
    }

    @Bean
    public Binding topicSecondQueueBindExchange() {
        // 队列二绑定topic类型交换机，并设置 routing_key 通配符为 *.second.#
        return BindingBuilder.bind(topicSecondQueue()).to(topicExchange()).with(RabbitConstant.TOPICS_ROUTING_KEY_SECOND_WILDCARD);
    }

    @Bean
    public Binding topicThirdQueueBindExchange() {
        // 队列三绑定topic类型交换机，并设置 routing_key 通配符为 *.third.*
        return BindingBuilder.bind(topicThirdQueue()).to(topicExchange()).with(RabbitConstant.TOPICS_ROUTING_KEY_THRID_WILDCARD);
    }

}
