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
public class SfAliConsumer {
    public static void main(String[] args) throws Exception {
        // 声明并初始化一个 consumer
        // 需要一个 consumer group 名字作为构造方法的参数
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("sf-ali-consumer-group");
        // 同样也要设置 NameServer 地址，须要与提供者的地址列表保持一致
        consumer.setNamesrvAddr("192.168.31.103:9876");
        //设置为广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
        //利用SQL WHERE子句写法对自定义属性进行过滤
        consumer.subscribe("sf-sample-data", MessageSelector.bySql("source in ('tmall' ,'taobao')"));
        // 注册消息监听者
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(msg -> {
                    log.info("id:{},source:{},data:{}", msg.getUserProperty("id"), msg.getUserProperty("source"), new String(msg.getBody()));
                });
                // 返回消费状态
                // CONSUME_SUCCESS 消费成功
                // RECONSUME_LATER 消费失败，需要稍后重新消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 调用 start() 方法启动 consumer
        consumer.start();
        log.info("广播消费者启动成功，正在监听新消息");
    }
}
