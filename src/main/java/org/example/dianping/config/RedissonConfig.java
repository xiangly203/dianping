package org.example.dianping.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient(@Value("${spring.data.redis.host}") String host, @Value("${spring.data.redis.port}") String port){
        Config config = new Config();
        config.useSingleServer().setAddress(String.format("redis://%s:%s", host, port));
        return Redisson.create(config);
    }

    @Bean
    RedissonConnectionFactory redissonConnectionFactory(RedissonClient redissonClient) {
        return new RedissonConnectionFactory(redissonClient);
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedissonConnectionFactory redissonConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redissonConnectionFactory);
        return template;
    }
}