package com.grad.dao;

import com.grad.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    public void addUser(User user);
    public User getUserById(String uid);

    public User getUserByEmail(@Param("email") String email);
}
