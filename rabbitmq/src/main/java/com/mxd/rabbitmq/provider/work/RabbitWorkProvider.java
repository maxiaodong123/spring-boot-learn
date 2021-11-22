package com.mxd.rabbitmq.provider.work;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitWorkProvider {

    @Bean
    public Queue workQueue() {
        return new Queue(RabbitConstant.WORK_QUEUE_NAME);
    }

}
