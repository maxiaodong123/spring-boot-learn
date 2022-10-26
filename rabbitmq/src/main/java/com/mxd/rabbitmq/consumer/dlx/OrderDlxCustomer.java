package com.mxd.rabbitmq.consumer.dlx;

import com.alibaba.fastjson.JSONObject;
import com.mxd.rabbitmq.provider.dlx.OrderDlxProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author mxd
 * @Date 2022/6/24 15:00
 * @Version 1.0
 * @Description
 */
@Component
@Slf4j
public class OrderDlxCustomer {

    @RabbitListener(queues = OrderDlxProducer.DLX_QUEUE_ORDER)
    public void outGoods(String json){
        log.info("订单20分钟未支付自动取消 消息体: {},执行时间：{}", json, LocalDateTime.now());
        try {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Long orderId =  jsonObject.getLong("orderId");

        }catch (Exception e){
            log.error("订单20分钟未支付自动取消,异常信息为",json);
            e.printStackTrace();
        }

    }
}
