package com.utils;

public class Test2 {

    int x ;
    static int y ;

    public Test2(){
        System.err.println("2.构造方法");
    }
    static {
        System.err.println("2.静态变量");
    }

    {
        System.err.println("2.非静态变量");
    }
}
