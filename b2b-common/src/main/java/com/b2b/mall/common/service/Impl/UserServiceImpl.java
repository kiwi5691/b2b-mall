package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.jms.EmailService;
import com.b2b.mall.common.service.UserService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.EmailTemplate;
import com.b2b.mall.common.util.MD5Util;
import com.b2b.mall.common.util.UUIDUtils;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.mapper.UserRoleKeyMapper;
import com.b2b.mall.db.mapper.sysMessageLogMapper;
import com.b2b.mall.db.mapper.sysMessageMapper;
import com.b2b.mall.db.model.User;
import com.b2b.mall.db.model.UserRoleKey;
import com.b2b.mall.db.model.sysMessage;
import com.b2b.mall.db.model.sysMessageLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author kiwi
 * @Date: 2019/6/26 13:09
 */
@Slf4j
@PropertySource("email.properties")
@Service
public class UserServiceImpl  implements UserService {
    @Resource
    private UserMapper userMapper;


    @Value("${com.mailUrl}")
    private String sendUrl;

    @Resource
    private EmailService emailService;


    @Resource
    private sysMessageLogMapper sysMessageLogMapper;
    @Resource
    private sysMessageMapper sysMessageMapper;

    @Resource
    private UserRoleKeyMapper userRoleKeyMapper;

    @Override
    public User selectAllByName(String userName) {
        return userMapper.selectAllByName(userName);
    }

    @Override
    public void userManagePost(Model model, User user) {

        log.info(user.toString());

        user.setState(BaseHTMLStringCase.lockCheckToInt(user.getStateStr()));
        log.info("temp state"+user.getStateStr()+"temp int"+user.getState());
        userMapper.update(user);
        Integer roleId=0;
        //插入rolekey表
        roleId =userMapper.selectRoleIdByBiz(userMapper.selectById(user.getUserName()).getId());
        log.info("roId is"+roleId);

        UserRoleKey userRoleKey=new UserRoleKey();
        userRoleKey.setRoleId(roleId);
        userRoleKey.setUserId(userMapper.selectById(user.getUserName()).getId());
        userRoleKeyMapper.updateUserId(userRoleKey);
    }

    @Override
    public int register(Model model, User user) throws IOException {

        Integer roleId=0;

        String pwd=user.getPassword();
        user.setPassword(MD5Util.encrypt(user.getUserName(), pwd));
        Date date = new Date();
        user.setAddDate(date);
        user.setUpdateDate(date);
        user.setBusiness("客维员");
        //先冻结住
        user.setState(0);
        userMapper.insert(user);
        //插入rolekey表
        roleId =userMapper.selectRoleIdByBiz(userMapper.selectById(user.getUserName()).getId());
        UserRoleKey userRoleKey=new UserRoleKey();
        userRoleKey.setRoleId(roleId);
        userRoleKey.setUserId(userMapper.selectById(user.getUserName()).getId());
        userRoleKeyMapper.insert(userRoleKey);

        //发送邮件

        sysMessage sysMessage = new sysMessage();
        sysMessageLog sysMessageLog = new sysMessageLog();
        //构造
        sysMessage.setStartTime(new Date());
        sysMessage.setPostTime(new Date());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        Date dateDisp = cal.getTime();
        sysMessage.setEndTime(dateDisp);
        sysMessage.setMsgType(0);
        sysMessage.setMsgTypeId(UUIDUtils.getIdByDate());
        sysMessage.setType(2);
        sysMessage.setTitle("欢迎注册");
        String code = UUIDUtils.getUUID()+ UUIDUtils.getUUID();
        sysMessage.setMessageText(code);
        sysMessageMapper.insert(sysMessage);
        //结束构造
       //开始构造
        Long sysId =Long.parseLong(sysMessage.getMsgTypeId());
        log.info("id is"+sysId);
        sysMessageLog.setId(sysId);
        sysMessageLog.setMessageId(sysMessageMapper.getIdByMessageId(sysMessage.getMsgTypeId()));
        sysMessageLog.setEmail(user.getEmail());
        sysMessageLog.setGroupId("1");
        sysMessageLog.setRecId(String.valueOf(userMapper.selectById(user.getUserName()).getId()));
        sysMessageLog.setSendId("0");
        sysMessageLog.setStatus(0);
        sysMessageLogMapper.insert(sysMessageLog);

        emailService.sendEmail(sysMessageLog.getEmail(),"欢迎注册", EmailTemplate.registerTemplate(sendUrl,sysMessage.getMessageText()));
        log.info("发送邮件");

        model.addAttribute("error", "请访问邮件，点击其中的链接");

        return 0;
    }

    @Override
    public void checkCode(String code) {

        sysMessage sysMessage = sysMessageMapper.checkCode(code);
        if (sysMessage !=null){
            Long syslogId =Long.parseLong(sysMessage.getMsgTypeId());
            sysMessageLog sysMessageLog = sysMessageLogMapper.getRecvId(syslogId);
            String id = sysMessageLog.getRecId();
            log.info("id is"+id);
            sysMessageLog.setStatus(1);
            //解绑
            sysMessageLogMapper.updateByPrimaryKeySelective(sysMessageLog);
            userMapper.updateSatueLocked(Integer.parseInt(id));
        }

    }
}
