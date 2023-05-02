package com.grad.controller;

import com.grad.constants.UserConstants;
import com.grad.pojo.User;
import com.grad.ret.Status;
import com.grad.ret.committee.NoteItem;
import com.grad.ret.communitynews.CommunityNews;
import com.grad.ret.reserve.ReserveItem;
import com.grad.service.UserService;
import com.grad.ret.RegisterRet;
import com.grad.constants.DefaultVals;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

import static java.lang.Thread.sleep;

@RestController
@Slf4j
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws InterruptedException {
        log.info("get user:" + user);
        try {
            User user_ = userService.registerUser(user);
            if(user_ == null){
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
            user_.setPassword("");
            return new ResponseEntity<>(user_, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/user/check")
    public ResponseEntity<Status> getUserById(@RequestParam("uid")String uid){
        try{
            User user = userService.checkUserById(uid);
            if(user != null) return new ResponseEntity<>(new Status(UserConstants.USER_EXISTS), HttpStatus.OK);
            return new ResponseEntity<>(new Status(UserConstants.USER_NOT_EXISTS), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<User> loginUser(@RequestParam("email")String email, @RequestParam("password")String password){

        try {
            User user = userService.loginUser(email, password);
            if(user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            user.setPassword("");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }


    }

    @GetMapping("/user/notes")
    public ResponseEntity<List<NoteItem>> getNotes(@RequestParam("communityName")String communityName){
        return userService.getNotes(communityName);
    }

    @PostMapping("/user/read-note")
    public ResponseEntity readNote(@RequestParam("noteId")String noteId){
        return userService.readNote(noteId);
    }

    @PostMapping("/user/add-reserve")
    public ResponseEntity addReserve(@RequestBody ReserveItem reserveItem){
        return userService.addReserve(reserveItem);
    }

    @GetMapping("/user/reserve")
    public ResponseEntity<List<ReserveItem>> getUserReserve(@RequestParam("uid")String uid){
        return userService.getUserReserve(uid);
    }

    @GetMapping("/user/get-newest-note")
    public ResponseEntity<NoteItem> getNewestNote(@RequestParam("communityName")String communityName){
        return userService.getNewestNote(communityName);
    }

    @GetMapping("/user/community-news")
    public ResponseEntity<List<CommunityNews>> getCommunityNews(@RequestParam("communityName")String communityName){
        return userService.getCommunityNews(communityName);
    }

}
