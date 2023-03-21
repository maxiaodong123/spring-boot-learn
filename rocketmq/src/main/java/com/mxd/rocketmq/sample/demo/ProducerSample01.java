package com.mxd.rocketmq.sample.demo;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class ProducerSample01 {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("pg1");
        producer.setNamesrvAddr("192.168.31.103:9876");
        try {
            producer.start();
            for (int i = 0; i < 10000; i++) {
                String data = "{\"title\":\"X市2021年度第四季度税务汇总数据\"}";
                Message message = new Message("tax-data", "2021S4", data.getBytes());
                SendResult result = producer.send(message);
                System.out.println("消息已发送：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
            }
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                producer.shutdown();
                System.out.println("链接已关闭");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
