package com.mxd.rabbitmq.consumer.dlx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author mxd
 * @Date 2022/7/4 9:54
 * @Version 1.0
 * @Description 延时队列之rabbitmq_delayed_message_exchange插件方式
 */
@Slf4j
@Component
public class RabbitmqDelayedConsumer {

    @RabbitListener(queues = "test_queue_1")
    public void receive(String msg) {
        System.out.println("消息接收时间:" + LocalDateTime.now());
        System.out.println("接收到的消息:" + msg);
    }
}
