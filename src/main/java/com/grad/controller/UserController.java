package com.grad.controller;

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
    @Resource
    UserService userService;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) throws InterruptedException {
        RegisterRet res = userService.registerUser(user);
        return JsonUtil.objectToJson(res);
    }

}
