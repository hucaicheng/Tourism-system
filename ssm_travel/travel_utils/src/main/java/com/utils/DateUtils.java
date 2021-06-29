package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 日期转成字符串
     */
    public static String dateToString(Date date, String patt){
        System.out.println("日期转成字符串");
        SimpleDateFormat sdf = new SimpleDateFormat(patt);
        String format = sdf.format(date);
        System.out.println("转换完成");
        return format;

    }

    /**
     * 字符串转日期
     */
    public static Date stringToDate(String str ,String patt) throws ParseException {
        System.out.println("字符串转日期");
        SimpleDateFormat sdf = new SimpleDateFormat(patt);
        Date parse = sdf.parse(str);
        System.out.println("转换完成");
        return parse;
    }
}
