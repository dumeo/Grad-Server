package com.grad.dao;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisOperator {
    @Resource
    RedisTemplate redisTemplate;

    public void set(String key, Object value, long timeOut, TimeUnit timeUnit){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeOut, timeUnit);
    }
}
