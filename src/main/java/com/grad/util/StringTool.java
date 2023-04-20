package com.grad.util;

public class StringTool {
    public static String parsePostId(String s){
        for(int i = 0;i < s.length(); i ++){
            if(s.charAt(i) == ':'){
                return s.substring(0, i);
            }
        }
        return null;
    }

    public static String parseOrder(String s){
        boolean got = false;
        int i = -1;
        for(int k = 0; k < s.length(); k ++){
            if(!got && s.charAt(k) == ':'){
                got = true;
                i = k;
            }
            else if(got && s.charAt(k) == ':'){
                return s.substring(i + 1, k);
            }
        }
        return null;
    }


}
