package com.grad;

import com.grad.dao.PostMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostTest {
    @Resource
    PostMapper postMapper;
    @Test public void test(){

    }
}
