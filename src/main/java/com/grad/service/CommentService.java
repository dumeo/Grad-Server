package com.grad.service;

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


    public String addComment(Comment comment){
        String commentId = UUIDUtil.generateUUID();
        String commentDate = DateUtil.generateDate();
        comment.setCommentId(commentId);
        comment.setCommentDate(commentDate);
        try{
            if(comment.getFatherId() != null){
                commentMapper.addCommentOnFather(comment);
            }
            else commentMapper.addComment(comment);

        return JsonUtil.objectToJson(comment);
        }catch (Exception e){
            e.printStackTrace();
            return JsonUtil.objectToJson(new Comment());
        }
    }

    public String getCommentsByPostId2(String postId){
        List<Comment> commentList = commentMapper.getCommentsByTime(postId);
        List<CommentItem> commentItems = new ArrayList<>();
        for(Comment comment : commentList){
            String uid = comment.getUid();
            User user = userMapper.getUserById(uid);
            commentItems.add(new CommentItem(comment, user));
        }

        return JsonUtil.objectToJson(commentItems);
    }

    public String getCommentsByPostId(String clientUid, String postId){
        List<Comment> commentList = commentMapper.getCommentsByTime(postId);
        List<Comment> level0Comments = new ArrayList<>();
        List<CommentItem> res = new ArrayList<>();
        for(Comment comment : commentList){
            if(comment.getFatherId() == null)
                level0Comments.add(comment);
        }

        for(Comment comment : level0Comments){
            String uid = comment.getUid();
            User user = userMapper.getUserById(uid);
            CommentItem commentItem = new CommentItem(comment, user);
            buildSubCommentTree(clientUid, commentItem, commentList);
            res.add(commentItem);
        }

        return JsonUtil.objectToJson(res);
    }

    private void buildSubCommentTree(String clientUid, CommentItem fatherCommentItem, List<Comment> commentList) {
        Comment father = fatherCommentItem.getComment();
        if(fatherCommentItem.getClientToThisInfo() == null)
            fatherCommentItem.setClientToThisInfo(generateClientToThisInfo(clientUid, father.getCommentId()));
        for(Comment comment : commentList){
            if(comment.getFatherId() != null && comment.getFatherId().equals(father.getCommentId())){
                String uid = comment.getUid();
                User user = userMapper.getUserById(uid);
                CommentItem childCommentItem = new CommentItem(comment, user);
                buildSubCommentTree(clientUid, childCommentItem, commentList);
                if(childCommentItem.getClientToThisInfo() == null){
                    childCommentItem.setClientToThisInfo(generateClientToThisInfo(clientUid, comment.getCommentId()));
                }
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
