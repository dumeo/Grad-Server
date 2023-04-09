package com.grad.ret.vote;

import com.grad.pojo.Post;
import com.grad.pojo.User;

import java.util.List;

public class VoteItem {
    private Post post;
    private User user;
    private ClientToVoteInfo clientToThisInfo;
    private List<VoteOption> voteOptions;
    private int voteCnt = -1;

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
}