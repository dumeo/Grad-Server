package com.grad.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectMapper {
    public void addCollect(@Param("uid")String uid, @Param("postId")String postId);
    public void deleteCollect(@Param("uid")String uid, @Param("postId")String postId);
    public Object checkIsCollected(@Param("uid")String uid, @Param("postId")String postId);
    public List<String> getUserPostCollect(@Param("uid")String uid);
}
