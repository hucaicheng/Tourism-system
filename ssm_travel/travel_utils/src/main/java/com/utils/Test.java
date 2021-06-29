package com.utils;


/**
 * 静态变量与非静态变量理解案例
 */
public class Test  extends  Test2{

    public Test(){
        System.err.println("构造方法");
    }
    static {
        System.err.println("静态变量");
    }

    {
        System.err.println("非静态变量");
    }

    public static void main(String[] args) {
        System.err.println("start");
        Test test = new Test();
        Test test2 = new Test();
        System.err.println("end");

        //静态变量（类变量）访问不需要实例对象
        //实例变量（成员变量）必须通过实例对象来访问
        Test2.y = 100;
        test2.y = 200;
        System.err.println(test.y);
        System.err.println(test2.y);

        System.out.println(People.LISI.getName());
    }
}
