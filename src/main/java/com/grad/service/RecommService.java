package com.grad.service;

import com.grad.constants.RecommContants;
import com.grad.constants.UserConstants;
import com.grad.dao.PostMapper;
import com.grad.dao.RecommMapper;
import com.grad.dao.RedisOperator;
import com.grad.dao.bloomfilter.BloomFilter;
import com.grad.pojo.Post;
import com.grad.ret.PostItem;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommService {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    BloomFilter bloomFilter;
    @Resource
    PostService postService;
    @Resource
    RecommMapper recommMapper;
    @Resource
    PostMapper postMapper;
/*
* redis中有两种布隆过滤器，第一种是BF_RECOMMENDED_PREFIX，
* 第二种是BF_VIEW_RECORD_PREFIX。
* 第一中用来过滤那些已经推荐过的帖子，第二种用来过滤
* 用户已经浏览过的帖子。redis中有一个链表，
* 它记录用户最近点击过的帖子，容量限制20个
* */
    public List<PostItem> getRecommend(String uid){
        long len = redisTemplate.opsForList().size(RecommContants.LIST_VIEW_RECORD_PREFIX +uid);
        if(len == 0){
            return  new ArrayList<>();
        }
        List<Object> objects = redisTemplate.opsForList().range(RecommContants.LIST_VIEW_RECORD_PREFIX + uid, 0, (int) (len - 1));
        List<PostItem> res = new ArrayList<>();
        String bfRecommendedKey = RecommContants.BF_RECOMMENDED_PREFIX + uid;
        String bfViewedKey = RecommContants.BF_VIEW_RECORD_PREFIX + uid;
        for(Object obj : objects){
            String postId = (String) obj;
            List<String> recommIds = recommMapper.getPostRecomm(postId);
            for(String recommId : recommIds){
                if(!bloomFilter.contains(bfRecommendedKey, recommId) && !bloomFilter.contains(bfViewedKey, recommId)){
                    Post recommPost = postMapper.getPostById(recommId);
                    res.add(postService.postToPostItem(recommPost));
                    //已经推荐过，加到布隆过滤器中
                    bloomFilter.add(bfRecommendedKey, recommId);
                }
            }
        }
    return res;

    }

    //随机推荐：用户点击过的帖子和上一次随机推荐过的帖子都会过滤掉
//    public List<PostItem> randomRecomm(String uid){
//        Random r = new Random();
//        int cnt = postMapper.getPostTotalCnt();
//        int start = r.nextInt(cnt);
//        int end = Math.min(start + RecommContants.RANDOM_RECOMM_CNT, cnt - 1);
//        List<Post> posts = postMapper.getPostsByRange(start, end);
//        List<PostItem> res = new ArrayList<>();
//        String bfRecommendedKey = RecommContants.BF_RECOMMENDED_PREFIX + uid;
//        String bfViewedKey = RecommContants.BF_VIEW_RECORD_PREFIX + uid;
//        for(Post post : posts){
//            if(!bloomFilter.contains(bfRecommendedKey, post.getPostId()) &&
//                !bloomFilter.contains(bfViewedKey, post.getPostId())){
//                res.add(postService.postToPostItem(post));
//                bloomFilter.add(bfRecommendedKey, post.getPostId());
//            }
//
//        }
//        return res;
//    }

}













