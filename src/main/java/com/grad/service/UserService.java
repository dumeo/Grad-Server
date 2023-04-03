package com.grad.service;

import com.grad.dao.UserMapper;
import com.grad.pojo.User;
import com.grad.util.DateUtil;
import com.grad.util.DefaultVals;
import com.grad.util.HttpStatusCode;
import com.grad.ret.RegisterRet;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {

    @Resource
    UserMapper userMapper;

    public RegisterRet registerUser(User user){
        String createDate = DateUtil.generateDate();
        String uid = UUIDUtil.generateUUID();
        user.setUid(uid);
        user.setCreateDate(createDate);
        user.setAvatarUrl(DefaultVals.DEFAULT_AVATAR);
        userMapper.addUser(user);
        User RetUser = userMapper.selectUserById(uid);
        return new RegisterRet(RetUser, HttpStatusCode.REGISTER_SUCCESS, HttpStatusCode.MSG_REGISTER_SUCCESS);
    }

}
