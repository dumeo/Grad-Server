package com.grad.service;

import com.grad.constants.DefaultVals;
import com.grad.constants.UserConstants;
import com.grad.dao.UserMapper;
import com.grad.pojo.User;
import com.grad.util.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

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
}
