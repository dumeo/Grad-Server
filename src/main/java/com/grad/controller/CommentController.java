package com.grad.controller;


import com.grad.pojo.Comment;
import com.grad.service.CommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.GET;

@RestController
@Slf4j
public class CommentController {
    @Resource
    CommentService commentService;

    @PostMapping("/comment/add")
    public String addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/comment/{postId}")
    public String getComments(@PathVariable("postId")String postId){
        return commentService.getCommentsByPostId(postId);
    }

}
