package com.b2b.mall.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @auther kiwi
 * @Date 2019/9/7 23:55
 */
@Slf4j
public class EmailTemplate {

    /**
     *@Auther kiwi
     *@Data 2019/9/8
     @param  * @param
     * 注册模板
     *@reutn java.lang.String
    */
    public static String registerTemplate(String sendUrl,String code) throws IOException {
        String fileName = "pod-scale-alarm.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.error("读取文件失败，fileName:{}", fileName, e);
        } finally {
            inputStream.close();
            fileReader.close();
        }

        String contentText = "以下是需要点击的链接, 进入便可完成注册.<a href='"+sendUrl+"checkCode?code="+code+"'>Click Me</a>";
        //邮件表格header
        String header = "<td>商品管理(itemManagement)</td><td>订单管理(orderManagement)</td><td>用户管理(userManagement)</td><td>系统管理(systemManagement)</td><td>调度任务(Scheduled Tasks)</td>";
        StringBuilder linesBuffer = new StringBuilder();
        linesBuffer.append("<tr><td>" + "four menu" + "</td><td>" + "four menu" + "</td><td>" + "three menu" + "</td>" +
                "<td>" + "two menu" + "</td><td>" + "two menu" + "</td></tr>");

        String emailHeadColor = "#8A2BE2";
        String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        //填充html模板中的五个参数
        String htmlText = MessageFormat.format(buffer.toString(), emailHeadColor, contentText, date, header, linesBuffer.toString());

        //改变表格样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
        return htmlText;
    }
}
