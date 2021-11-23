package com.mxd.rabbitmq.provider.retry;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitRetryProvider {

    @Bean
    public Queue retryQueueName() {
        return new Queue(RabbitConstant.RETRY_QUEUE_NAME);
    }


    @Bean
    public FanoutExchange fanoutRetryExchange() {
        // 创建fanout类型交换机，表示与此交换机会将消息发送给所有绑定的队列
        return new FanoutExchange(RabbitConstant.RETRY_EXCHANGE_NAME);
    }

    @Bean
    public Binding bindRetryFanoutExchange() {

        return BindingBuilder.bind(retryQueueName()).to(fanoutRetryExchange());
    }

}
