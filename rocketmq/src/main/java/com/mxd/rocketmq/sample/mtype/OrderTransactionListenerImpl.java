package com.mxd.rocketmq.sample.mtype;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

@Data
@Slf4j
public class OrderTransactionListenerImpl implements TransactionListener {

    @Override
    //执行本地事务代码
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("正在执行本地事务,订单编号:" + msg.getKeys());
        /* 伪代码
        try{
            //插入1030号订单
            orderDao.insert(1030,order);
            //向1030号订单新增3条订单明细，10081-10083，
            orderDetailDao.insert(10081,1030,orderDetail1);
            orderDetailDao.insert(10082,1030,orderDetail2);
            orderDetailDao.insert(10083,1030,orderDetail3);
            connection.commit();
            //返回Commit，消费者可以消费1030订单消息
            return LocalTransactionState.COMMIT_MESSAGE;
        }catch(Exception e){
            //返回Rollback，Broker直接将数据删除，消费者不能收到1030订单消息
            connection.rollback();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        */
        log.info("模拟网络中断，Broker并未收到生产者本地事务状态回执，返回UNKNOW");
        return LocalTransactionState.UNKNOW;
    }

    @Override
    //会查本地事务处理状态
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {

        String keys = msg.getKeys();
        log.info("触发回查，正在检查" + keys + "订单状态");
        /* 伪代码

        Order order = orderDao.selectById(1030);
        if(order != null){
            //查询到记录，代表数据库已处理成功，回查返回Commit，消费者可以消费1030订单消息
            return LocalTransactionState.COMMIT_MESSAGE;
        }else{
            //未查询到记录，代表数据库处理失败，回查返回Rollback，Broker直接将数据删除，消费者不能收到1030订单消息
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        */
        log.info("回查结果，" + keys + "订单已入库，发送Commit指令");
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}