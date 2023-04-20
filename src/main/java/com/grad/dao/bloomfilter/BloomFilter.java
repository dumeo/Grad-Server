package com.grad.dao.bloomfilter;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BloomFilter {
    @Resource
    RedissonClient redissonClient;
    public void add(String key, String value) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(10000000, 0.03);
        bloomFilter.add(value);
    }

    public boolean contains(String key, String value) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(key);
        boolean res = false;
        try{
            res = bloomFilter.contains(value);
            return res;
        }catch (Exception e){
            log.info("======= BloomFilter is not initialized, Trying to init  ======");
            bloomFilter.tryInit(10000000, 0.03);
            return bloomFilter.contains(value);
        }
    }
}
