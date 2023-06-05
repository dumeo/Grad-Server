package com.grad.service;

import com.grad.constants.CommitteeConstants;
import com.grad.dao.CommitteeMapper;
import com.grad.ret.committee.NoteItem;
import com.grad.ret.communitynews.CommunityNews;
import com.grad.util.DateUtil;
import com.grad.util.JsonUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CommitteeService {
    @Resource
    CommitteeMapper committeeMapper;
    @Resource
    RedisTemplate redisTemplate;
    public ResponseEntity uploadNote(String communityName, String content){
        try{
            String noteId = UUIDUtil.generateUUID();
            String createDate = DateUtil.generateDate();
            NoteItem noteItem = new NoteItem(noteId, communityName, content, 0, createDate);
            committeeMapper.addNote(noteItem);
            redisTemplate.opsForValue().set(CommitteeConstants.COMM_NEWEST_NOTE_PREFIX + communityName, JsonUtil.objectToJson(noteItem));
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    public ResponseEntity uploadNews(CommunityNews communityNews) {
        try{
            String newsId = UUIDUtil.generateUUID();
            String createDate = DateUtil.generateDate();
            communityNews.setNewsId(newsId);
            communityNews.setCreateDate(createDate);
            committeeMapper.addNews(communityNews);
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity increaseNewsViewCnt(String newsId){
        try {
            committeeMapper.increaseNewsViewCnt(newsId);
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity banUser(String email, int days) {
        try {
            String date = DateUtil.generateDate();
            redisTemplate.opsForValue().set(CommitteeConstants.BAN_USER_PREFIX + email, date + "=" + days, days, TimeUnit.DAYS);
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
