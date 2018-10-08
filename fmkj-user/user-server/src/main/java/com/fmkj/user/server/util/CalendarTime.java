package com.fmkj.user.server.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: fmkj
 * @description: 当前时间之前或之后多少天的时间
 * @author: 杨胜彬
 * @create: 2018-08-28 11:48
 **/
public class CalendarTime {

    /**
    * @Description:  获取当前时间num天之前或之后的时间
    * @Param:
    * @return:
    * @Author: 杨胜彬
    * @Date: 2018/8/28 0028
    */
    public  Timestamp dateStartEed(int num){

        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        String endDate = sdf.format(today);//当前日期

        //获取num天前日期
        Calendar theCa = Calendar.getInstance();
        theCa.setTime(today);
        theCa.add(theCa.DATE, num);//最后一个数字30可改，30天的意思
        Date start = theCa.getTime();
        String startDate = sdf.format(start);//num天地饿日期
        Timestamp time = Timestamp.valueOf(startDate);
        return time;
    }

    public Timestamp thisDate(){
        //获取当前日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        String endDate = sdf.format(today);//当前日期
        Timestamp time = Timestamp.valueOf(endDate);
        return time;
    }


}
