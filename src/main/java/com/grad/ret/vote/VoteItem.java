package com.grad.ret.vote;

import com.grad.pojo.Post;
import com.grad.pojo.User;

import java.util.List;

public class VoteItem {
    private Post post;
    private User user;
    private ClientToVoteInfo clientToThisInfo;
    private List<VoteOption> voteOptions;
    private int voteCnt = 0;
    private String Status;
    private String endTime;

    public VoteItem() {
    }

    public VoteItem(Post post, User user, ClientToVoteInfo clientToThisInfo, List<VoteOption> voteOptions) {
        this.post = post;
        this.user = user;
        this.clientToThisInfo = clientToThisInfo;
        this.voteOptions = voteOptions;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ClientToVoteInfo getClientToThisInfo() {
        return clientToThisInfo;
    }

    public void setClientToThisInfo(ClientToVoteInfo clientToThisInfo) {
        this.clientToThisInfo = clientToThisInfo;
    }

    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    public void setVoteOptions(List<VoteOption> voteOptions) {
        this.voteOptions = voteOptions;
    }

    public int getVoteCnt() {
        return voteCnt;
    }

    public void setVoteCnt(int voteCnt) {
        this.voteCnt = voteCnt;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "VoteItem{" +
                "post=" + post +
                ", user=" + user +
                ", clientToThisInfo=" + clientToThisInfo +
                ", voteOptions=" + voteOptions +
                ", voteCnt=" + voteCnt +
                ", Status='" + Status + '\'' +
                '}';
    }
}