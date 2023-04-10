package com.grad.service;

import com.grad.constants.DefaultVals;
import com.grad.dao.RedisOperator;
import com.grad.dao.VoteMapper;
import com.grad.pojo.Post;
import com.grad.ret.Status;
import com.grad.ret.vote.VoteItem;
import com.grad.ret.vote.VoteOption;
import com.grad.util.DateUtil;
import com.grad.util.JsonUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.DatabaseVersion;
import org.springframework.stereotype.Service;

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

    public String addVote(VoteItem voteItem) throws Exception{
        String startTime = DateUtil.generateDate();
        String endTime = voteItem.getEndTime();
        log.info("endTime = " + endTime);
        long interTime = DateUtil.getDateInter(startTime, endTime);
        String postId = UUIDUtil.generateUUID();
        Post post = voteItem.getPost();
        //Padding post
        post.setPostId(postId);
        post.setPostDate(startTime);
        postService.addPost(post);
        //Padding vote options
        for(VoteOption voteOption : voteItem.getVoteOptions()){
            String optionId = UUIDUtil.generateUUID();
            voteOption.setOptionId(optionId);
            voteOption.setPostId(postId);
            voteMapper.addVoteOption(voteOption);
        }
        //store postId to redis
        redisOperator.set("vote:" + postId, 0, interTime, TimeUnit.HOURS);
        return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_OK));
    }

}
