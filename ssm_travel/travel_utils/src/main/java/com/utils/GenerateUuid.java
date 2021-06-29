package com.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("Generate")
public class GenerateUuid {

//    public static void main(String[] args) {
//        System.err.println(Uuid());
//
//    }
    //自动生成32位字符串
    public  String Uuid(){
        UUID uuid = UUID.randomUUID();
        String guid = uuid.toString().replaceAll("[-]","");
        return guid;
    }


}
