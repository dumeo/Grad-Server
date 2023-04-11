package com.grad.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateUtil {

    public static long StringToTimestamp(String time){

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

    public static long getDateInter(String date1, String date2) throws Exception{
        long x1 = StringToTimestamp(date1), x2 = StringToTimestamp(date2);
        long diff = x2 - x1;
        long diffHours = diff / (60 * 60);
        return diffHours < 1 ? 1 : diffHours;
    }
}
