package com.grad.dao;

import com.grad.pojo.PostImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {
    public void addImage(PostImage image);
    public List<PostImage> selectImagesByPostId(@Param("postId") String postId);
}
