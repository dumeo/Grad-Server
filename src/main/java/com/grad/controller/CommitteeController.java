package com.grad.controller;

import com.grad.ret.committee.NoteItem;
import com.grad.ret.communitynews.CommunityNews;
import com.grad.service.CommitteeService;
import io.lettuce.core.RedisCommandTimeoutException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class CommitteeController {
    @Resource
    CommitteeService committeeService;
    @Resource
    RedisTemplate redisTemplate;

    @PostMapping("/committee/upload-note")
    public ResponseEntity uploadNote(@RequestParam("communityName") String communityName, @RequestParam("content")String content){
        return committeeService.uploadNote(communityName, content);
    }

    @PostMapping("/committee/upload-news")
    public ResponseEntity uploadNews(@RequestBody CommunityNews communityNews){
        return committeeService.uploadNews(communityNews);
    }

    @PostMapping("/committee/incr-news-view-cnt")
    public ResponseEntity increaseNewsViewCnt(@RequestParam("newsId")String newsId){
        return committeeService.increaseNewsViewCnt(newsId);
    }





}
