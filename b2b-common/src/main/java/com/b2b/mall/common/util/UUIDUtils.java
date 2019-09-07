package com.b2b.mall.common.util;

import java.text.*;
import java.util.Calendar;
import java.util.UUID;

/**
 * @auther kiwi
 * @Date 2019/9/7 16:51
 */
public class UUIDUtils {

    /**
     *
     */
    private static final FieldPosition HELPER_POSITION = new FieldPosition(0);

    /** 时间：精确到秒 */
    private final static Format dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");

    private final static NumberFormat numberFormat = new DecimalFormat("00000");

    private static int seq = 0;

    private static final int MAX = 99999;

    //UUIDUtils 随机生成激活码
        public static String getUUID(){
            return UUID.randomUUID().toString().replace("-","");
        }


     //根据日期生词订单号
     public static synchronized String getOrderId(){
         Calendar rightNow = Calendar.getInstance();
         StringBuffer sb = new StringBuffer();
         dateFormat.format(rightNow.getTime(), sb, HELPER_POSITION);
         numberFormat.format(seq, sb, HELPER_POSITION);
         if (seq == MAX) {
             seq = 0;
         } else {
             seq++;
         }
         return sb.toString();
     }
}
