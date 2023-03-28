package com.grad.controller;

import com.grad.pojo.Post;
import com.grad.ret.PostItem;
import com.grad.service.PostService;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class InformationController {
    @Resource
    PostService postService;

    @GetMapping("/posts")
    public String getPosts(){
        String json = postService.getPosts();
        return json;
    }

    @GetMapping("/posts/load-more")
    public String loadMorePosts(@RequestParam("startTime") String startTime){
        String json = postService.loadMorePosts(startTime);
        return json;
    }

    @PostMapping("/posts/new")
    public String newPost(@RequestBody Post post){
        return postService.newPost(post);
    }

    @PostMapping("/posts/new/upload-imgs")
    public String uploadImages(MultipartHttpServletRequest request) throws ServletException, IOException, MyException {
        return postService.storeImage(request);
    }
}
