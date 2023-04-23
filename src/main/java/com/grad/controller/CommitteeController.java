package com.grad.controller;

import com.grad.ret.committee.NoteItem;
import com.grad.service.CommitteeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class CommitteeController {
    @Resource
    CommitteeService committeeService;

    @PostMapping("/committee/upload-note")
    public ResponseEntity uploadNote(@RequestParam("communityName") String communityName, @RequestParam("content")String content){
        return committeeService.uploadNote(communityName, content);
    }





}
