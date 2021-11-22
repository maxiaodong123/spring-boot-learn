package com.mxd.rabbitmq.consumer.simple;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitSimpleConsumer {

    @RabbitHandler
    @RabbitListener(queues = RabbitConstant.SIMPLE_QUEUE_NAME)
    public void simpleListener(String context) {
        System.out.println("rabbit receiver: " + context);
    }

}
