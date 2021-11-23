package com.mxd.rabbitmq.constant;

public interface RabbitConstant {

    /**
     * 简单模式
     */
    String SIMPLE_QUEUE_NAME = "simple_queue";

    /**
     * work 模式
     */
    String WORK_QUEUE_NAME = "work_queue";

    /**
     * 发布/订阅（publish/subscribe）模式
     */
    String PUBLISH_SUBSCRIBE_EXCHANGE_NAME = "publish_subscribe_exchange";
    String PUBLISH_SUBSCRIBE_FIRST_QUEUE_NAME = "publish_subscribe_first_queue";
    String PUBLISH_SUBSCRIBE_SECOND_QUEUE_NAME = "publish_subscribe_second_queue";

    /**
     * 路由（routing）模式
     */
    String ROUTING_EXCHANGE_NAME = "routing_exchange";
    String ROUTING_FIRST_QUEUE_NAME = "routing_first_queue";
    String ROUTING_SECOND_QUEUE_NAME = "routing_second_queue";
    String ROUTING_THIRD_QUEUE_NAME = "routing_third_queue";
    String ROUTING_FIRST_QUEUE_ROUTING_KEY_NAME = "routing_first_queue_routing_key";
    String ROUTING_SECOND_QUEUE_ROUTING_KEY_NAME = "routing_second_queue_routing_key";
    String ROUTING_THIRD_QUEUE_ROUTING_KEY_NAME = "routing_third_queue_routing_key";

    /**
     * 主题（topics）模式
     */
    String TOPICS_EXCHANGE_NAME = "topics_exchange";
    String TOPICS_FIRST_QUEUE_NAME = "topics_first_queue";
    String TOPICS_SECOND_QUEUE_NAME = "topics_second_queue";
    String TOPICS_THIRD_QUEUE_NAME = "topics_third_queue";
    String TOPICS_FIRST_QUEUE_ROUTING_KEY = "topics.first.routing.key";
    String TOPICS_SECOND_QUEUE_ROUTING_KEY = "topics.second.routing.key";
    String TOPICS_THIRD_QUEUE_ROUTING_KEY = "topics.third.routing.key";
    String TOPICS_ROUTING_KEY_FIRST_WILDCARD = "#.first.#";
    String TOPICS_ROUTING_KEY_SECOND_WILDCARD = "*.second.#";
    String TOPICS_ROUTING_KEY_THRID_WILDCARD = "*.third.*";

    /**
     * header 模式
     */
    String HEADER_EXCHANGE_NAME = "header_exchange";
    String HEADER_FIRST_QUEUE_NAME = "header_first_queue";
    String HEADER_SECOND_QUEUE_NAME = "header_second_queue";

    /**
     * rpc 模式
     */
    String RPC_QUEUE_NAME = "rpc_queue";


    /**
     * retry 消息重试
     */
    String RETRY_EXCHANGE_NAME = "retry_exchange";
    String RETRY_QUEUE_NAME = "retry_queue";

    /**
     * 异常队列
     */
    String ERROR_EXCHANGE_NAME = "error_exchange";
    String ERROR_QUEUE_NAME = "error_queue";
    String ERROR_ROUTING_KEY = "error_routing_key";

    /**
     * 死信队列
     */
    String NORMAL_EXCHANGE_NAME = "normal_exchange";
    String NORMAL_QUEUE_NAME = "normal_queue";
    String NORMAL_ROUTING_KEY = "normal_routing_key";

    String DLX_EXCHANGE_NAME = "dlx_exchange";
    String DLX_QUEUE_NAME = "dlx_queue";
    String DLX_ROUTING_KEY = "dlx_routing_key";

    /**
     * DLX，定义参数
     */
    String X_DEAD_LETTER_EXCHANGE="x-dead-letter-exchange";
    String X_DEAD_LETTER_ROUTING_KEY="x-dead-letter-routing-key";
    String X_MESSAGE_TTL="x-message-ttl";
    String X_MAX_LENGTH="x-max-length";

}


