package com.example.administrator.matata_android.zhzbase.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期，时间转换工具类
 */
public class DateUtils  {

    private static String mYear;//当前年
    private static String mMonth;//当前月
    private static String mDay;//当前日

    public DateUtils(){}

    private static final DateUtils dateUtils=new DateUtils();

    public static DateUtils getInstance(){
        return dateUtils;
    }

    /**
     * 把当前日期转换格式为yyyy-MM-dd
     * @param date
     * @return
     */
    public String format(Date date){
        String format="yyyy-MM-dd";
        SimpleDateFormat fmt=new SimpleDateFormat(format);
        return fmt.format(date);
    }

    /**
     * 把当前日期转换位指定格式
     * @param date
     * @param format
     * @return
     */
    public String format(Date date,String format){

        SimpleDateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    /**
     * 将String 日期转换为Date对象
     * @param s
     * @return
     * @throws ParseException
     */
    public String stringToDate(String s)throws ParseException{
        SimpleDateFormat fmt = new SimpleDateFormat("yy-MM-dd");
        Date datel=fmt.parse(s);
       return fmt.format(datel);
    }

    /**
     * 将String类型的秒转换为分钟
     * @param time
     * @return
     */
    public String stringTime(String time){
        int a =Integer.parseInt(time);
        int b =a/60;
        String s=String.valueOf(b);
        return s;
    }

    /**
     *  转换星期
     * @param position
     * @return
     */
    public String stringweek(int position){
        String s =null;
        switch (position){
            case 0:
                s= "一";
                break;
            case 1:
                s= "二";
                break;
            case 2:
                s= "三";
                break;
            case 3:
                s= "四";
            break;
            case 4:
                s= "五";
            break;
            case 5:
                s= "六";
            break;
            case 6:
                s= "日";
            break;
        }
        return s;
    }

}
