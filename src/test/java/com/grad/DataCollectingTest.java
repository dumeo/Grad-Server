package com.grad;

import com.grad.constants.DefaultVals;
import com.grad.constants.UserConstants;
import com.grad.dao.ImageMapper;
import com.grad.dao.PostMapper;
import com.grad.dao.UserMapper;
import com.grad.dao.bloomfilter.BloomFilterOp;
import com.grad.pojo.User;
import com.grad.util.DateUtil;
import com.grad.util.UUIDUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class DataCollectingTest {
    @Resource
    UserMapper userMapper;
    @Resource
    PostMapper postMapper;
    @Resource
    ImageMapper imageMapper;
    @Resource
    BloomFilterOp bloomFilterOp;
    @Test
    void createUserTest(){
//        for(int i = 0;i < 100; i ++){
//            User user = new User(UUIDUtil.generateUUID(), 0,
//                    "用户"+UUIDUtil.generateUUID().substring(28),
//                    "123", UUIDUtil.generateUUID().substring(26) + "@qq.com",
//                    UserConstants.DEFAULT_COMMUNITY, UserConstants.DEFAULT_HOUSE_ADDR,
//                    DefaultVals.DEFAULT_AVATAR, 0, DateUtil.generateDate());
//            userMapper.addUser(user);
//        }

        bloomFilterOp.add("test", "123");
        System.out.println(bloomFilterOp.contains("test", "123"));

    }

}
