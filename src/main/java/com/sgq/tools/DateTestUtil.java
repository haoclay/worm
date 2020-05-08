package com.sgq.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by NAGOYA on 2020/5/1.
 */
public class DateTestUtil {

    public static void main(String[] args) throws Exception {

        DateTestUtil dateTestUtil = new DateTestUtil();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2020-04-30");
        String a = sdf.format(date);
        System.out.println(dateTestUtil.compareTime(a));

    }

    public  boolean compareTime(String last_modify_time){
        Date now = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String now_time=sdf.format(now);
        System.out.println("last_modify_time:"+last_modify_time);
        System.out.println("now_time:"+now_time);
         if(last_modify_time.equals(now_time)){
             return true;
             //今天已点赞
         }
        return false ;
        //今天未点赞
    }

    public void test() throws ParseException {

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        String str="20110823";
        Date dt=sdf.parse(str);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,-1);//日期减1年
        rightNow.add(Calendar.MONTH,3);//日期加3个月
        rightNow.add(Calendar.DAY_OF_YEAR,10);//日期加10天
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        System.out.println(reStr);

    }

}
