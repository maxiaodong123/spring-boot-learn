package com.mxd.redisson.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableCaching
@Component
public class RedisConfig {

    @Autowired
    private RedisConfigProperties redisConfigProperties;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        //设置序列化
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        //指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //配置redisTemplate
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 配置连接工厂
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig genericObjectPoolConfig) {
        // 单机版配置
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase()==null ? 0 : redisProperties.getDatabase());
//        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
//        redisStandaloneConfiguration.setPort(redisProperties.getPort());
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
        // 集群版配置
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        //获取redis集群的ip及端口号等相关信息；
//        String[] serverArray = redisProperties.getNodes().split(",");
//        Set<HostAndPort> nodes = new HashSet<>();
//
//        //遍历add到HostAndPort中；
//        for (String ipPort : serverArray) {
//            String[] ipPortPair = ipPort.split(":");
//            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
//        }
//        String[] serverArray = redisClusterProperties.getNodes().split(",");
        Set<RedisNode> nodes = new HashSet<RedisNode>();
//        for (String ipPort : serverArray) {
//            String[] ipAndPort = ipPort.split(":");
//            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
//        }

        List<String> clusterNodes = new ArrayList<>();
        for (int i = 0; i < redisConfigProperties.getCluster().getNodes().size(); i++) {
            String ipPort = redisConfigProperties.getCluster().getNodes().get(i);
            String[] ipAndPort = ipPort.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
        }

        redisClusterConfiguration.setPassword(redisConfigProperties.getPassword());
        redisClusterConfiguration.setClusterNodes(nodes);
        redisClusterConfiguration.setMaxRedirects(redisConfigProperties.getCluster().getMaxRedirects());
        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(50000))
                .poolConfig(genericObjectPoolConfig)
                .build();
//        if (redisProperties.getSsl()){
//            clientConfig.isUseSsl();
//        }
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
        return factory;
    }

    /**
     * GenericObjectPoolConfig 连接池配置
     *
     * @return
     */
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(redisConfigProperties.getLettuce().getPool().getMaxIdle());
        genericObjectPoolConfig.setMinIdle(redisConfigProperties.getLettuce().getPool().getMinIdle());
        genericObjectPoolConfig.setMaxTotal(redisConfigProperties.getLettuce().getPool().getMaxActive());
        genericObjectPoolConfig.setMaxWaitMillis(redisConfigProperties.getLettuce().getPool().getMaxWait());
        return genericObjectPoolConfig;
    }

    @Bean
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(lettuceConnectionFactory);
        return builder.build();
    }

    //添加redisson的bean
    @Bean(name = "redisson")
    public RedissonClient redisson() {
        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
//        List<String> clusterNodes = new ArrayList<>();
//        for (int i = 0; i < redisConfigProperties.getCluster().getNodes().size(); i++) {
//            clusterNodes.add("redis://" + redisConfigProperties.getCluster().getNodes().get(i));
//        }
//        Config config = new Config();
//        ClusterServersConfig clusterServersConfig = config.useClusterServers()
//                .addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
//        clusterServersConfig.setPassword(redisConfigProperties.getPassword());//设置密码
//        return (Redisson) Redisson.create(config);


        //redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        List<String> clusterNodes = new ArrayList<>();
        for (int i = 0; i < redisConfigProperties.getCluster().getNodes().size(); i++) {
            clusterNodes.add("redis://" + redisConfigProperties.getCluster().getNodes().get(i));
        }
        Config config = new Config();
        // 添加集群地址
        ClusterServersConfig clusterServersConfig = config.useClusterServers().addNodeAddress(
                clusterNodes.toArray(new String[clusterNodes.size()]));
//        config.useClusterServers().setScanInterval(1000);
        clusterServersConfig.setScanInterval(1000);
        clusterServersConfig.setConnectTimeout(1000000);
        clusterServersConfig.setTimeout(1000000);
        // 设置密码
        clusterServersConfig.setPassword(redisConfigProperties.getPassword());
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

}
