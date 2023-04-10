package com.grad.dao;

import com.grad.pojo.Post;
import com.grad.ret.vote.VoteOption;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VoteMapper {
    public void addVoteOption(VoteOption voteOption);
}
