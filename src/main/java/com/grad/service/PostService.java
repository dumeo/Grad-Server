package com.grad.service;

import com.grad.dao.ImageMapper;
import com.grad.dao.PostMapper;
import com.grad.dao.UserMapper;
import com.grad.pojo.Post;
import com.grad.pojo.ImageItem;
import com.grad.pojo.User;
import com.grad.ret.PostItem;
import com.grad.util.DefaultVals;
import com.grad.util.JsonUtil;
import com.grad.util.StringTool;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.csource.common.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
        log.info("post tag = " + post.getPostTag());
        post.setPostId(UUIDUtil.generateUUID());
        String createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        post.setPostDate(createDate);
        postMapper.addPost(post);
        return "{\"postId\":" + "\"" + post.getPostId() + "\"" + "}";
    };










    private PostItem postToPostItem(Post post){
        String postId = post.getPostId();

        String uid = post.getUid();
        long postType = post.getPostType();
        User user = userMapper.selectUserById(uid);
        List<String> mediaUrls = new ArrayList<>(); //mediaUrl有可能是图片类的url，也可能是视频类的url
        if(postType == DefaultVals.POST_TYPE_IMG){
            mediaUrls = getPostImages(postId);
        }
        PostItem res = new PostItem(postId, uid, post.getPostType(),
                user.getAvatarUrl(),
                user.getUsername(), user.getHouseAddr() ,
                mediaUrls,post.getPostTitle(),
                post.getPostContent(), post.getPostTag(),
                post.getViewTimes(), post.getPostDate());

        return res;
    }

    private List<String> getPostImages(String postId) {
        List<ImageItem> imageItems = imageMapper.selectImagesByPostId(postId);
        List<String> images = new ArrayList<>();
        for(ImageItem imageItem : imageItems)
            images.add(imageItem.getUrl());
        return images;
    }

    public String storeImage(MultipartHttpServletRequest request) throws IOException, ServletException, MyException {
        String fileName = request.getPart("file").getSubmittedFileName();
        String postId = StringTool.parsePostId(fileName);
        long imgOrder = Integer.parseInt(StringTool.parseOrder(fileName));
        log.info("posId = " + postId + "\nimageOrder = " + imgOrder);
        String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
        InputStream sin = request.getFile("file").getInputStream();
        String[] fileAddr = fileService.client.upload_file(sin.readAllBytes(), extName, null);
        sin.close();
        String fileUrl = fileService.getFileAddr(fileAddr);
        imageMapper.addImage(new ImageItem(1, postId, imgOrder, fileUrl));
        postMapper.setPostType(postId, DefaultVals.POST_TYPE_IMG);
        return "{\"hello\":1}";
    }
}
