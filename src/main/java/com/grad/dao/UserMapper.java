package com.grad.dao;

import com.grad.pojo.User;
import com.grad.ret.committee.NoteItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    public void addUser(User user);
    public User getUserById(String uid);
    public User getUserByEmail(@Param("email") String email);
    public void storeUserViewRecord(@Param("uid")String uid, @Param("postId")String postId, @Param("createDate")String createDate);
    public List<String> getUserViewRecord(@Param("uid")String uid);
    List<NoteItem> getNotes(@Param("communityName")String communityName);
    void readNote(@Param("noteId")String noteId);
}
