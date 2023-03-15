package com.grad.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.grad.pojo.User;
import com.grad.service.UserService;
import com.grad.ret.RegisterRet;
import com.grad.util.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    public static  int cnt = 0;
    @Resource
    UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) throws InterruptedException {
        RegisterRet res = userService.registerUser(user);
        return JsonUtil.objectToJson(res);
    }














    @GetMapping("index")
    public String index(){
        cnt ++;
        System.out.println("get request:" + cnt);
        User user = new User(1,"李四", "gfdbfd", "22222@qq.com",
                "武汉社区", "www.ss.com", 0, "2023-03-15 00:45:11");
        return JsonUtil.objectToJson(user);
    }
}
