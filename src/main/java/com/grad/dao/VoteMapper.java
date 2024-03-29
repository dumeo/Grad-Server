package com.grad.dao;

import com.grad.ret.vote.Vote;
import com.grad.ret.vote.VoteOption;
import com.grad.ret.vote.VoteRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VoteMapper {
    public void addVote(Vote vote);
    public Vote getVoteById(@Param("voteId")String voteId);
    public void addVoteOption(VoteOption voteOption);
    public List<VoteOption> getVoteOptionsByVoteId(@Param("voteId") String voteId);
    public List<Vote> getMoreVotes(@Param("createDate")String createDate, @Param("cnt")Integer cnt);
    public List<Vote> getVoteByNewest(@Param("cnt")Integer cnt);
    public VoteRecord getUserVoteRecord(@Param("uid")String uid, @Param("voteId")String voteId);
    public void addVoteRecord(@Param("uid")String uid, @Param("voteId")String voteId, @Param("optionId")String optionId);
    public Object getVoteCntByVoteId(@Param("voteId")String voteId);
    public Object getOptionVotedCnt(@Param("optionId")String optionId);
}
