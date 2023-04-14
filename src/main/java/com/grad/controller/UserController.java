package com.grad.controller;

import com.grad.constants.UserConstants;
import com.grad.pojo.User;
import com.grad.ret.Status;
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

import java.util.concurrent.locks.ReadWriteLock;

import static java.lang.Thread.sleep;

@RestController
@Slf4j
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws InterruptedException {
        try {
            User user_ = userService.registerUser(user);
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
    public ResponseEntity<User> loginUser(@RequestParam("username")String username, @RequestParam("password")String password){

        try {
            User user = userService.loginUser(username, password);
            if(user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            user.setPassword("");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }


    }

}
