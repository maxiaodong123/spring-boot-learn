package com.mxd.rabbitmq.provider.rpc;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitRpcProvider {

    @Bean
    public Queue rpcQueue() {
        return new Queue(RabbitConstant.RPC_QUEUE_NAME);
    }

}
