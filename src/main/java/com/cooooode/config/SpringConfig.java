package com.cooooode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import redis.clients.jedis.JedisPoolConfig;


/**
 * @Author: vua(杨晓迪)
 * @Date: Created on 2019-08-24
 * @Description:
 **/

@Configuration
//@PropertySource("classpath:./jdbc.properties")
public class SpringConfig {
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        // JdbcTemplate
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(1);
        return config;
    }
//    @Value("${redis.port}")
//    private int port;
//    @Value("${redis.host}")
//    private String host;
//    @Value("${redis.password}")
//    private String password;
    @Bean
    @Deprecated
    public JedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory redisConnectionFactory=new JedisConnectionFactory();
        redisConnectionFactory.setHostName("127.0.0.1");
        redisConnectionFactory.setPort(6379);
        redisConnectionFactory.setPassword("841103");
        redisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return redisConnectionFactory;
    }
    @Bean
    public RedisSerializer<String> redisSerializer(){
        return RedisSerializer.string();
    }
    @Bean
    @Deprecated
    public RedisTemplate<String,String> redisTemplate(){
        RedisTemplate<String,String> redisTemplate=new RedisTemplate<String,String>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        //序列化设置
        redisTemplate.setKeySerializer(redisSerializer());
        redisTemplate.setValueSerializer(redisSerializer());
        redisTemplate.setHashKeySerializer(redisSerializer());
        return redisTemplate;
    }
}
