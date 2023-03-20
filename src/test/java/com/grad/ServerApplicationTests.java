package com.grad;

import com.grad.dao.UserMapper;
import com.grad.pojo.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ServerApplicationTests {



    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() throws IOException {
        User user = userMapper.selectUserById(1);
        System.out.println("get user from mybatis:" + user.toString());
        userMapper.addUser(new User("李四", "gfdbfd", "22222@qq.com",
                "武汉社区", "三单元501", "www.ss.com", 0, "2023-03-15 00:45:11"));

    }

}
