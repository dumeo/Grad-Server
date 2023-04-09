package com.grad.controller;

import com.grad.pojo.Post;
import com.grad.ret.Status;
import com.grad.service.PostService;
import com.grad.util.DefaultVals;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@Slf4j
public class PostsController {
    @Resource
    PostService postService;


    @GetMapping("/post")
    public String getPostById(@RequestParam("clientUid")String clientUid, @RequestParam("postId")String postId){
        return postService.getPostById(clientUid, postId);
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
//        log.info("cnt get id:" + postId);
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
            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_FAILED));
        }
    }

    @PostMapping("/post/like")
    public String setLikeStatus(@RequestParam("uid")String uid, @RequestParam("postId")String postId, @RequestParam("transferType")int transferType){
        try {
        return postService.setLikeStatus(uid, postId, transferType);
        }catch (Exception e){
            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_FAILED));
        }
    }

    @PostMapping("/post/collect/add")
    public String addCollect(@RequestParam("uid")String uid, @RequestParam("postId")String postId, @RequestParam("collectType")int collectType){
        try {
            return postService.addCollect(uid, postId, collectType);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_FAILED));
        }
    }

}
