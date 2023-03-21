package com.mxd.rocketmq.sample.pull;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class PullProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("pg1");
        producer.setNamesrvAddr("192.168.31.103:9876");
        try {
            producer.start();
            for (int i = 1; i <= 100; i++) {
                String data = "id=" + i + "税务数据";
                Message message = new Message("tax-data", data.getBytes());
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
