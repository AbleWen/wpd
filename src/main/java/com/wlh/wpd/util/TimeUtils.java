package com.wlh.wpd.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间辅助类
 */
public class TimeUtils
{

    /**
     * <获取当前日期的字符串,格式为yyyyMMddHHmmssSSS>
     * @return [参数说明]
     * @return String [日期字符串]
     */
    public static String getTimeStamp()
    {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.format(new Date());
    }

    public static String getTimeStamp(String style, Date date)
    {
        DateFormat format = new SimpleDateFormat(style);
        return format.format(date);
    }

    /**
     * <获取当前日期的字符串,格式指定>
     * @return [参数说明]
     * @return String [日期字符串]
     */
    public static String getTimeStampByFormat(String style)
    {
        DateFormat format = new SimpleDateFormat(style);
        return format.format(new Date());
    }

    /**
     * 把日期字符串转换成日期型数据 ,日期字符串格式为 yyyyMMddHHmmssSSS
     * @param str 要转换的日期字符串
     * @return 转换后的日期型数据
     * @throws ParseException [参数说明]
     * @return Date [日期对象]
     * @exception throws [违例类型] [违例说明]
     */
    public static Date toDate(String str) throws ParseException
    {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return format.parse(str);
    }

    /**
     * 把日期字符串转换成日期型数据 ,日期字符串格式为style指定
     * @param str 要转换的日期字符串
     * @param style 格式
     * @return 转换后的日期型数据
     * @throws ParseException [参数说明]
     * @return Date [日期对象]
     * @exception throws [违例类型] [违例说明]
     */
    public static Date toDate(String str, String style) throws ParseException
    {
        DateFormat format = new SimpleDateFormat(style);
        return format.parse(str);
    }
}
