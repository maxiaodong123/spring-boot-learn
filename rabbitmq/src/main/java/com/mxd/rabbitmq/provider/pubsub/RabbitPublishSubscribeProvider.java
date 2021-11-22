package com.mxd.rabbitmq.provider.pubsub;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPublishSubscribeProvider {

    @Bean
    public Queue pubsubQueueFirst() {
        return new Queue(RabbitConstant.PUBLISH_SUBSCRIBE_FIRST_QUEUE_NAME);
    }

    @Bean
    public Queue pubsubQueueSecond() {
        return new Queue(RabbitConstant.PUBLISH_SUBSCRIBE_SECOND_QUEUE_NAME);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        // 创建fanout类型交换机，表示与此交换机会将消息发送给所有绑定的队列
        return new FanoutExchange(RabbitConstant.PUBLISH_SUBSCRIBE_EXCHANGE_NAME);
    }

    @Bean
    public Binding pubsubQueueFirstBindFanoutExchange() {
        // 队列一绑定交换机
        return BindingBuilder.bind(pubsubQueueFirst()).to(fanoutExchange());
    }

    @Bean
    public Binding pubsubQueueSecondBindFanoutExchange() {
        // 队列二绑定交换机
        return BindingBuilder.bind(pubsubQueueSecond()).to(fanoutExchange());
    }

}
