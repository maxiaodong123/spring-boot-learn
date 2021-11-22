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

}
