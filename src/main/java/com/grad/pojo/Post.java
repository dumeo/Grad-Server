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

  private long postId;
  private long uid;
  private long postType;
  private String postTitle;
  private String postContent;
  private String postTag;
  private long viewTimes;
  private String postDate;


  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }


  public long getUid() {
    return uid;
  }

  public void setUid(long uid) {
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


}
