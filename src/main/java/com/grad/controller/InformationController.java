package com.grad.controller;

import com.grad.ret.PostItem;
import com.grad.service.PostService;
import com.grad.util.DefaultVals;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class InformationController {
    @Resource
    PostService postService;

    @GetMapping("/posts")
    public String getPosts(@PathParam("sort") String sort, @PathParam("startId") long startId){
        List<PostItem> res = postService.getPosts(startId, sort);
        String json = JsonUtil.objectToJson(res);
        return json;
    }
}
