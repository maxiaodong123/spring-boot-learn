package com.mxd.rabbitmq.consumer.header;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitHeaderConsumer {

    @RabbitListener(queues = RabbitConstant.HEADER_FIRST_QUEUE_NAME)
    @RabbitHandler
    public void headerFirstQueue(String context) {
        System.out.println("rabbit header queue first receiver: " + context);
    }

    @RabbitListener(queues = RabbitConstant.HEADER_SECOND_QUEUE_NAME)
    @RabbitHandler
    public void headerSecondQueue(String context) {
        System.out.println("rabbit header queue second receiver: " + context);
    }

}
