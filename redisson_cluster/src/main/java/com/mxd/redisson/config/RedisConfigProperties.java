package com.mxd.redisson.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProperties {
    private String password;
    private cluster cluster;
    private lettuce lettuce;

    public static class cluster {
        private List<String> nodes;
        private int expireSeconds;
        private int commandTimeout;
        private int maxRedirects;

        public int getExpireSeconds() {
            return expireSeconds;
        }

        public void setExpireSeconds(int expireSeconds) {
            this.expireSeconds = expireSeconds;
        }

        public int getCommandTimeout() {
            return commandTimeout;
        }

        public void setCommandTimeout(int commandTimeout) {
            this.commandTimeout = commandTimeout;
        }

        public int getMaxRedirects() {
            return maxRedirects;
        }

        public void setMaxRedirects(int maxRedirects) {
            this.maxRedirects = maxRedirects;
        }

        public List<String> getNodes() {
            return nodes;
        }

        public void setNodes(List<String> nodes) {
            this.nodes = nodes;
        }
    }

    public static class lettuce {
        private pool pool;

        public RedisConfigProperties.pool getPool() {
            return pool;
        }

        public void setPool(RedisConfigProperties.pool pool) {
            this.pool = pool;
        }
    }

    public static class pool {
        private int maxActive;
        private int maxWait;
        private int maxIdle;
        private int minIdle;

        public int getMaxActive() {
            return maxActive;
        }

        public void setMaxActive(int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxWait() {
            return maxWait;
        }

        public void setMaxWait(int maxWait) {
            this.maxWait = maxWait;
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            this.minIdle = minIdle;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RedisConfigProperties.cluster getCluster() {
        return cluster;
    }

    public void setCluster(RedisConfigProperties.cluster cluster) {
        this.cluster = cluster;
    }

    public RedisConfigProperties.lettuce getLettuce() {
        return lettuce;
    }

    public void setLettuce(RedisConfigProperties.lettuce lettuce) {
        this.lettuce = lettuce;
    }
}