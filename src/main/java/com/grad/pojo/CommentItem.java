package com.grad.pojo;

import com.grad.ret.ClientToThisInfo;

import java.util.ArrayList;
import java.util.List;


public class CommentItem {
    //评论实体
    private Comment comment;
    //子评论列表
    List<CommentItem> childComments = new ArrayList<>();
    //发表该评论的用户
    private User user;
    //客户端对该评论的操作
    private ClientToThisInfo clientToThisInfo;

    public CommentItem(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public List<CommentItem> getChildComments() {
        return childComments;
    }

    public ClientToThisInfo getClientToThisInfo() {
        return clientToThisInfo;
    }

    public void setClientToThisInfo(ClientToThisInfo clientToThisInfo) {
        this.clientToThisInfo = clientToThisInfo;
    }
}
