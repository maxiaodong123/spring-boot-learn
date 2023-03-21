package com.mxd.rocketmq.sample.pull;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

@Slf4j
public class PullConsumer {
    public static void main(String[] args) throws Exception {
        //采用DefaultLitePullConsumer 对象拉取消息
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("consumer-group");
        //注册NameServer地址
        consumer.setNamesrvAddr("192.168.31.103:9876");
        //订阅主题
        consumer.subscribe("tax-data", "*");
        consumer.setPullBatchSize(100);
        // 启动消费者
        consumer.start();
        log.info("消费者启动成功，正在监听新消息");
        int i = 0;
        while (true) {
            ++i;
            //调用poll方法拉取未消费数据
            List<MessageExt> list = consumer.poll();
            int j = 0;
            if (list != null && list.size() > 0) {
                for (MessageExt ext : list) {
                    j++;
                    log.info("{}-{},{},{},{}", String.valueOf(i), String.valueOf(j), "Queue-" + ext.getQueueId(), ext.getQueueOffset(), new String(ext.getBody()));
                }
            }
        }
    }
}
