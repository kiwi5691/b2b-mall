package com.b2b.mall.common.util;

import com.b2b.mall.common.enums.TimeQuantumEnum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author kiwi
 * @date 2018/4/2
 */
public class DateUtil {

    public static String  getDateStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date strToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date result = null;
        try {
            result = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String preciseDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date dateBeforeMoth() {

        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-30);
        Date date = c.getTime();
        String dateStr = format.format(date);
        Date result = null;
        try {
            result = format.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String checkTimeQuantum(){
        String timeQuantum="";
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = df.format(date);
        int a = Integer.parseInt(str);
        if (a >= TimeQuantumEnum.zero.getValue() && a <= TimeQuantumEnum.six.getValue()) {
            timeQuantum=("凌晨好，该睡觉了");
        }
        if (a > TimeQuantumEnum.six.getValue() && a <= TimeQuantumEnum.twelve.getValue()) {
            timeQuantum=("上午好");
        }
        if (a > TimeQuantumEnum.twelve.getValue() && a <= TimeQuantumEnum.thirteen.getValue()) {
            timeQuantum=("中午好");
        }
        if (a > TimeQuantumEnum.thirteen.getValue() && a <= TimeQuantumEnum.eighteen.getValue()) {
            timeQuantum=("下午好");
        }
        if (a > TimeQuantumEnum.eighteen.getValue() && a <= TimeQuantumEnum.twetytwo.getValue()) {
            timeQuantum=("晚上好");
        }
        return timeQuantum;
    }
}
