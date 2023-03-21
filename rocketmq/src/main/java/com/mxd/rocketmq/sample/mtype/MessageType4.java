package com.mxd.rocketmq.sample.mtype;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Data
@Slf4j
public class MessageType4 {
    public static void main(String[] args) throws MQClientException, InterruptedException, UnsupportedEncodingException {

        //事务消息一定要使用TransactionMQProducer事务生产者创建
        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name1");
        //从NameServer获取配置数据
        producer.setNamesrvAddr("192.168.31.103:9876");

        //CachedThreadPool线程池用于回查事务数据
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("check-transaction-thread");
                return thread;
            }
        });
        //将生产者与线程池绑定
        producer.setExecutorService(cachedThreadPool);
        //绑定事务监听器，用于执行代码
        TransactionListener transactionListener = new OrderTransactionListenerImpl();
        producer.setTransactionListener(transactionListener);
        //启动生产者
        producer.start();

        //创建消息对象
        Message msg =
                new Message("order", "order-1030",
                        "1030", "1030订单与明细的完整JSON数据（略）".getBytes());
        //一定要调用sendMessageInTransaction发送事务消息
        //参数1：消息对象
        //参数2：其他参数，目前用不到
        producer.sendMessageInTransaction(msg, null);
    }
}
