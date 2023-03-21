package com.mxd.rocketmq.sample.sqlfilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.Random;

//消息过滤案例生产者
@Slf4j
public class SfProducer {
    public static void main(String[] args) {
        //DefaultMQProducer用于发送非事务消息
        DefaultMQProducer producer = new DefaultMQProducer("sf-producer-group");
        //注册NameServer地址
        producer.setNamesrvAddr("192.168.31.103:9876");
        /*//异步发送失败后Producer自动重试2次
        producer.setRetryTimesWhenSendAsyncFailed(2);*/
        try {
            //启动生产者实例
            producer.start();
            for (Integer i = 0; i < 10; i++) {
                Thread.sleep(1000);
                Integer rnd = new Random().nextInt(10);
                //用户自定义属性
                String source = "";
                switch (rnd % 3) {
                    case 0:
                        source = "jd";
                        break;
                    case 1:
                        source = "tmall";
                        break;
                    case 2:
                        source = "taobao";
                        break;
                }
                //消息数据
                String data = "第" + i + "条消息数据";
                //消息主题，使用用户自定义属性时可以不设置tag与key
                Message message = new Message("sf-sample-data", data.getBytes());
                message.putUserProperty("id", i.toString());
                message.putUserProperty("source", source);
                //发送结果
                SendResult result = producer.send(message);
                log.info("id:{},source:{},data:{}", i.toString(), source, data);
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
