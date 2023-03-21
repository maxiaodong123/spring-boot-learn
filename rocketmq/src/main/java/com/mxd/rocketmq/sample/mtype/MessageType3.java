package com.mxd.rocketmq.sample.mtype;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

//发送延迟消息
public class MessageType3 {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.31.103:9876");
        producer.setRetryTimesWhenSendAsyncFailed(2);
        try {
            producer.start();
            long id = 4466l;

            String data = "{\"id\":" + id + " , + \"title\":\"X市2021年度第四季度税务汇总数据\"}";
            Message message = new Message("tax-data", "2021S4", data.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(5);
            SendResult result = producer.send(message);
            System.out.println("消息已发送：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                producer.shutdown();
                System.out.println("连接已关闭");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
