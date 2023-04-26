package com.grad.dao;

import com.grad.pojo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    public List<Post> getPostsByNewest(@Param("postTag")String postTag, @Param("count") Integer count);
    public void addPost(Post post);
    public void setPostType(@Param("postId") String postId, @Param("postType") long postType);
    public List<Post> getMorePosts(@Param("postTag")String postTag, @Param("startTime") String startTime, @Param("count") Integer count);
    public Post getPostById(@Param("postId")String postId);
    public Object checkLikeStatus(@Param("uid")String uid, @Param("postId")String postId);
    public void increasePostLikeCnt(@Param("postId")String postId, @Param("cnt")int cnt);
    public void setUserLikeStatus(@Param("uid")String uid, @Param("postId")String postId, @Param("likeStatus")int likeStatus);
    public void addUserLikeStatus(@Param("uid")String uid, @Param("postId")String postId, @Param("likeStatus")int likeStatus);
    public void deleteUserLikeStatus(@Param("uid")String uid, @Param("postId")String postId);
    public List<Post> getPostsByRange(@Param("start")Integer start, @Param("end") Integer end);
    public int getPostTotalCnt();
}
