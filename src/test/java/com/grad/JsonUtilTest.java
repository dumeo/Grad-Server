package com.grad;


import com.grad.pojo.User;
import com.grad.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonUtilTest {
    @Test
    public void test(){
        User user = JsonUtil.jsonToObject("{\"uid\":1,\"username\":\"李四\",\"password\":\"gfdbfd\",\"email\":\"22222@qq.com\",\"communityName\":\"武汉社区\",\"avatarUrl\":\"www.ss.com\",\"emailValid\":0,\"createDate\":\"2023-03-15 00:45:11\"}", User.class);
        System.out.println(user);
        System.out.println("-----------------------------------");
        User user2 = new User("李四", "gfdbfd", "22222@qq.com",
                "武汉社区", "www.ss.com", 0, "2023-03-15 00:45:11");
        System.out.println(JsonUtil.objectToJson(user2));
    }
}
