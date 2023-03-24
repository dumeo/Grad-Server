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
        String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        user.setCreateDate(createDate);
        user.setAvatarUrl(DefaultVals.DEFAULT_AVATAR);
        userMapper.addUser(user);
        long uid = userMapper.selectMaxUid();
        User RetUser = userMapper.selectUserById(uid);
        return new RegisterRet(RetUser, HttpStatusCode.REGISTER_SUCCESS, HttpStatusCode.MSG_REGISTER_SUCCESS);
    }

}
