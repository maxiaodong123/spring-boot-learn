package com.mxd.rocketmq.sample.seqmsg;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

@Slf4j
//发送分区顺序消息
public class SequenceMessageProvider {
    public static void main(String[] args) {
        //前置准备代码
        DefaultMQProducer producer = new DefaultMQProducer("producer-group");
        producer.setNamesrvAddr("192.168.31.103:9876");
        try {
            producer.start();
            //模拟10笔订单
            for (Integer orderId = 1; orderId <= 10; orderId++) {
                //每笔订单要发送3条消息：(1)创建订单 (2)订单库存扣减 (3)加积分
                for (int i = 0; i < 3; i++) {
                    String data = "";
                    switch (i % 3) {
                        case 0:
                            data = orderId + "号创建订单";
                            break;
                        case 1:
                            data = orderId + "号订单减库存";
                            break;
                        case 2:
                            data = orderId + "号订单加积分";
                            break;
                    }
                    //创建消息对象 topic="order",tags="order",key=orderId
                    Message message = new Message("order", "order", orderId.toString(), data.getBytes("UTF-8"));
                    //发送消息，实现MessageQueueSelector接口
                    SendResult result = producer.send(message, new MessageQueueSelector() {
                        //select方法决定向broker哪一个队列发送消息
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            int orderId = Integer.parseInt(msg.getKeys());
                            int index = orderId % mqs.size();
                            MessageQueue messageQueue = mqs.get(index);
                            log.info("id:{},data:{},queue:{}", orderId, new String(msg.getBody()), messageQueue);
                            return messageQueue;
                        }
                    }, null);
                }
            }
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
