package com.grad.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class ImageItem {

  private long imgId;
  private String postId;
  private long imgOrder;
  private String url;

  public ImageItem(long imgId, String postId, long imgOrder, String url) {
    this.imgId = imgId;
    this.postId = postId;
    this.imgOrder = imgOrder;
    this.url = url;
  }

  public long getImgId() {
    return imgId;
  }

  public void setImgId(long imgId) {
    this.imgId = imgId;
  }


  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
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
