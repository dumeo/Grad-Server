package com.grad.service;

import com.grad.constants.DefaultVals;
import com.grad.constants.RecommContants;
import com.grad.constants.UserConstants;
import com.grad.dao.UserMapper;
import com.grad.dao.bloomfilter.BloomFilter;
import com.grad.pojo.User;
import com.grad.util.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    BloomFilter bloomFilter;

    public User registerUser(User user){
        if(checkEmailExists(user.getEmail())) return null;

        String createDate = DateUtil.generateDate();
        String uid = UUIDUtil.generateUUID();
        user.setUid(uid);
        user.setCommunityName(UserConstants.DEFAULT_COMMUNITY);
        user.setHouseAddr(UserConstants.DEFAULT_HOUSE_ADDR);
        user.setAvatarUrl(DefaultVals.DEFAULT_AVATAR);
        user.setCreateDate(createDate);
        userMapper.addUser(user);
        User user_ = userMapper.getUserById(uid);
        return user_;
    }

    private boolean checkEmailExists(String email) {
        User user = userMapper.getUserByEmail(email);
        if(user != null) return true;
        return false;
    }

    public User checkUserById(String uid) throws  Exception{
        User user = userMapper.getUserById(uid);
        return user;
    }

    public User loginUser(String username, String password) {
        User user = userMapper.getUserByEmail(username);
        if(user == null || !user.getPassword().equals(password)) return null;
        return user;
    }

    public void storeUserViewRecord(String uid, String postId){
        log.info("Storing record, uid = " + uid + ", postId = " + postId);
        //用户已经浏览该贴，添加到布隆过滤器中
        if(!bloomFilter.contains(RecommContants.BF_VIEW_RECORD_PREFIX + uid, postId))
            bloomFilter.add(RecommContants.BF_VIEW_RECORD_PREFIX + uid, postId);

        //添加浏览记录到redis链表中
        if(redisTemplate.opsForList().size(RecommContants.LIST_VIEW_RECORD_PREFIX+uid) == RecommContants.MAX_LIST_VIEW_RECORD){
            redisTemplate.opsForList().rightPop(RecommContants.LIST_VIEW_RECORD_PREFIX+uid);
        }
        redisTemplate.opsForList().leftPush(RecommContants.LIST_VIEW_RECORD_PREFIX+uid, postId);

    }

}
