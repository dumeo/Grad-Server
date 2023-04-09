package com.grad;

import com.grad.dao.CollectMapper;
import jakarta.annotation.Resource;
import org.intellij.lang.annotations.RegExp;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CollectTest {
    @Resource
    CollectMapper collectMapper;

    @Test
    public void test(){

    }
}
