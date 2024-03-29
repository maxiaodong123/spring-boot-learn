package com.mxd.rabbitmq.consumer.confirm;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
public class RabbitConfirmConsumer1 {

    private int retryNum = 5;

    private int currentNum = 0;


    @RabbitHandler
    public void processHandler(String msg, Channel channel, Message message) throws IOException {

        try {
            log.info("消费者 2 号收到：{}", msg);

            int a = 1 / 0;
            // int a = 1 / 0; 模拟异常
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {
            log.error("currentNum----------------" + currentNum);
            if (currentNum < retryNum) {
                // 异常拒绝签收，让mq重发此信息
                //deliveryTag -接收到的标签AMQP.Basic.GetOk或AMQP.Basic.Deliver
                //multiple - true拒绝所有消息，包括提供的发送标签;False表示仅拒绝提供的投递标签。
                //Requeue -如果被拒绝的消息应该被Requeue而不是被丢弃/死信，则为true
                log.error("第{}次拒绝一个或多个接收到的消息, 消息内容为：{}", currentNum, msg);
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                // 该信息丢了，但是不需要你重发
                // channel.basicNack(message.getMessageProperties().getDeliveryTag(),true,false);

            }
            if (currentNum == retryNum) {
//                e.printStackTrace();
                log.error("消息已5次处理失败,拒绝再次接收，msg :{}", msg);
                //拒绝一个消息。从AMQP.Basic.GetOk或AMQP.Basic.Deliver方法中提供deliveryTag，其中包含被拒绝的接收消息。
                //Params: deliveryTag -从收到的AMQP.Basic.GetOk或AMQP.Basic.Deliver requeue -如果被拒绝的消息应该被重新队列而不是被丢弃/死信，则为true
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            }
            currentNum++;
        }
    }

//    @RabbitHandler
//    public void processHandler(CorrelationData correlationData , String msg, Channel channel, Message message) throws IOException {
//
//        try {
//            log.info("消费者 2 号收到：{}", msg);
//
//            String correlationId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
//
//            System.out.println(correlationId);
//
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//
//        } catch (Exception e) {
//
//        }
//    }
}
