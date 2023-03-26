package com.grad.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private String uid;
  private long utype;
  private String username;
  private String password;
  private String email;
  private String communityName;
  private String houseAddr;
  private String avatarUrl;
  private long emailValid;
  private String createDate;




  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }


  public long getUtype() {
    return utype;
  }

  public void setUtype(long utype) {
    this.utype = utype;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getCommunityName() {
    return communityName;
  }

  public void setCommunityName(String communityName) {
    this.communityName = communityName;
  }


  public String getHouseAddr() {
    return houseAddr;
  }

  public void setHouseAddr(String houseAddr) {
    this.houseAddr = houseAddr;
  }


  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }


  public long getEmailValid() {
    return emailValid;
  }

  public void setEmailValid(long emailValid) {
    this.emailValid = emailValid;
  }


  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    return "User{" +
            "uid=" + uid +
            ", utype=" + utype +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            ", communityName='" + communityName + '\'' +
            ", houseAddr='" + houseAddr + '\'' +
            ", avatarUrl='" + avatarUrl + '\'' +
            ", emailValid=" + emailValid +
            ", createDate='" + createDate + '\'' +
            '}';
  }
}
