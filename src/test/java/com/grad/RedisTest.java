package com.grad;

import com.grad.constants.UserConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
public class RedisTest {
    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void test(){
//        redisTemplate.delete(UserConstants.REDIS_RESERVE_PREFIX + "50a9437d12634a30962df64f3efc59ff");
    }

    @Test
    public void test2(){
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        redisTemplate.delete("key1");
        String val1 = (String) valueOperations.get("key1");
        String val2 = (String) valueOperations.get("key22");
        log.info("val1 = " + val1 + ", val2 = " + val2);
    }
}
