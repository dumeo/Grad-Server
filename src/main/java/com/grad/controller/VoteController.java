package com.grad.controller;


import com.google.gson.JsonObject;
import com.grad.constants.DefaultVals;
import com.grad.ret.Status;
import com.grad.ret.vote.VoteItem;
import com.grad.service.VoteService;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.remote.JMXServerErrorException;

@RestController
@Slf4j
public class VoteController {
    @Resource
    VoteService voteService;

    @PostMapping("/vote/add")
    public String addVote(@RequestBody VoteItem voteItem){
        try {
            log.info("voteItem:" + voteItem);
            return voteService.addVote(voteItem);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_FAILED));
        }

    }
}
