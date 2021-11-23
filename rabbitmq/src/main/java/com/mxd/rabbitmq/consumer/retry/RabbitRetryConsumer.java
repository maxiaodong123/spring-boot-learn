package com.mxd.rabbitmq.consumer.retry;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: mxd
 * @Description: 消息重试
 * @Date: 2021/11/23 11:38
 */
@Slf4j
@Component
@RabbitListener(queues = "retry_queue")
public class RabbitRetryConsumer {

//    @RabbitHandler
//    public void processHandler(String msg, Channel channel, Message message) throws IOException {
//        重试并不是RabbitMQ重新发送了消息，仅仅是消费者内部进行的重试，换句话说就是重试跟mq没有任何关系；
//        因此上述消费者代码不能添加try{}catch(){}，一旦捕获了异常，在自动ack模式下，就相当于消息正确处理了，消息直接被确认掉了，不会触发重试的；
//        try {
//            log.info("retry接收到消息>>>{}",msg);
//            int temp = 10/0;
//            log.info("retry消息{}消费成功",msg);
//
//        }  catch (Exception e) {
//
//            log.error("retry消息{}消费异常",e.getMessage());
//
//        }
//    }

    @RabbitHandler
    public void processHandler1(String msg, Channel channel, Message message) throws IOException {
//        重试并不是RabbitMQ重新发送了消息，仅仅是消费者内部进行的重试，换句话说就是重试跟mq没有任何关系；
//        因此上述消费者代码不能添加try{}catch(){}，一旦捕获了异常，在自动ack模式下，就相当于消息正确处理了，消息直接被确认掉了，不会触发重试的；

        log.info("retry接收到消息>>>{}",msg);
        int temp = 10/0;
        log.info("retry消息{}消费成功",msg);


    }
}
