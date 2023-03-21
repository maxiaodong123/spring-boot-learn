package com.mxd.rocketmq.sample.mtype;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

@Slf4j
//发送分区顺序消息
public class MessageType2 {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.31.103:9876");
        producer.setRetryTimesWhenSendAsyncFailed(2);
        try {
            producer.start();
            Integer id = 4465;
            String data = "{\"id\":" + id + " , + \"title\":\"X市2021年度第四季度税务汇总数据\"}";
            Message message = new Message("tax-data", "2021S4", id.toString(), data.getBytes(RemotingHelper.DEFAULT_CHARSET));
            //分区有序消息最大的区别便是调用send方法是，需要实现MessageQueueSelector接口，确定使用哪个队列投递消息
            SendResult result = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    log.info("当前队列数量：" + mqs.size() + ",明细：" + mqs.toString());
                    log.info("Message对象：" + msg.toString());
                    int dataId = Integer.parseInt(msg.getKeys());
                    int index = dataId % mqs.size();
                    MessageQueue messageQueue = mqs.get(0);
                    log.info("分区队列:" + messageQueue);
                    return messageQueue;
                }
            }, null);

            log.info("消息已发送：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
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
