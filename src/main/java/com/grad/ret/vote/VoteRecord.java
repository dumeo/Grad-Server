package com.grad.ret.vote;


public class VoteRecord {

  private long id;
  private String uid;
  private String voteId;
  private String optionId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }


  public String getVoteId() {
    return voteId;
  }

  public void setVoteId(String voteId) {
    this.voteId = voteId;
  }


  public String getOptionId() {
    return optionId;
  }

  public void setOptionId(String optionId) {
    this.optionId = optionId;
  }

  @Override
  public String toString() {
    return "VoteRecord{" +
            "id=" + id +
            ", uid='" + uid + '\'' +
            ", voteId='" + voteId + '\'' +
            ", optionId='" + optionId + '\'' +
            '}';
  }
}
