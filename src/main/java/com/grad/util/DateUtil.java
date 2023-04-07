package com.grad.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static Integer StringToTimestamp(String time){

        int times = -1;
        try {
            times = (int) ((Timestamp.valueOf(time).getTime())/1000);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return times;

    }
    public static String generateDate(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
