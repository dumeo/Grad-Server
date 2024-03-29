package com.grad.controller;


import com.grad.constants.DefaultVals;
import com.grad.ret.Status;
import com.grad.ret.vote.VoteItem;
import com.grad.service.VoteService;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class VoteController {
    @Resource
    VoteService voteService;

    @PostMapping("/vote/add")
    public String addVote(@RequestBody VoteItem voteItem){
        try {
            return voteService.addVote(voteItem);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_FAILED));
        }

    }

    @GetMapping("/vote/newest")
    public ResponseEntity<List<VoteItem>> getVotesByNewest(){
        try {
           List<VoteItem> voteItemList = voteService.getVoteByNewest();
           return new ResponseEntity<>(voteItemList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/vote/more")
    public ResponseEntity<List<VoteItem>> getHistoryVotes(@RequestParam("createDate")String createDate){
        try {
            List<VoteItem> voteItemList = voteService.getMoreVotes(createDate);
            return new ResponseEntity<>(voteItemList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/vote")
    public ResponseEntity<VoteItem> getVoteById(@RequestParam("clientUid")String clientUid, @RequestParam("voteId")String voteId){
        try{
            VoteItem voteItem = voteService.getVoteById(clientUid, voteId);
//            log.info("return voteItem:" + voteItem.toString());
            return new ResponseEntity<>(voteItem, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @PostMapping("/vote/to-vote")
    public ResponseEntity<VoteItem> toVote(@RequestParam("uid")String uid, @RequestParam("voteId")String voteId, @RequestParam("optionId")String optionId){
        try {
            VoteItem voteItem = voteService.vote(uid, voteId, optionId);
//            log.info("return voteItem:" + voteItem.toString());
            return new ResponseEntity<>(voteItem, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

}
