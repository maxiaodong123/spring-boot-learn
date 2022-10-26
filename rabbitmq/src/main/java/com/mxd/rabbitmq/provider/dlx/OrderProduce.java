package com.mxd.rabbitmq.provider.dlx;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author mxd
 * @Date 2022/6/24 14:57
 * @Version 1.0
 * @Description 订单半个小时未支付自动取消 死信队列
 */
@Slf4j
@Component
public class OrderProduce {

    @Autowired
    private AmqpTemplate rabbitAmqpTemplate;


    /***
     * 订单半个小时未支付自动取消
     * @param
     */
    public void send(Long orderId) {
        log.info("消息传输开始---订单id为{}----发送时间为{}----" ,orderId, LocalDateTime.now());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",orderId);
        rabbitAmqpTemplate.convertAndSend(
                OrderDlxProducer.EXCHANGE_ORDER
                , OrderDlxProducer.ROUTING_KEY_ORDER, jsonObject.toJSONString());
    }


}
