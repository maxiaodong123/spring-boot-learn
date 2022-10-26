package com.mxd.rabbitmq.consumer.confirm;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: mxd
 * @Description: 消息确认
 * @Date: 2021/11/22 17:52
 */
@Slf4j
@Component
@RabbitListener(queues = "confirm_test_queue")
public class RabbitConfirmConsumer {

    @RabbitHandler
    public void processHandler(String msg, Channel channel, Message message) throws IOException {

        try {
            log.info("消费者 1 号收到：{}", msg);

            //TODO 具体业务
            // 正常签收，mq收到此消息被正常签收后即可从队列中删除vi信息
            // 是哟了那个channel的方法
            // 第一个参数是deliverytag 标识哪条信息 第二个参数是是否批量签收
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("msg：{} 已被消费", msg);
        }  catch (Exception e) {

            if (message.getMessageProperties().getRedelivered()) {

                log.error("消息已重复处理失败,拒绝再次接收...11111");
                //拒绝一个消息。从AMQP.Basic.GetOk或AMQP.Basic.Deliver方法中提供deliveryTag，其中包含被拒绝的接收消息。
                //Params: deliveryTag -从收到的AMQP.Basic.GetOk或AMQP.Basic.Deliver requeue -如果被拒绝的消息应该被重新队列而不是被丢弃/死信，则为true
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {

                log.error("消息即将再次返回队列处理...");

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}
