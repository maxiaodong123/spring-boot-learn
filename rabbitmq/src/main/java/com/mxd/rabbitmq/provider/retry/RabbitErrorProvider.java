package com.mxd.rabbitmq.provider.retry;

import com.mxd.rabbitmq.constant.RabbitConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.ImmediateRequeueMessageRecoverer;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: mxd
 * @Description: 异常消息处理队列
 * @Date: 2021/11/23 14:23
 */
@Configuration
public class RabbitErrorProvider {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public DirectExchange errorExchange(){
        return new DirectExchange(RabbitConstant.ERROR_EXCHANGE_NAME,true,false);
    }

    @Bean
    public Queue errorQueue(){
        return new Queue(RabbitConstant.ERROR_QUEUE_NAME, true);
    }

    @Bean
    public Binding errorBinding(Queue errorQueue, DirectExchange errorExchange){
        return BindingBuilder.bind(errorQueue).to(errorExchange).with(RabbitConstant.ERROR_ROUTING_KEY);
    }

    /**
     * 重新发布消息
     * 消息重试5次以后直接以新的routingKey发送到了配置的交换机中，此时再查看监控页面，可以看原始队列中已经没有消息了，但是配置的异常队列中存在一条消息。
     * 对于重试之后仍然异常的消息，可以采用RepublishMessageRecoverer，将消息发送到其他的队列中，再专门针对新的队列进行处理。
     * @return
     */
    @Bean
    public MessageRecoverer messageRecoverer(){
        return new RepublishMessageRecoverer(rabbitTemplate,RabbitConstant.ERROR_EXCHANGE_NAME,RabbitConstant.ERROR_ROUTING_KEY);
    }

    /**
     * 立即重新返回队列
     * 重试5次之后，返回队列，然后再重试5次，周而复始直到不抛出异常为止，这样还是会影响后续的消息消费。
     * @return
     */
    @Bean
    public MessageRecoverer immediateRequeueMessageRecoverer(){
        return new ImmediateRequeueMessageRecoverer();
    }
}
