package com.grad.dao;

import com.grad.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public void addUser(User user);
    public User selectUserById(int uid);
    public int selectMaxUid();

}
