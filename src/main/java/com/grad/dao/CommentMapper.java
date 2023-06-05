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

    public Object checkLikeStatus(@Param("uid")String uid, @Param("commentId")String commentId);
    public void increaseCommentLikeCnt(@Param("commentId")String commentId, @Param("cnt")int cnt);
    public void setUserLikeStatus(@Param("uid")String uid, @Param("commentId")String commentId, @Param("likeStatus")int likeStatus);
    public void addUserLikeStatus(@Param("uid")String uid, @Param("commentId")String commentId, @Param("likeStatus")int likeStatus);
    public void deleteUserLikeStatus(@Param("uid")String uid, @Param("commentId")String commentId);
    public List<Comment> getCommentsByPostId(@Param("postId")String postId);
    public void deleteCommentById(@Param("commentId")String commentId);
    public void deleteUserLikeComment(@Param("commentId")String commentId);
}
