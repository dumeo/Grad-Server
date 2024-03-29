package com.grad.service;

import com.grad.constants.CommitteeConstants;
import com.grad.constants.DefaultVals;
import com.grad.constants.RecommContants;
import com.grad.constants.UserConstants;
import com.grad.dao.CommitteeMapper;
import com.grad.dao.UserMapper;
import com.grad.dao.bloomfilter.BloomFilter;
import com.grad.pojo.User;
import com.grad.ret.Status;
import com.grad.ret.committee.NoteItem;
import com.grad.ret.communitynews.CommunityNews;
import com.grad.ret.reserve.ReserveItem;
import com.grad.util.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    BloomFilter bloomFilter;
    @Resource
    CommitteeMapper committeeMapper;
    @Resource
    FileService fileService;

    public User registerUser(User user){
        //检测是否存在邮箱相同的用户
        if(checkEmailExists(user.getEmail())) return null;
        //填充用户信息
        String createDate = DateUtil.generateDate();
        String uid = UUIDUtil.generateUUID();
        user.setUid(uid);
        user.setCommunityName(UserConstants.DEFAULT_COMMUNITY);
        user.setHouseAddr(UserConstants.DEFAULT_HOUSE_ADDR);
        user.setAvatarUrl(DefaultVals.DEFAULT_AVATAR);
        user.setCreateDate(createDate);
        //将注册用户存入数据库
        userMapper.addUser(user);
        User user_ = userMapper.getUserById(uid);
        return user_;
    }

    private boolean checkEmailExists(String email) {
        User user = userMapper.getUserByEmail(email);
        if(user != null) return true;
        return false;
    }

    public User checkUserById(String uid) throws  Exception{
        User user = userMapper.getUserById(uid);
        return user;
    }

    public ResponseEntity<User> loginUser(String email, String password) {
        try{
            //根据邮箱取出数据库中的用户
            User user = userMapper.getUserByEmail(email);
            //密码比对
            if(user == null || !user.getPassword().equals(password)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            user.setPassword("");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public void storeUserViewRecord(String uid, String postId){
        //用户已经浏览该贴，添加到布隆过滤器中
        if(!bloomFilter.contains(RecommContants.BF_VIEW_RECORD_PREFIX + uid, postId))
            bloomFilter.add(RecommContants.BF_VIEW_RECORD_PREFIX + uid, postId);
        //如果链表中的浏览数据达到20个，则将最后一个数据删除
        if(redisTemplate.opsForList().size(RecommContants.LIST_VIEW_RECORD_PREFIX+uid) == RecommContants.MAX_LIST_VIEW_RECORD){
            redisTemplate.opsForList().rightPop(RecommContants.LIST_VIEW_RECORD_PREFIX+uid);
        }
        //添加浏览记录到redis链表中
        redisTemplate.opsForList().leftPush(RecommContants.LIST_VIEW_RECORD_PREFIX+uid, postId);
    }

    public ResponseEntity<List<NoteItem>> getNotes(String communityName){
        try{
            List<NoteItem> noteItems = userMapper.getNotes(communityName);
            return new ResponseEntity<>(noteItems, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity readNote(String noteId) {
        try {
            userMapper.readNote(noteId);
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity addReserve(ReserveItem reserveItem) {
        try {
            String reserveId = UUIDUtil.generateUUID();
            reserveItem.setReserveId(reserveId);
            String currDate = DateUtil.generateDate();
            String reserveDate = reserveItem.getReserveDate();
            String uid = reserveItem.getUid();
            //预约信息
            String content = reserveItem.getReserveContent() + "|"
                    + reserveItem.getPhoneNum()
                    + "|"
                    + reserveItem.getReserveDate();
            //以预约信息为内容生成二维码
            byte[] imgQRCodeBytes = QRCodeUtil.generateQRCode(content);
            //将二维码存入FastDFS文件系统
            String[] fileAddr = fileService.client.upload_file(imgQRCodeBytes, "png", null);
            String fileUrl = fileService.generateFileUrl(fileAddr);
            String qrUrl = fileUrl;
            reserveItem.setQrUrl(qrUrl);
            //以二维码地址作为redis的val
            String redisVal = JsonUtil.objectToJson(reserveItem);
            //计算预约时间当前时间的时间差
            long diffMinutes = DateUtil.getDateMinuteInter(reserveDate, currDate);
            //存入数据库, 为了以后将过期的预约的图片从fdfs中删掉
            userMapper.addReserve(reserveId, uid, qrUrl);
            //写入redis
            redisTemplate.opsForValue().set(UserConstants.REDIS_RESERVE_PREFIX + uid + reserveId,
                    redisVal, diffMinutes, TimeUnit.MINUTES);
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseEntity.EMPTY, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }
    }

    public ResponseEntity<List<ReserveItem>> getUserReserve(String uid) {
        try {
            Set<String> keySet = redisTemplate.keys((UserConstants.REDIS_RESERVE_PREFIX + uid).concat("*"));
            List<String> keys = new ArrayList<>(keySet);
            List<ReserveItem> res = new ArrayList<>();
            for(String key : keys){
                String val = (String) redisTemplate.opsForValue().get(key);
                ReserveItem reserveItem = JsonUtil.jsonToObject(val, ReserveItem.class);
                String qrUrl = reserveItem.getQrUrl();
                if(!qrUrl.startsWith("http:"))
                    reserveItem.setQrUrl(DefaultVals.FILE_SERVER_URL + qrUrl);
                res.add(reserveItem);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    public ResponseEntity<NoteItem> getNewestNote(String communityName) {
        try {
            String resStr = (String) redisTemplate.opsForValue().get(CommitteeConstants.COMM_NEWEST_NOTE_PREFIX + communityName);
            NoteItem res = JsonUtil.jsonToObject(resStr, NoteItem.class);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    public ResponseEntity<List<CommunityNews>> getCommunityNews(String communityName) {
        try {
            List<CommunityNews> communityNewsList = userMapper.getCommunityNews(communityName);
            return new ResponseEntity<>(communityNewsList, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    public ResponseEntity<Status> checkBanned(String email) {
        try {
            Object obj = redisTemplate.opsForValue().get(CommitteeConstants.BAN_USER_PREFIX + email);
            if(obj != null){
                return new ResponseEntity<>(new Status(UserConstants.USER_BANNED, obj.toString()), HttpStatus.OK);
            }
            return new ResponseEntity<>(new Status(UserConstants.USER_NOT_BANNED), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
