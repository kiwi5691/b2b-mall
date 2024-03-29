package com.b2b.mall.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @auther kiwi
 * @Date 2019/9/6 8:17
 * @Describe 根据反射获取属性判定
 */
public class BaseHTMLStringCase {

    // 判定是否为null
    public static void isNull(Object model) throws  Exception{
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model); // 调用getter方法获取属性值
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,String.class);
                        m.invoke(model, "无");
                    }
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,Integer.class);
                        m.invoke(model, 0);
                    }
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set"+name,Boolean.class);
                        m.invoke(model, false);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public static Integer lockCheckToInt(String number){
        if(number.trim().equals("被冻结")){
            return 0;
        }
        else {
            return 1;
        }
    }
    public static String lockCheck(String number){
        if(number.equals("1")){
            return "无";
        }
        else {
            return "被冻结";
        }
    }
    public static String recIdStrTo(String number){
        if(number.trim().equals("0")){
            return "系统";
        }
        else {
            return number;
        }
    }

    public static String typeStrTo(String number){
        if(number.trim().equals("0")){
            return "私信";
        }
        else if(number.trim().equals("1")){
            return "公共消息";
        }
        else if(number.trim().equals("2")){
            return "系统消息";
        }
        else {
            return "未知消息";
        }
    }

    public static String MsgtypeStrTo(String number){
        if(number.trim().equals("0")){
            return "注册/改密";
        }
        else if(number.trim().equals("1")){
            return "审核";
        }
        else if(number.trim().equals("2")){
            return "消息";
        }
        else {
            return "未知分类";
        }
    }


    public static String statusStrTo(String number){
        if(number.trim().equals("0")){
            return "未读";
        }
        else if(number.trim().equals("1")){
            return "已查看";
        }
        else if(number.trim().equals("2")){
            return "删除";
        }
        else {
            return "未知";
        }
    }

    public static Boolean isParent(Integer integer){
        if(integer==1){
            return true;
        }
        else {
            return false;
        }
    }

    public static String isSent(String shippingCode){
        if(shippingCode!=null){
            return shippingCode;
        }
        else {
            return "未发货";
        }
    }


}
