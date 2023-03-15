package com.grad.mapper;

import com.grad.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    public void addUser(User user);
    public User selectUserById(int uid);
    public int selectMaxUid();

}
