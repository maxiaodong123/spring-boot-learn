package com.mxd.rabbitmq.consumer.dlx;

import com.mxd.rabbitmq.constant.RabbitConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * @Author: mxd
 * @Description: 死信消息消费者
 * @Date: 2021/11/23 15:42
 */
@Slf4j
@Component
public class RabbitmqDlxConsumer {

    @RabbitListener(queues = RabbitConstant.NORMAL_QUEUE_NAME,concurrency = "1")
    @RabbitHandler
    public void msgConsumer(String msg, Channel channel, Message message) throws IOException {
        log.info("正常队列接收到消息>>>{}",msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("拒绝消息， 使之进入死信队列");
        System.out.println("时间： " + new Date());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {

        }

        // basicReject第二个参数为false进入死信队列或丢弃
        channel.basicReject(deliveryTag, false);
    }


    @RabbitListener(queues = RabbitConstant.DLX_QUEUE_NAME,concurrency = "1")
    @RabbitHandler
    public void dxlMsgConsumer(String msg, Channel channel, Message message) throws IOException {
        log.info("死信队列接收到消息>>>{}",msg);
        if(msg.indexOf("0")>-1){
            throw new RuntimeException("抛出异常");
        }
        log.info("消息{}消费成功",msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
