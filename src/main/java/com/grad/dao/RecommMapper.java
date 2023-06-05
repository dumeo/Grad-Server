package com.grad.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Mapper
public interface RecommMapper {
    List<String> getPostRecomm(@Param("postId") String postId);
    public void deleteRecommendByPostId(@Param("postId")String postId);
}
