package com.mxd.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.logging.Logger;

/**
 * @Author: mxd
 * @Description:
 * @Date: 2021/11/25 18:08
 */

@Service
public class IndicatorService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    /**
     * 注入KafkaTemplate
     * @param kafkaTemplate kafka模版类
     */
    @Autowired
    public IndicatorService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @KafkaListener(topics = "#{kafkaTopicName}", groupId = "#{topicGroupId}")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        System.out.println("kafka processMessage start");
//        System.out.println("processMessage, topic = {}, msg = {}", record.topic(), record.value());

        // do something ...

        System.out.println("kafka processMessage end");
    }

    public void sendMessage(String topic, String data) {
        System.out.println("kafka sendMessage start");
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic, data);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
//                System.out.println("kafka sendMessage error, ex = {}, topic = {}, data = {}", ex, topic, data);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
//                System.out.println("kafka sendMessage success topic = {}, data = {}",topic, data);
            }
        });
        System.out.println("kafka sendMessage end");
    }
}
