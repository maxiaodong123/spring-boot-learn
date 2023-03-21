package com.mxd.rocketmq.sample.sqlfilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

@Slf4j
public class SfJDConsumer {
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sf-jd-consumer-group");
        consumer.setNamesrvAddr("192.168.31.103:9876");
        consumer.setMessageModel(MessageModel.CLUSTERING);
        //利用SQL WHERE子句写法对自定义属性进行过滤
        consumer.subscribe("sf-sample-data", MessageSelector.bySql("source='jd'"));
        // 注册消息监听者
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(msg -> {
                    log.info("id:{},source:{},data:{}", msg.getUserProperty("id"), msg.getUserProperty("source"), new String(msg.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        log.info("集群消费者启动成功，正在监听新消息");
    }
}
