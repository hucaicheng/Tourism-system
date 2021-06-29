package com.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将String类型转化为Date类型
 */
public class StringToDateConverter implements Converter<String, Date> {

    /**
     *
     * @param s 传入过来的数据
     * @return
     */
    public Date convert(String s) {
        if (s==null){
            throw new RuntimeException("请输入数据");
        }
        System.out.println("将表单中string类型转为时间类型");
        System.out.println(s);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println("修改完成");
        try {
            return dateFormat.parse(s);
        } catch (Exception e) {
            throw new RuntimeException("转化失败");
        }
    }
}
