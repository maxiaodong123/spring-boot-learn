package com.mxd.rabbitmq.consumer.rpc;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitRpcConsumer {

    @RabbitListener(queues = RabbitConstant.RPC_QUEUE_NAME)
    @RabbitHandler
    public String rpcQueue(String context) {
        System.out.println("rabbit rpc queue receiver: " + context);
        return "copy that!";
    }

}
