package com.grad.dao;

import com.grad.mapper.UserMapper;
import com.grad.pojo.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

    @Resource
    UserMapper userMapper;
    public void insertToUser(User user){
        userMapper.addUser(user);
    }

    public int getMaxUid(){
        return userMapper.selectMaxUid();
    }
}
