package com.grad.dao.bloomfilter;

import jakarta.annotation.Resource;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
public class BloomFilterOp {
    @Resource
    RedissonClient redissonClient;
    public void add(String key, String value) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(key);
        bloomFilter.tryInit(10000000, 0.03);
        bloomFilter.add(value);
    }

    public boolean contains(String key, String value) {
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter(key);
        return bloomFilter.contains(value);
    }
}
