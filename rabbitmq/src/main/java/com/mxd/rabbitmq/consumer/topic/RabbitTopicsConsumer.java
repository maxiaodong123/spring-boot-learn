package com.mxd.rabbitmq.consumer.topic;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicsConsumer {

    @RabbitListener(queues = RabbitConstant.TOPICS_FIRST_QUEUE_NAME)
    @RabbitHandler
    public void topicFirstQueue(String context) {
        System.out.println("rabbit topics queue first receiver: " + context);
    }

    @RabbitListener(queues = RabbitConstant.TOPICS_SECOND_QUEUE_NAME)
    @RabbitHandler
    public void topicSecondQueue(String context) {
        System.out.println("rabbit topics queue second receiver: " + context);
    }

    @RabbitListener(queues = RabbitConstant.TOPICS_THIRD_QUEUE_NAME)
    @RabbitHandler
    public void topicThirdQueue(String context) {
        System.out.println("rabbit topics queue third receiver: " + context);
    }

}
