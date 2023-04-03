package com.grad;

import com.grad.dao.CommentMapper;
import com.grad.dao.PostMapper;
import com.grad.pojo.Comment;
import com.grad.service.PostService;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CommentTest {
    @Resource
    CommentMapper commentMapper;


    @Test
    void contextLoads(){
//        Comment comment = new Comment(UUIDUtil.generateUUID(), "5e7aea7876594ffbbc556bee0ad57587",
//                "05b7686a40b5497ab8c9ac7701e85008", "comment test",
//                0, null, 0, "2023-04-03 12:12:52");
//        commentMapper.addComment(comment);
//        List<Comment> comments = commentMapper.getCommentsByTime("05b7686a40b5497ab8c9ac7701e85008");
//        for(Comment comm : comments){
//            System.out.println(comm);
//        }

    }
}
