package com.grad.service;

import com.grad.dao.ImageMapper;
import com.grad.dao.PostMapper;
import com.grad.dao.UserMapper;
import com.grad.pojo.Post;
import com.grad.pojo.PostImage;
import com.grad.pojo.User;
import com.grad.ret.PostItem;
import com.grad.util.DefaultVals;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public List<PostItem> getPosts(long startId, String sort, int count){
        List<Post> posts = postMapper.getPosts(startId, sort, count);
        List<PostItem> postItems = new ArrayList<>();
        for(Post post : posts)
            postItems.add(postToPostItem(post));
        return postItems;
    }

    private PostItem postToPostItem(Post post){
        long postId = post.getPostId();
        long uid = post.getUid();
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

    private List<String> getPostImages(long postId) {
        List<PostImage> postImages = imageMapper.selectImagesByPostId(postId);
        List<String> images = new ArrayList<>();
        for(PostImage postImage : postImages)
            images.add(postImage.getUrl());
        return images;
    }
}
