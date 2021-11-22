package com.mxd.rabbitmq.provider.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: mxd
 * @Description:
 * @Date: 2021/11/22 17:36
 */
@Slf4j
@Component
public class ReturnCallbackService implements RabbitTemplate.ReturnsCallback {

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("returnedMessage {}", returnedMessage);
    }
}