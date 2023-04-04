package com.grad.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {

  private String postId;
  private String uid;
  private long postType;
  private String postTitle;
  private String postContent;
  private String postTag;
  private long viewTimes;
  private String postDate;
  private long likeCnt;

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }


  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }


  public long getPostType() {
    return postType;
  }

  public void setPostType(long postType) {
    this.postType = postType;
  }


  public String getPostTitle() {
    return postTitle;
  }

  public void setPostTitle(String postTitle) {
    this.postTitle = postTitle;
  }


  public String getPostContent() {
    return postContent;
  }

  public void setPostContent(String postContent) {
    this.postContent = postContent;
  }


  public String getPostTag() {
    return postTag;
  }

  public void setPostTag(String postTag) {
    this.postTag = postTag;
  }


  public long getViewTimes() {
    return viewTimes;
  }

  public void setViewTimes(long viewTimes) {
    this.viewTimes = viewTimes;
  }


  public String getPostDate() {
    return postDate;
  }

  public void setPostDate(String postDate) {
    this.postDate = postDate;
  }
  public long getLikeCnt() {
    return likeCnt;
  }

  public void setLikeCnt(long likeCnt) {
    this.likeCnt = likeCnt;
  }

}
