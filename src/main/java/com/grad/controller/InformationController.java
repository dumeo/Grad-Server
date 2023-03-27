package com.grad.controller;

import com.grad.pojo.Post;
import com.grad.pojo.PostImage;
import com.grad.ret.PostItem;
import com.grad.service.PostService;
import com.grad.util.DefaultVals;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.websocket.server.PathParam;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
        List<PostItem> res = postService.getPosts();
        String json = JsonUtil.objectToJson(res);
        return json;
    }

    @PostMapping("/posts/new")
    public String newPost(@RequestBody Post post){
        return postService.newPost(post);
    }

    @PostMapping("/posts/new/upload-imgs")
    public String uploadImages(MultipartHttpServletRequest request) throws ServletException, IOException {
        log.info("get request!!");
        return postService.storeImage(request);
    }
}
