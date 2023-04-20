package com.grad.dao;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisOperator {
    @Resource
    RedisTemplate redisTemplate;

    public void set(String key, Object value, long timeOut, TimeUnit timeUnit){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, timeOut, timeUnit);
    }
    public List<String> getKeys(String prefix){
        Set<String> keys = redisTemplate.keys(prefix.concat("*"));
        List<String> tmp = new ArrayList<>(keys);
        List<String> res = new ArrayList<>();
        for(String key : tmp){
            String t = key.substring((prefix).length(), key.length());
            res.add(t);
        }
        return res;
    }
    public Object get(String key){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }


}
