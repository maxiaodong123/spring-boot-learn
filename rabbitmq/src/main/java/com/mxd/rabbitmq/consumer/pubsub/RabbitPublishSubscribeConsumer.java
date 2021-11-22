package com.mxd.rabbitmq.consumer.pubsub;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitPublishSubscribeConsumer {

    @RabbitListener(queues = RabbitConstant.PUBLISH_SUBSCRIBE_FIRST_QUEUE_NAME)
    @RabbitHandler
    public void pubsubQueueFirst(String context) {
        System.out.println("rabbit pubsub queue first receiver: " + context);
    }

    @RabbitListener(queues = RabbitConstant.PUBLISH_SUBSCRIBE_SECOND_QUEUE_NAME)
    @RabbitHandler
    public void pubsubQueueSecond(String context) {
        System.out.println("rabbit pubsub queue second receiver: " + context);
    }

}
