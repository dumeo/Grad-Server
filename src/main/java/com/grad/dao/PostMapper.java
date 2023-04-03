package com.grad.dao;

import com.grad.pojo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    public List<Post> getPostsByNewest(@Param("count") Integer count);
    public void addPost(Post post);
    public void setPostType(@Param("postId") String postId, @Param("postType") long postType);
    public List<Post> getMorePosts(@Param("startTime") String startTime, @Param("count") Integer count);
    public Post getPostById(@Param("postId")String postId);
}
