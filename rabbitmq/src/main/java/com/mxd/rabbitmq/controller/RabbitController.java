package com.mxd.rabbitmq.controller;

import com.mxd.rabbitmq.constant.RabbitConstant;
import com.mxd.rabbitmq.provider.confirm.RabbitConfirmProvider;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitConfirmProvider rabbitConfirmProvider;

    @GetMapping(value = "/simple")
    public void simple() {
        rabbitTemplate.convertAndSend(RabbitConstant.SIMPLE_QUEUE_NAME, "hello world!");
    }

    @GetMapping(value = "/work")
    public void work() {
        rabbitTemplate.convertAndSend(RabbitConstant.WORK_QUEUE_NAME, "work hello!");
    }

    @GetMapping(value = "/pubsub")
    public void pubsub() {
        rabbitTemplate.convertAndSend(RabbitConstant.PUBLISH_SUBSCRIBE_EXCHANGE_NAME, null, "publish/subscribe hello");
    }

    @GetMapping(value = "/routing")
    public void routing() {
        // 给第一个队列发送消息
        rabbitTemplate.convertAndSend(RabbitConstant.ROUTING_EXCHANGE_NAME, RabbitConstant.ROUTING_FIRST_QUEUE_ROUTING_KEY_NAME, "routing hello");
    }

    @GetMapping(value = "/topics")
    public void topics() {
        // 给第一个队列发送消息，此时队列能接受到消息，因为队列通配符为 #.first.#，而 routing_key 为 topics.first.routing.key，匹配成功
        rabbitTemplate.convertAndSend(RabbitConstant.TOPICS_EXCHANGE_NAME, RabbitConstant.TOPICS_FIRST_QUEUE_ROUTING_KEY, "topics hello");
        // 给第二个队列发送消息，此时队列也能接受到消息，因为队列通配符为 *.second.#，而 routing_key 为 topics.second.routing.key，匹配成功
        rabbitTemplate.convertAndSend(RabbitConstant.TOPICS_EXCHANGE_NAME, RabbitConstant.TOPICS_SECOND_QUEUE_ROUTING_KEY, "topics hello");
        // 给第三个队列发送消息，此时队列无法接受到消息，因为队列通配符为 *.third.*，而 routing_key 为 topics.third.routing.key，匹配失败
        rabbitTemplate.convertAndSend(RabbitConstant.TOPICS_EXCHANGE_NAME, RabbitConstant.TOPICS_THIRD_QUEUE_ROUTING_KEY, "topics hello");
    }

    @GetMapping(value = "/header")
    public void header() {
        // 这条消息应该能被两个队列都接收到，第一个队列 all 匹配成功，第二个队列 hello-value any匹配成功
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("matchAll", "YES");
        messageProperties.setHeader("hello", "world");
        Message message = new Message("header first hello".getBytes(StandardCharsets.UTF_8), messageProperties);
        rabbitTemplate.convertAndSend(RabbitConstant.HEADER_EXCHANGE_NAME, null, message);

        // 这条消息应该只被第二个队列接受，第一个队列 all 匹配失败，第二个队列 matchAll-NO any匹配成功
        MessageProperties messagePropertiesSecond = new MessageProperties();
        messagePropertiesSecond.setHeader("matchAll", "NO");
        Message messageSecond = new Message("header second hello".getBytes(StandardCharsets.UTF_8), messagePropertiesSecond);
        rabbitTemplate.convertAndSend(RabbitConstant.HEADER_EXCHANGE_NAME, null, messageSecond);
    }

    @GetMapping(value = "/rpc")
    public void rpc() {
        Object responseMsg = rabbitTemplate.convertSendAndReceive(RabbitConstant.RPC_QUEUE_NAME, "rpc hello!");
        System.out.println("rabbit rpc response message: " + responseMsg);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ResponseBody
    public String confirm() {

        for (int i = 0; i < 100; i++) {
            System.out.println("发送第" + i + "个消息");
            rabbitConfirmProvider.sendMessage("", "confirm_test_queue",  "发送消息");
        }
        return "success";
    }


    @RequestMapping(value = "/confirmError", method = RequestMethod.GET)
    @ResponseBody
    public String confirmError() {

        for (int i = 0; i < 100; i++) {
            System.out.println("发送第" + i + "个消息");
            rabbitConfirmProvider.sendMessage("confirm_test_queue", "confirm_test_queue",  "发送消息");
        }
        return "success";
    }

    @RequestMapping(value = "/retry", method = RequestMethod.GET)
    @ResponseBody
    public String retry() {
        rabbitTemplate.convertAndSend(RabbitConstant.RETRY_EXCHANGE_NAME, RabbitConstant.RETRY_QUEUE_NAME, "retry hello");

        return "success";
    }

    @RequestMapping(value = "/dlx", method = RequestMethod.GET)
    @ResponseBody
    public String dlx() {
        rabbitTemplate.convertAndSend(RabbitConstant.NORMAL_EXCHANGE_NAME, RabbitConstant.NORMAL_ROUTING_KEY,"dlx1 hello");
        rabbitTemplate.convertAndSend(RabbitConstant.DLX_EXCHANGE_NAME, RabbitConstant.DLX_ROUTING_KEY,"dlx2 hello");

        return "success";
    }
}
