package com.mxd.rocketmq.sample.consumemode;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

//广播模式生产者
@Slf4j
public class CmProducer {
    public static void main(String[] args) {
        //DefaultMQProducer用于发送非事务消息
        DefaultMQProducer producer = new DefaultMQProducer("cm-producer-group");
        //注册NameServer地址
        producer.setNamesrvAddr("192.168.31.103:9876");
        //异步发送失败后Producer自动重试2次
        producer.setRetryTimesWhenSendAsyncFailed(2);
        try {
            //启动生产者实例
            producer.start();
            for (Integer i = 0; i < 10; i++) {
                //消息数据
                String data = "第" + i + "条消息数据";
                //消息主题
                Message message = new Message("cm-sample-data", "test", i.toString(), data.getBytes());
                //发送结果
                SendResult result = producer.send(message);
                log.info("Broker响应：" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭连接
                producer.shutdown();
                log.info("连接已关闭");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
