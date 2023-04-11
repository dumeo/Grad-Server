package com.grad.service;

import com.grad.constants.DefaultVals;
import com.grad.constants.VoteConstants;
import com.grad.dao.PostMapper;
import com.grad.dao.RedisOperator;
import com.grad.dao.UserMapper;
import com.grad.dao.VoteMapper;
import com.grad.pojo.User;
import com.grad.ret.Status;
import com.grad.ret.vote.Vote;
import com.grad.ret.vote.VoteItem;
import com.grad.ret.vote.VoteOption;
import com.grad.util.DateUtil;
import com.grad.util.JsonUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VoteService {

    @Resource
    VoteMapper voteMapper;
    @Resource
    RedisOperator redisOperator;
    @Resource
    PostService postService;
    @Resource
    PostMapper postMapper;
    @Resource
    UserMapper userMapper;




    public String addVote(VoteItem voteItem) throws Exception{
        String createDate = DateUtil.generateDate();
        String endDate = voteItem.getVote().getEndDate();
        long interTime = DateUtil.getDateInter(createDate, endDate);
        Vote vote = voteItem.getVote();
        String voteId = UUIDUtil.generateUUID();
        vote.setVoteId(voteId);
        vote.setCreateDate(createDate);
        log.info("vote:" + vote.toString());
        voteMapper.addVote(vote);
        //Padding vote options
        for(VoteOption voteOption : voteItem.getVoteOptions()){
            String optionId = UUIDUtil.generateUUID();
            voteOption.setOptionId(optionId);
            voteOption.setVoteId(voteId);
            voteMapper.addVoteOption(voteOption);
        }
        //store postId to redis
        redisOperator.set("vote:" + voteId, 0, interTime, TimeUnit.HOURS);
        return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_OK));
    }


    public String getVoteById(String voteId, String clientUid){

        return null;
    }

    public List<VoteItem> getVoteByNewest() {
        List<Vote> votes = voteMapper.getVoteByNewest(DefaultVals.POST_ITEM_COUNT);
        List<String> votingIds = redisOperator.getKeys(VoteConstants.REDIS_VOTE_PREFIX);
        List<VoteItem> res = new ArrayList<>();
        for(Vote vote : votes){
            if(votingIds.contains(vote.getVoteId()))
                vote.setVoteStatus(VoteConstants.VOTE_STATUS_VOTING);
            else vote.setVoteStatus(VoteConstants.VOTE_STATUS_ENDED);
            User user = userMapper.getUserById(vote.getUid());
            List<VoteOption> voteOptions = voteMapper.getVoteOptionsByVoteId(vote.getVoteId());
            VoteItem voteItem = new VoteItem(vote, user, null, voteOptions);
            res.add(voteItem);
        }
        return res;
    }
    public List<VoteItem> getMoreVotes(String createDate) {
        List<Vote> votes = voteMapper.getMoreVotes(createDate, DefaultVals.POST_ITEM_COUNT);
        List<String> votingIds = redisOperator.getKeys(VoteConstants.REDIS_VOTE_PREFIX);
        List<VoteItem> res = new ArrayList<>();
        for(Vote vote : votes){
            if(votingIds.contains(vote.getVoteId()))
                vote.setVoteStatus(VoteConstants.VOTE_STATUS_VOTING);
            else vote.setVoteStatus(VoteConstants.VOTE_STATUS_ENDED);
            User user = userMapper.getUserById(vote.getUid());
            List<VoteOption> voteOptions = voteMapper.getVoteOptionsByVoteId(vote.getVoteId());
            VoteItem voteItem = new VoteItem(vote, user, null, voteOptions);
            res.add(voteItem);
        }

        return res;
    }
}
