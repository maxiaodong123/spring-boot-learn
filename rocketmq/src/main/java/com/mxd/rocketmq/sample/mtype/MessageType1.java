package com.mxd.rocketmq.sample.mtype;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

//普通消息类型
@Slf4j
public class MessageType1 {
    public static void main(String[] args) {
        //DefaultMQProducer用于发送非事务消息
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        //注册NameServer地址
        producer.setNamesrvAddr("192.168.31.103:9876");
        //异步发送失败后Producer自动重试2次
        producer.setRetryTimesWhenSendAsyncFailed(2);
        try {
            //启动生产者实例
            producer.start();
            //消息数据
            String data = "{\"title\":\"X市2021年度第四季度税务汇总数据\"}";
            //消息主题
            Message message = new Message("tax-data", "2021S4", "3333", data.getBytes());
            //发送结果
            SendResult result = producer.send(message);
            log.info("Broker响应：" + result);
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
