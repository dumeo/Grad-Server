package com.grad.ret.vote;


public class VoteOption {

  private String optionId;
  private String postId;
  private String optionContent;
  private long optionOrder;
  private long cnt = 0;

  public VoteOption(String optionId, String postId, String optionContent, long optionOrder, long cnt) {
    this.optionId = optionId;
    this.postId = postId;
    this.optionContent = optionContent;
    this.optionOrder = optionOrder;
    this.cnt = cnt;
  }

  public String getOptionId() {
    return optionId;
  }

  public void setOptionId(String optionId) {
    this.optionId = optionId;
  }


  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }


  public String getOptionContent() {
    return optionContent;
  }

  public void setOptionContent(String optionContent) {
    this.optionContent = optionContent;
  }


  public long getOptionOrder() {
    return optionOrder;
  }

  public void setOptionOrder(long optionOrder) {
    this.optionOrder = optionOrder;
  }


  public long getCnt() {
    return cnt;
  }

  public void setCnt(long cnt) {
    this.cnt = cnt;
  }

}
