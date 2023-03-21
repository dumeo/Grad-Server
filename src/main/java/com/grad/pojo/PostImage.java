package com.grad.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostImage {

  private long imgId;
  private long postId;
  private long imgOrder;
  private String url;


  public long getImgId() {
    return imgId;
  }

  public void setImgId(long imgId) {
    this.imgId = imgId;
  }


  public long getPostId() {
    return postId;
  }

  public void setPostId(long postId) {
    this.postId = postId;
  }


  public long getImgOrder() {
    return imgOrder;
  }

  public void setImgOrder(long imgOrder) {
    this.imgOrder = imgOrder;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


}
