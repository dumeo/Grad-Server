package com.grad.service;

import com.grad.dao.CommentMapper;
import com.grad.dao.PostMapper;
import com.grad.dao.UserMapper;
import com.grad.pojo.Comment;
import com.grad.pojo.CommentItem;
import com.grad.pojo.User;
import com.grad.util.DateUtil;
import com.grad.util.JsonUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    PostMapper postMapper;
    @Resource
    UserMapper userMapper;

    public String addComment(Comment comment){
        String commentId = UUIDUtil.generateUUID();
        String commentDate = DateUtil.generateDate();
        comment.setCommentId(commentId);
        comment.setCommentDate(commentDate);
        try{
        commentMapper.addComment(comment);
        return JsonUtil.objectToJson(comment);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.objectToJson(new Comment());
        }
    }

    public String getCommentsByPostId(String postId){
        List<Comment> commentList = commentMapper.getCommentsByTime(postId);
        List<CommentItem> commentItems = new ArrayList<>();
        for(Comment comment : commentList){
            String uid = comment.getUid();
            User user = userMapper.selectUserById(uid);
            commentItems.add(new CommentItem(comment, user));
        }

        return JsonUtil.objectToJson(commentItems);
    }


}
