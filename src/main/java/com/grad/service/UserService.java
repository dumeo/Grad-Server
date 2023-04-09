package com.grad.service;

import com.grad.dao.UserMapper;
import com.grad.pojo.User;
import com.grad.ret.Status;
import com.grad.util.*;
import com.grad.ret.RegisterRet;
import jakarta.annotation.Resource;
import org.hibernate.dialect.identity.DB2390IdentityColumnSupport;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Service;

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
        User RetUser = userMapper.getUserById(uid);
        return new RegisterRet(RetUser, HttpStatusCode.REGISTER_SUCCESS, HttpStatusCode.MSG_REGISTER_SUCCESS);
    }

    public String checkUserById(String uid) throws  Exception{
        User user = userMapper.getUserById(uid);
        Status status;
        if(user != null){
            status = new Status(DefaultVals.STATUS_OK, DefaultVals.MSG_USER_EXISTS);
        }
        else {
            status = new Status(DefaultVals.STATUS_OK, DefaultVals.MSG_USER_NOT_EXISTS);
        }
        return JsonUtil.objectToJson(status);
    }

}
