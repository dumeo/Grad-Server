package com.grad.util;

import java.util.UUID;

import static java.util.UUID.randomUUID;

public class UUIDUtil {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
