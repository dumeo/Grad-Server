package com.grad.controller;

import com.grad.pojo.Post;
import com.grad.service.PostService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@Slf4j
public class PostsController {
    @Resource
    PostService postService;

    @GetMapping("/post/{postId}")
    public String getPostById(@PathVariable("postId")String postId){
        return postService.getPostById(postId);
    }



    @GetMapping("/posts")
    public String getPosts(){
        String json = postService.getPosts();
        return json;
    }

    @GetMapping("/post/load-more")
    public String loadMorePosts(@RequestParam("startTime") String startTime){
        String json = postService.loadMorePosts(startTime);
        return json;
    }

    @GetMapping("/post/{postId}/comment-cnt")
    public String getPostCommentCnt(@PathVariable("postId")String postId){
        String json = postService.getPostCommentCnt(postId);
        return json;
    }




    @PostMapping("/post/new")
    public String newPost(@RequestBody Post post){
        return postService.newPost(post);
    }

    @PostMapping("/post/new/upload-imgs")
    public String uploadImages(MultipartHttpServletRequest request) {
        try {
            return postService.storeImage(request);
        }catch (Exception e){
            e.printStackTrace();
            return "{\"status\":\"upload failed\"}";
        }


    }
}
