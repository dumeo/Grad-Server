package com.grad;

import com.grad.dao.ImageMapper;
import com.grad.dao.PostMapper;
import com.grad.dao.UserMapper;
import com.grad.pojo.Post;
import com.grad.pojo.PostImage;
import com.grad.pojo.User;
import com.grad.service.PostService;
import com.grad.util.DefaultVals;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Slf4j
class ServerApplicationTests {



    @Resource
    UserMapper userMapper;
    @Resource
    PostMapper postMapper;
    @Resource
    PostService postService;
    @Resource
    ImageMapper imageMapper;

    @Test
    void contextLoads() throws IOException {
//        userMapper.addUser(new User("gsdgdrfher", 0, "李四", "gfdbfd", "22222@qq.com",
//                "武汉社区", "三单元501", "www.ss.com", 0, "2023-03-15 00:45:11"));
//        User user = userMapper.selectUserById("gsdgdrfher");
//        System.out.println("get user from mybatis:" + user.toString());

    }

    @Test
    void addPostTest(){
//        for(int i = 0;i < 2; i ++){
//            postMapper.addPost(new Post(UUIDUtil.generateUUID(), "gsdgdrfher", 0, "测试标题", "测试内容",
//                    "shit", 1002, "2023-03-21 16:34:4" + i));
//        }
//        List<Post> res = postMapper.getPosts(DefaultVals.SORT_NEWEST, 20);
//        for(Post post : res){
//            log.info(post.toString());
//        }

    }

    @Test
    void addImageTest(){
//        for(int i = 0;i < 3; i ++){
//            imageMapper.addImage(new PostImage(1, "8587e548071641939be801e501c91487", i, "https://iso.500px.com/wp-content/uploads/2016/03/stock-photo-142984111-1500x1000.jpg"));
//        }
//        List<PostImage> res = imageMapper.selectImagesByPostId("8587e548071641939be801e501c91487");
//        for(PostImage postImage : res)
//            log.error(postImage.toString());
//        System.out.println(UUIDUtil.generateUUID());
//    }



}

}
