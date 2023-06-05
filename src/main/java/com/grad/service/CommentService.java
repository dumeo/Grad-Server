package com.grad.service;

import com.grad.constants.CommitteeConstants;
import com.grad.constants.UserConstants;
import com.grad.dao.CommentMapper;
import com.grad.dao.UserMapper;
import com.grad.pojo.Comment;
import com.grad.pojo.CommentItem;
import com.grad.pojo.User;
import com.grad.ret.ClientToThisInfo;
import com.grad.ret.Status;
import com.grad.util.DateUtil;
import com.grad.constants.DefaultVals;
import com.grad.util.JsonUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    RedisTemplate redisTemplate;


    public ResponseEntity<Comment> addComment(Comment comment){
        //判断用户是否被禁言
        String uid = comment.getUid();
        User user = userMapper.getUserById(uid);
        String email = user.getEmail();
        Object obj = redisTemplate.opsForValue().get(CommitteeConstants.BAN_USER_PREFIX + email);
        if(obj != null) return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);

        String commentId = UUIDUtil.generateUUID();
        String commentDate = DateUtil.generateDate();
        comment.setCommentId(commentId);
        comment.setCommentDate(commentDate);
        //将评论实体内容存入数据库
        try{
            if(comment.getFatherId() != null){
                commentMapper.addCommentOnFather(comment);
            }
            else commentMapper.addComment(comment);

        return new ResponseEntity<>(comment, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }


    public String getCommentsByPostId(String clientUid, String postId){
        List<Comment> commentList = commentMapper.getCommentsByTime(postId);
        List<Comment> level0Comments = new ArrayList<>();
        List<CommentItem> res = new ArrayList<>();
        //加载0级评论
        for(Comment comment : commentList){
            if(comment.getFatherId() == null)
                level0Comments.add(comment);
        }

        for(Comment comment : level0Comments){
            String uid = comment.getUid();
            User user = userMapper.getUserById(uid);
            CommentItem commentItem = new CommentItem(comment, user);
            //以0级评论为根节点构造评论树
            buildSubCommentTree(clientUid, commentItem, commentList);
            res.add(commentItem);
        }

        return JsonUtil.objectToJson(res);
    }

    private void buildSubCommentTree(String clientUid,
                                     CommentItem fatherCommentItem,
                                     List<Comment> commentList) {
        Comment father = fatherCommentItem.getComment();
        if(fatherCommentItem.getClientToThisInfo() == null)
            fatherCommentItem.setClientToThisInfo(generateClientToThisInfo(clientUid,
                    father.getCommentId()));
        for(Comment comment : commentList){
            //判断评论comment是否是当前评论的子评论
            if(comment.getFatherId() != null
                    && comment.getFatherId().equals(father.getCommentId())){
                String uid = comment.getUid();
                User user = userMapper.getUserById(uid);
                CommentItem childCommentItem = new CommentItem(comment, user);
                //若comment是当前评论的子评论，以comment为根节点进行递归
                buildSubCommentTree(clientUid, childCommentItem, commentList);
                if(childCommentItem.getClientToThisInfo() == null){
                    childCommentItem.setClientToThisInfo(generateClientToThisInfo(clientUid,
                            comment.getCommentId()));
                }
                //将comment添加到当前评论的子评论列表
                fatherCommentItem.getChildComments().add(childCommentItem);

                fatherCommentItem.getChildComments().sort(new Comparator<CommentItem>() {
                    @Override
                    public int compare(CommentItem o1, CommentItem o2) {
                        if(DateUtil.StringToTimestamp(o1.getComment().getCommentDate()) < DateUtil.StringToTimestamp(o2.getComment().getCommentDate()))
                            return -1;
                        return 1;
                    }
                });
            }
        }
    }

    private ClientToThisInfo generateClientToThisInfo(String uid, String commentId){
        int likeStatus = -1;
        Object object = commentMapper.checkLikeStatus(uid, commentId);
        if(object != null) likeStatus = (int)(long) object;
        else likeStatus = DefaultVals.LIKE_STATUS_NOSTATUS;
        ClientToThisInfo clientToThisInfo = new ClientToThisInfo();
        clientToThisInfo.setLikeStatus(likeStatus);
        return clientToThisInfo;

    }


    public String setLikeStatus(String uid, String commentId, int transferType) {
        try{
            if(transferType == DefaultVals.LIKED_TO_DISLIKE){
                commentMapper.increaseCommentLikeCnt(commentId, -2);
                commentMapper.setUserLikeStatus(uid, commentId, DefaultVals.LIKE_STATUS_DISLIKED);
            }
            else if(transferType == DefaultVals.DISLIKED_TO_LIKE){
                commentMapper.increaseCommentLikeCnt( commentId, 2);
                commentMapper.setUserLikeStatus(uid, commentId, DefaultVals.LIKE_STATUS_LIKED);
            }
            else if(transferType == DefaultVals.LIKED_TO_NOSTATUS){
                commentMapper.increaseCommentLikeCnt(commentId, -1);
                commentMapper.deleteUserLikeStatus(uid, commentId);
            }
            else if(transferType == DefaultVals.DISLIKED_TO_NOSTATUS){
                commentMapper.increaseCommentLikeCnt(commentId, 1);
                commentMapper.deleteUserLikeStatus(uid, commentId);
            }
            else if(transferType == DefaultVals.NOSTATUS_TO_DISLIKE){
                commentMapper.increaseCommentLikeCnt(commentId, -1);
                commentMapper.addUserLikeStatus(uid, commentId, DefaultVals.LIKE_STATUS_DISLIKED);
            }
            else if(transferType == DefaultVals.NOSTATUS_TO_LIKE){
                commentMapper.increaseCommentLikeCnt(commentId, 1);
                commentMapper.addUserLikeStatus(uid, commentId, DefaultVals.LIKE_STATUS_LIKED);
            }

            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_OK));
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.objectToJson(new Status(DefaultVals.STATUS_FAILED));
        }
    }
}
