package com.mxd.rabbitmq.provider.dlx;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author mxd
 * @Date 2022/6/24 14:49
 * @Version 1.0
 * @Description 订单半个小时未支付自动取消 死信队列
 */
@Configuration
public class OrderDlxProducer {

    private Long delayTime = 120000L;

    //普通队列名称
    public static final String QUEUE_ORDER = "queue_order";

    //普通交换机名称
    public final static String EXCHANGE_ORDER = "exchange_order";

    // 普通routingKey
    public final static String ROUTING_KEY_ORDER = "routing_key_order";

    //死信消息队列名称
    public final static String DLX_QUEUE_ORDER = "dlx_queue_order";

    //死信交换机名称
    public final static String DLX_EXCHANGE_ORDER = "dlx_exchange_order";

    //死信 routingKey
    public final static String DLX_ROUTINGKEY_ORDER = "dlx_routingkey_order";

    //死信队列 交换机标识符
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    //死信队列交换机绑定键标识符
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";



    /**
     * 生命一个普通的队列
     * @return
     */
    @Bean
    public Queue orderQueue() {
        // 将普通队列绑定到死信队列交换机上
        Map<String, Object> args = new HashMap<>();
        //直接设置 Queue 延迟时间 但如果直接给队列设置过期时间,这种做法不是很灵活
        args.put("x-message-ttl", delayTime);
        //这里采用发送消息动态设置延迟时间,这样我们可以灵活控制
        args.put(DEAD_LETTER_QUEUE_KEY, DLX_EXCHANGE_ORDER);
        args.put(DEAD_LETTER_ROUTING_KEY, DLX_ROUTINGKEY_ORDER);
        Queue queue = new Queue(QUEUE_ORDER, true, false, false, args);
        return queue;
    }


    /**
     * 声明一个direct类型的交换机
     */
    @Bean
    DirectExchange orderExchange() {
        return new DirectExchange(EXCHANGE_ORDER);
    }


    /**
     * 绑定Queue队列到交换机,并且指定routingKey
     */
    @Bean
    Binding bindingDirectExchangeOrder() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ROUTING_KEY_ORDER);
    }

    /**
     * 创建配置死信队列
     * @return
     */
    @Bean
    public Queue dlxQueueOrder() {
        Queue queue = new Queue(DLX_QUEUE_ORDER, true);
        return queue;
    }

    /**
     * 创建死信交换机
     * @return
     */
    @Bean
    public DirectExchange dlxExchangeOrder() {
        return new DirectExchange(DLX_EXCHANGE_ORDER);
    }


    /**
     * 死信队列与死信交换机绑定
     * @return
     */
    @Bean
    public Binding bindingDeadExchangeOrder() {
        return BindingBuilder.bind(dlxQueueOrder()).to(dlxExchangeOrder()).with(DLX_ROUTINGKEY_ORDER);
    }


}
