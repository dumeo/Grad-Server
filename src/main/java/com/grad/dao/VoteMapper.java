package com.grad.dao;

import com.grad.ret.vote.Vote;
import com.grad.ret.vote.VoteOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Mapper
public interface VoteMapper {
    public void addVote(Vote vote);
    public Vote getVoteById(@Param("voteId")String voteId);
    public void addVoteOption(VoteOption voteOption);
    public List<VoteOption> getVoteOptionsByVoteId(@Param("voteId") String voteId);
    public List<Vote> getMoreVotes(@Param("createDate")String createDate, @Param("cnt")Integer cnt);
    public List<Vote> getVoteByNewest(@Param("cnt")Integer cnt);
}
