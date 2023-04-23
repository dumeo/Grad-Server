package com.grad.service;

import com.grad.dao.CommitteeMapper;
import com.grad.ret.committee.NoteItem;
import com.grad.util.DateUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommitteeService {
    @Resource
    CommitteeMapper committeeMapper;

    public ResponseEntity uploadNote(String communityName, String content){
        try{
            String noteId = UUIDUtil.generateUUID();
            String createDate = DateUtil.generateDate();
            committeeMapper.addNote(new NoteItem(noteId, communityName, content, 0, createDate));
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


}
