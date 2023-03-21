package com.mxd.rocketmq.sample.tagfilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

@Slf4j
public class TfJDConsumer {

    public static void main(String[] args) throws Exception {

        // 声明并初始化一个 consumer
        // 需要一个 consumer group 名字作为构造方法的参数
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tf-jd-consumer-group");

        // 同样也要设置 NameServer 地址，须要与提供者的地址列表保持一致
        consumer.setNamesrvAddr("192.168.31.103:9876");

        //设置为集群模式
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置 consumer 所订阅的 Topic 和 Tag，*代表全部的 Tag
        consumer.subscribe("tf-sample-data", "jd");
        // 注册消息监听者
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(msg -> {
                    log.info(msg.getTags() + ":" + new String(msg.getBody()));
                });

                // 返回消费状态
                // CONSUME_SUCCESS 消费成功
                // RECONSUME_LATER 消费失败，需要稍后重新消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 调用 start() 方法启动 consumer
        consumer.start();
        log.info("集群消费者启动成功，正在监听新消息");
    }
}
