package com.grad.service;

import com.grad.constants.DefaultVals;
import com.grad.dao.*;
import com.grad.pojo.Comment;
import com.grad.ret.*;
import com.grad.pojo.Post;
import com.grad.pojo.ImageItem;
import com.grad.pojo.User;
import com.grad.util.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    @Resource
    FileService fileService;
    @Resource
    CommentMapper commentMapper;
    @Resource
    CollectMapper collectMapper;
    @Resource
    KafkaTemplate<String, String> kafkaTemplate;
    @Resource
    UserService userService;
    @Resource
    RecommMapper recommMapper;


    public String getPostCommentCnt(String postId){
        long commentCnt = commentMapper.getPostCommentCnt(postId);
        CommentCntRet commentCntRet = new CommentCntRet(commentCnt);
        return JsonUtil.objectToJson(commentCntRet);
    }

    public String getPostById(String clientUid, String postId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                userService.storeUserViewRecord(clientUid, postId);
            }
        }).start();
        Post post = postMapper.getPostById(postId);
        PostItem postItem = postToPostItem(post);
        ClientToThisInfo clientToThisInfo = generateClientToThisInfo(clientUid, postId);
        postItem.setClientToThisInfo(clientToThisInfo);
        return JsonUtil.objectToJson(postItem);
    }

    private ClientToThisInfo generateClientToThisInfo(String clientUid, String postId){
        Object likeStatus = postMapper.checkLikeStatus(clientUid, postId);
        ClientToThisInfo clientToThisInfo = new ClientToThisInfo();
        if(likeStatus != null) clientToThisInfo.setLikeStatus((int)(long)likeStatus);
        else clientToThisInfo.setLikeStatus(DefaultVals.LIKE_STATUS_NOSTATUS);
        Object isCollected = collectMapper.checkIsCollected(clientUid, postId);
        if(isCollected == null) clientToThisInfo.setCollected(false);
        else clientToThisInfo.setCollected(true);
        return clientToThisInfo;
    }


    public String getPosts(String postTag){
        List<PostItem> postItems = new ArrayList<>();
        List<Post> posts = postMapper.getPostsByNewest(postTag, DefaultVals.POST_ITEM_COUNT);
        for(Post post : posts)
            postItems.add(postToPostItem(post));
        String json = JsonUtil.objectToJson(postItems);
        return json;
    }

    public String loadMorePosts(String postTag, String startTime){
        List<PostItem> postItems = new ArrayList<>();
        List<Post> posts = postMapper.getMorePosts(postTag, startTime, DefaultVals.POST_ITEM_COUNT);
        for(Post post : posts)
            postItems.add(postToPostItem(post));
        String json = JsonUtil.objectToJson(postItems);
        return json;
    }


    public String addPost(Post post){
        String postId = UUIDUtil.generateUUID();
        post.setPostId(postId);
        String createDate = DateUtil.generateDate();
        post.setPostDate(createDate);
        //调用DAO接口存入数据库
        postMapper.addPost(post);
        //用户自己发布的帖子也需添加到浏览记录
        userService.storeUserViewRecord(post.getUid(), postId);
        //将信息ID传入kafka
        kafkaTemplate.send(DefaultVals.KAFKA_TOPIC, "key", "new post:" + postId);
        return "{\"postId\":" + "\"" + post.getPostId() + "\"" + "}";
    };



    public PostItem postToPostItem(Post post){
        String postId = post.getPostId();
        String uid = post.getUid();
        User user = userMapper.getUserById(uid);
        user.setPassword("");
        List<ImageItem> imageItems;
        imageItems = imageMapper.selectImagesByPostId(postId);
        paddingImageUrl(imageItems);
        PostInfo postInfo = new PostInfo();
        postInfo.setImageItems(imageItems);
        long commentCnt = commentMapper.getPostCommentCnt(postId);
        postInfo.setCommentCnt(commentCnt);
        PostItem res = new PostItem(post, user, null, postInfo);
        return res;
    }

    private void paddingImageUrl(List<ImageItem> imageItems){
        for(ImageItem imageItem : imageItems){
            if(!imageItem.getUrl().startsWith("http"))
                imageItem.setUrl(DefaultVals.FILE_SERVER_URL + imageItem.getUrl());
        }
    }


    public String setLikeStatus(String uid, String postId, int transferType) throws Exception {

            if(transferType == DefaultVals.LIKED_TO_DISLIKE){
//                log.info("LIKED_TO_DISLIKE");
                postMapper.increasePostLikeCnt(postId, -2);
                postMapper.setUserLikeStatus(uid, postId, DefaultVals.LIKE_STATUS_DISLIKED);
            }
            else if(transferType == DefaultVals.DISLIKED_TO_LIKE){
//                log.info("DISLIKED_TO_LIKE");
                postMapper.increasePostLikeCnt( postId, 2);
                postMapper.setUserLikeStatus(uid, postId, DefaultVals.LIKE_STATUS_LIKED);
            }
            else if(transferType == DefaultVals.LIKED_TO_NOSTATUS){
//                log.info("LIKED_TO_NOSTATUS");
                postMapper.increasePostLikeCnt(postId, -1);
                postMapper.deleteUserLikeStatus(uid, postId);
            }
            else if(transferType == DefaultVals.DISLIKED_TO_NOSTATUS){
//                log.info("DISLIKED_TO_NOSTATUS");
                postMapper.increasePostLikeCnt(postId, 1);
                postMapper.deleteUserLikeStatus(uid, postId);
            }
            else if(transferType == DefaultVals.NOSTATUS_TO_DISLIKE){
//                log.info("NOSTATUS_TO_DISLIKE");
                postMapper.increasePostLikeCnt(postId, -1);
                postMapper.addUserLikeStatus(uid, postId, DefaultVals.LIKE_STATUS_DISLIKED);
            }
            else if(transferType == DefaultVals.NOSTATUS_TO_LIKE){
//                log.info("NOSTATUS_TO_LIKE");
                postMapper.increasePostLikeCnt(postId, 1);
                postMapper.addUserLikeStatus(uid, postId, DefaultVals.LIKE_STATUS_LIKED);
            }
            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_OK));
    }

    public String addCollect(String uid, String postId, int collectType) throws Exception {
        if(collectType == DefaultVals.COLLECT_POST)
            collectMapper.addCollect(uid, postId);
        else collectMapper.deleteCollect(uid, postId);
        return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_OK));
    }


    public ResponseEntity<List<Post>> searchPost(String postTitle) {
        try {
            List<Post> posts = postMapper.searchPost(postTitle);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity deletePost(String postId) {
        try {
            collectMapper.deleteCollectByPostId(postId);
            List<Comment> commentList = commentMapper.getCommentsByPostId(postId);
            for(Comment comment : commentList){
                commentMapper.deleteUserLikeComment(comment.getCommentId());
                commentMapper.deleteCommentById(comment.getCommentId());
            }
            postMapper.deleteUserLikePost(postId);
            postMapper.deletePostById(postId);
            recommMapper.deleteRecommendByPostId(postId);
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
