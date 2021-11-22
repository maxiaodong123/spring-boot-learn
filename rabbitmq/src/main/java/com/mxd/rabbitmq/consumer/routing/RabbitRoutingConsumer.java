package com.mxd.rabbitmq.consumer.routing;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitRoutingConsumer {

    @RabbitListener(queues = RabbitConstant.ROUTING_FIRST_QUEUE_NAME)
    @RabbitHandler
    public void routingFirstQueueListener(String context) {
        System.out.println("rabbit routing queue first receiver: " + context);
    }

    @RabbitListener(queues = RabbitConstant.ROUTING_SECOND_QUEUE_NAME)
    @RabbitHandler
    public void routingSecondQueueListener(String context) {
        System.out.println("rabbit pubsub queue second receiver: " + context);
    }

    @RabbitListener(queues = RabbitConstant.ROUTING_THIRD_QUEUE_NAME)
    @RabbitHandler
    public void routingThirdQueueListener(String context) {
        System.out.println("rabbit pubsub queue third receiver: " + context);
    }

}
