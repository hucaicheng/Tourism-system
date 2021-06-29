package com.utils;

public enum People {

    //枚举就是对象,全部采用大写命名，而且是常量
    //定义枚举项的时候会使用构造函数
    ZHANGSAN("张三","男"),
    LISI("李四","女");

    //名字
    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private People(){

    }
    private  People(String name ,String sex){
        this.name = name;
        this.sex = sex;
    }

    public static void main(String[] args) {

        //枚举就是对象(实例对象）
        System.out.println(LISI.getName());
        System.out.println(LISI.getSex());
    }

}
