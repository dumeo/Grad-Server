package com.grad.controller;

import com.grad.ret.PostItem;
import com.grad.service.RecommService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class RecommController {
    @Resource
    RecommService recommService;

    @GetMapping("/recommend")
    public ResponseEntity<List<PostItem>> getRecommend(@RequestParam("uid")String uid){
        try{
            List<PostItem> postItems = recommService.getRecommend(uid);
            return new ResponseEntity<>( postItems,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
}
