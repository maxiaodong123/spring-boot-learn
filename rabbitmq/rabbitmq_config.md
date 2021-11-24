★RabbitMQ
==========

★Version: 1.5.13.RELEASE
-------------------------

★属性文件：org.springframework.boot.autoconfigure.amqp.RabbitProperties
------------------------------------------------------------------------

★Config:
---------

base
----

> spring.rabbitmq.host: 服务Host<br>
> spring.rabbitmq.port: 服务端口<br>
> spring.rabbitmq.username: 登陆用户名<br>
> spring.rabbitmq.password: 登陆密码 <br>
> spring.rabbitmq.virtual-host: 连接到rabbitMQ的vhost<br>
> spring.rabbitmq.addresses: 指定client连接到的server的地址，多个以逗号分隔(优先取addresses，然后再取host)<br>
> spring.rabbitmq.requested-heartbeat: 指定心跳超时，单位秒，0为不指定；默认60s<br>
> spring.rabbitmq.publisher-confirms: 是否启用【发布确认】<br>
> spring.rabbitmq.publisher-returns: 是否启用【发布返回】<br>
> spring.rabbitmq.connection-timeout: 连接超时，单位毫秒，0表示无穷大，不超时<br>
> spring.rabbitmq.parsed-addresses:<br>

ssl

> spring.rabbitmq.ssl.enabled: 是否支持ssl<br>
> spring.rabbitmq.ssl.key-store: 指定持有SSL certificate的key store的路径<br>
> spring.rabbitmq.ssl.key-store-password: 指定访问key store的密码<br>
> spring.rabbitmq.ssl.trust-store: 指定持有SSL certificates的Trust store<br>
> spring.rabbitmq.ssl.trust-store-password: 指定访问trust store的密码<br>
> spring.rabbitmq.ssl.algorithm: ssl使用的算法，例如，TLSv1.1<br>

cache

> spring.rabbitmq.cache.channel.size: 缓存中保持的channel数量<br>
> spring.rabbitmq.cache.channel.checkout-timeout: 当缓存数量被设置时，从缓存中获取一个channel的超时时间，单位毫秒；如果为0，则总是创建一个新channel<br>
> spring.rabbitmq.cache.connection.size: 缓存的连接数，只有是CONNECTION模式时生效<br>
> spring.rabbitmq.cache.connection.mode: 连接工厂缓存模式：CHANNEL 和 CONNECTION<br>

listener

> spring.rabbitmq.listener.simple.auto-startup: 是否启动时自动启动容器<br>
> spring.rabbitmq.listener.simple.acknowledge-mode: 表示消息确认方式，其有三种配置方式，分别是none、manual和auto；默认auto<br>
> spring.rabbitmq.listener.simple.concurrency: 最小的消费者数量<br>
> spring.rabbitmq.listener.simple.max-concurrency: 最大的消费者数量<br>
> spring.rabbitmq.listener.simple.prefetch: 指定一个请求能处理多少个消息，如果有事务的话，必须大于等于transaction数量.<br>
> spring.rabbitmq.listener.simple.transaction-size: 指定一个事务处理的消息数量，最好是小于等于prefetch的数量.<br>
> spring.rabbitmq.listener.simple.default-requeue-rejected: 决定被拒绝的消息是否重新入队；默认是true（与参数acknowledge-mode有关系）<br>
> spring.rabbitmq.listener.simple.idle-event-interval: 多少长时间发布空闲容器时间，单位毫秒<br>
> spring.rabbitmq.listener.simple.retry.enabled: 监听重试是否可用<br>
> spring.rabbitmq.listener.simple.retry.max-attempts: 最大重试次数<br>
> spring.rabbitmq.listener.simple.retry.initial-interval: 第一次和第二次尝试发布或传递消息之间的间隔<br>
> spring.rabbitmq.listener.simple.retry.multiplier: 应用于上一重试间隔的乘数<br>
> spring.rabbitmq.listener.simple.retry.max-interval: 最大重试时间间隔<br>
> spring.rabbitmq.listener.simple.retry.stateless: 重试是有状态or无状态<br>

template

> spring.rabbitmq.template.mandatory: 启用强制信息；默认false<br>
> spring.rabbitmq.template.receive-timeout: receive() 操作的超时时间<br>
> spring.rabbitmq.template.reply-timeout: sendAndReceive() 操作的超时时间<br>
> spring.rabbitmq.template.retry.enabled: 发送重试是否可用<br>
> spring.rabbitmq.template.retry.max-attempts: 最大重试次数<br>
> spring.rabbitmq.template.retry.initial-interval: 第一次和第二次尝试发布或传递消息之间的间隔<br>
> spring.rabbitmq.template.retry.multiplier: 应用于上一重试间隔的乘数<br>
> spring.rabbitmq.template.retry.max-interval: 最大重试时间间隔<br>
