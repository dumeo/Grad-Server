package com.grad.service;

import com.grad.dao.CommentMapper;
import com.grad.dao.ImageMapper;
import com.grad.dao.PostMapper;
import com.grad.dao.UserMapper;
import com.grad.pojo.Post;
import com.grad.pojo.ImageItem;
import com.grad.pojo.User;
import com.grad.ret.CommentCntRet;
import com.grad.ret.PostItem;
import com.grad.ret.PostUserInfo;
import com.grad.util.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PostService {
    @Resource
    PostMapper postMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ImageMapper imageMapper;
    @Resource
    FileService fileService;
    @Resource
    CommentMapper commentMapper;

    public String getPostCommentCnt(String postId){
        long commentCnt = commentMapper.getPostCommentCnt(postId);
        log.info("cnt = " + commentCnt);
        CommentCntRet commentCntRet = new CommentCntRet(commentCnt);
        return JsonUtil.objectToJson(commentCntRet);
    }

    public String getPostById(String postId){
        Post post = postMapper.getPostById(postId);
        PostItem postItem = postToPostItem(post);
        List<ImageItem> imageItems = postItem.getImageItems();
        return JsonUtil.objectToJson(postItem);
    }


    public String getPosts(){
        List<PostItem> postItems = new ArrayList<>();
        List<Post> posts = postMapper.getPostsByNewest(DefaultVals.POST_ITEM_COUNT);
        for(Post post : posts)
            postItems.add(postToPostItem(post));
        String json = JsonUtil.objectToJson(postItems);
        return json;
    }

    public String loadMorePosts(String startTime){
        List<PostItem> postItems = new ArrayList<>();
        List<Post> posts = postMapper.getMorePosts(startTime, DefaultVals.POST_ITEM_COUNT);
        for(Post post : posts)
            postItems.add(postToPostItem(post));
        String json = JsonUtil.objectToJson(postItems);
        return json;
    }


    public String newPost(Post post){
        post.setPostId(UUIDUtil.generateUUID());
        String createDate = DateUtil.generateDate();
        post.setPostDate(createDate);
        postMapper.addPost(post);
        return "{\"postId\":" + "\"" + post.getPostId() + "\"" + "}";
    };



    private PostItem postToPostItem(Post post){
        String postId = post.getPostId();
        String uid = post.getUid();
        User user = userMapper.selectUserById(uid);
        PostUserInfo postUserInfo = new PostUserInfo(user.getAvatarUrl(), user.getUsername(), user.getHouseAddr());
        List<ImageItem> imageItems;
        imageItems = imageMapper.selectImagesByPostId(postId);
        PostItem res = new PostItem(post, postUserInfo, imageItems);

        return res;
    }



    public String storeImage(MultipartHttpServletRequest request) throws IOException, ServletException, MyException {
        String fileName = request.getPart("file").getSubmittedFileName();
        String postId = StringTool.parsePostId(fileName);
        long imgOrder = Integer.parseInt(StringTool.parseOrder(fileName));
        log.info("posId = " + postId + "\nimageOrder = " + imgOrder);
        String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
        InputStream ins = request.getFile("file").getInputStream();

        byte[] bytes = ins.readAllBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage sourceImg = ImageIO.read(inputStream);
        long width = sourceImg.getWidth();   // 源图宽度
        long height = sourceImg.getHeight();   // 源图高度

        String[] fileAddr = fileService.client.upload_file(bytes, extName, null);
        String fileUrl = fileService.generateFileUrl(fileAddr);
        imageMapper.addImage(new ImageItem(1, postId, imgOrder, fileUrl, width, height));
        postMapper.setPostType(postId, DefaultVals.POST_TYPE_IMG);
        ins.close();
        inputStream.close();
        return "{\"status\":\"upload success\"}";
    }

}
