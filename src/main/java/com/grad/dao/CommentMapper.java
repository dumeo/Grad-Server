package com.grad.dao;

import com.grad.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    public void addCommentOnFather(Comment comment);
    public void addComment(Comment comment);
    public List<Comment> getCommentsByTime(@Param("postId") String postId);
    public long getPostCommentCnt(@Param("postId")String postId);
    public Comment getCommentById(@Param("commentId")String commentId);
}
