package com.mxd.rocketmq.sample.seqmsg;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

@Slf4j
public class SequenceMessageConsumer {
    public static void main(String[] args) throws Exception {
        // 声明并初始化一个 consumer
        // 需要一个 consumer group 名字作为构造方法的参数
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group");
        // 同样也要设置 NameServer 地址，须要与提供者的地址列表保持一致
        consumer.setNamesrvAddr("192.168.31.103:9876");
        // 设置 consumer 所订阅的 Topic 和 Tag，*代表全部的 Tag
        consumer.subscribe("order", "*");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 注册消息监听者，消费者端要增加MessageListenerOrderly监听器，用于实现有序队列
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext context) {
                //遍历输出
                list.forEach(msg -> {
                    log.info("{},{},{}", msg.getKeys(), new String(msg.getBody()), context.getMessageQueue());
                });
                //确认接收成功
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        // 启动消费者
        consumer.start();
        log.info("消费者启动成功，正在监听新消息");
    }
}
