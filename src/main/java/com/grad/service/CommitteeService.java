package com.grad.service;

import com.grad.constants.CommitteeConstants;
import com.grad.dao.CommitteeMapper;
import com.grad.ret.committee.NoteItem;
import com.grad.util.DateUtil;
import com.grad.util.JsonUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
