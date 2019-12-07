package com.cooooode.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @Author: vua(杨晓迪)
 * @Date: Created on 2019-08-24
 * @Description:
 **/
@Service
public class CountService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    public int getCount(){

        ValueOperations<String, String> valueOperations= redisTemplate.opsForValue();


        if(!redisTemplate.hasKey("count")){
            valueOperations.set("count", "0");
        }
        valueOperations.increment("count",1);
        return Integer.parseInt(valueOperations.get("count"));
    }
}
