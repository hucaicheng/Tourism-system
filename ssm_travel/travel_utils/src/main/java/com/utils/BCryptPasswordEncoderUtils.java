package com.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordEncoderUtils {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String BCryptPassword(String s){
        return bCryptPasswordEncoder.encode(s);
    }

    public static void main(String[] args) {
        String s = "123";
        String ss= BCryptPassword(s);
        System.out.println(ss);
    }
}
