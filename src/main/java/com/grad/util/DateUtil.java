package com.grad.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    public static long getDateInter(String date1, String date2) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null, d2 = null;
        d1 = (Date) format.parse(date1);
        d2 = (Date) format.parse(date2);
        //毫秒ms
        long diff = d2.getTime() - d1.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        return diffHours < 12 ? 12 : diffHours;
    }
}
