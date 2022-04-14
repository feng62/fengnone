package com.feng.socket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisConfig {

    /**
     * description: 手动注册Redis监听到IOC
     *
     * @param redisConnectionFactory
     * @return: org.springframework.data.redis.listener.RedisMessageListenerContainer
     * @author: weirx
     * @time: 2021/3/22 14:11
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }
}