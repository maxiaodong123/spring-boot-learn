package com.mxd.rocketmq.sample.pull;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
//批量发送消息
public class BatchPullProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("pg1");
        producer.setNamesrvAddr("192.168.31.103:9876");
        try {
            producer.start();

            for (int i = 0; i < 10; i++) {
                List<Message> messages = new ArrayList<>();
                for (int j = 0; j < 100; j++) {
                    String data = "id=" + (i * 10 + j) + "税务数据";
                    Message message = new Message("tax-data", data.getBytes());
                    messages.add(message);
                }
                SendResult result = producer.send(messages);
                log.info("{},{}", i, result.toString());
                Thread.sleep(100);
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
