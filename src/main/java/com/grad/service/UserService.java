package com.grad.service;

import com.grad.dao.UserMapper;
import com.grad.pojo.User;
import com.grad.util.DefaultVals;
import com.grad.util.HttpStatusCode;
import com.grad.ret.RegisterRet;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public RegisterRet registerUser(User user){
        padUser(user);
        userMapper.addUser(user);
        long uid = userMapper.selectMaxUid();
        return new RegisterRet(uid, HttpStatusCode.REGISTER_SUCCESS, HttpStatusCode.MSG_REGISTER_SUCCESS);
    }

    public static void padUser(User user){
        String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        user.setCreateDate(createDate);
        user.setEmail(DefaultVals.DEFAULT_EMAIL);
        user.setAvatarUrl(DefaultVals.DEFAULT_AVATAR);
        user.setEmailValid(DefaultVals.DEFAULT_EMAIL_VALID);
    }

}
