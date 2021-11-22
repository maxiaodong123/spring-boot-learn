package com.mxd.rabbitmq.consumer.work;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitWorkConsumer {

    @RabbitListener(queues = RabbitConstant.WORK_QUEUE_NAME)
    @RabbitHandler
    public void workQueueListenerFirst(String context) {
        System.out.println("rabbit workQueue listener first receiver: " + context);
    }

    @RabbitListener(queues = RabbitConstant.WORK_QUEUE_NAME)
    @RabbitHandler
    public void workQueueListenerSecond(String context) {
        System.out.println("rabbit workQueue listener second receiver: " + context);
    }

}
