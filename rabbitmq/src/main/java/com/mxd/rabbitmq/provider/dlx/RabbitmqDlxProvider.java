package com.mxd.rabbitmq.provider.dlx;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: mxd
 * @Description: 死信队列生产者
 * @Date: 2021/11/23 14:03
 */
@Configuration
public class RabbitmqDlxProvider {


    /**
     * DLX 交换机
     * @return
     */
    @Bean
    public Exchange dlxExchange(){
        return ExchangeBuilder
                .directExchange(RabbitConstant.DLX_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**
     * DLX 队列
     * @return
     */
    @Bean
    public Queue dlxQueue(){
        return QueueBuilder
                .durable(RabbitConstant.DLX_QUEUE_NAME)
                .build();
    }

    /**
     * NORMAL 交换机
     * @return
     */
    @Bean
    public Exchange Exchange(){
        return ExchangeBuilder
                .directExchange(RabbitConstant.NORMAL_EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**
     * NORMAL 队列
     * @return
     */
    @Bean
    public Queue QueueA(){
        return QueueBuilder
                .durable(RabbitConstant.NORMAL_QUEUE_NAME)
                //设置 死信交换机
                .withArgument(RabbitConstant.X_DEAD_LETTER_EXCHANGE,RabbitConstant.DLX_EXCHANGE_NAME)
                .withArgument(RabbitConstant.X_DEAD_LETTER_ROUTING_KEY,RabbitConstant.DLX_ROUTING_KEY)
                //设置 队列所有消息 存活时间8秒
                .withArgument(RabbitConstant.X_MESSAGE_TTL,8000)
                //设置 队列最大长度 10条
                .withArgument(RabbitConstant.X_MAX_LENGTH,10)
                .build();
    }

    /**
     * 绑定 DLX队列 和 DLX交换机
     * @return
     */
    @Bean
    public Binding dlxBinding(){
        return new Binding(RabbitConstant.DLX_QUEUE_NAME,
                Binding.DestinationType.QUEUE,RabbitConstant.DLX_EXCHANGE_NAME,
                RabbitConstant.DLX_ROUTING_KEY,
                null);
    }

    /**
     * 绑定 NORMAL队列 和 NORMAL交换机
     * @return
     */
    @Bean
    public Binding normalBindingA(){
        return new Binding(RabbitConstant.NORMAL_QUEUE_NAME,
                Binding.DestinationType.QUEUE,
                RabbitConstant.NORMAL_EXCHANGE_NAME,
                RabbitConstant.NORMAL_ROUTING_KEY,
                null);
    }

}
