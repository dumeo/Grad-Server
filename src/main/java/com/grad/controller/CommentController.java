package com.grad.controller;


import com.grad.pojo.Comment;
import com.grad.service.CommentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.GET;

@RestController
@Slf4j
public class CommentController {
    @Resource
    CommentService commentService;



    @PostMapping("/comment/add")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/comments")
    public String getComments(@RequestParam("clientUid")String clientUid, @RequestParam("postId")String postId){
        return commentService.getCommentsByPostId(clientUid, postId);
    }

    @PostMapping("/comment/like")
    public String setLikeStatus(@RequestParam("uid")String uid, @RequestParam("commentId")String commentId, @RequestParam("transferType")int transferType){
        return commentService.setLikeStatus(uid, commentId, transferType);
    }

}
