package com.grad.service;

import com.grad.constants.DefaultVals;
import com.grad.dao.UserMapper;
import com.grad.pojo.User;
import com.grad.ret.Status;
import com.grad.util.*;
import com.grad.ret.RegisterRet;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public User registerUser(User user){
        String createDate = DateUtil.generateDate();
        String uid = UUIDUtil.generateUUID();
        user.setUid(uid);
        user.setCreateDate(createDate);
        user.setAvatarUrl(DefaultVals.DEFAULT_AVATAR);
        userMapper.addUser(user);
        User user_ = userMapper.getUserById(uid);
        return user_;
    }

    public User checkUserById(String uid) throws  Exception{
        User user = userMapper.getUserById(uid);
        return user;
    }

    public User loginUser(String username, String password) {
        return null;
    }
}
