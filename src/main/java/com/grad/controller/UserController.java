package com.grad.controller;

import com.grad.pojo.User;
import com.grad.ret.Status;
import com.grad.service.UserService;
import com.grad.ret.RegisterRet;
import com.grad.util.DefaultVals;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static java.lang.Thread.sleep;

@RestController
@Slf4j
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/user/register")
    public String registerUser(@RequestBody User user) throws InterruptedException {
        RegisterRet res = userService.registerUser(user);
        return JsonUtil.objectToJson(res);
    }

    @GetMapping("/user/check")
    public String getUserById(@RequestParam("uid")String uid){
        try{
            return userService.checkUserById(uid);
        }catch (Exception e){
            e.printStackTrace();
            Status status = new Status(DefaultVals.STATUS_FAILED);
            status.setMsg(DefaultVals.MSG_SERVER_ERROR);
            return JsonUtil.objectToJson(status);
        }
    }

}
