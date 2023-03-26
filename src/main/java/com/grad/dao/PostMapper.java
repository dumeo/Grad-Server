package com.grad.dao;

import com.grad.pojo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    public List<Post> getPostsByNewest(@Param("count") Integer count);
    public void addPost(Post post);
}
