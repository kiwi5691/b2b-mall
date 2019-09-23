package com.b2b.dubbo.sso.service.Impl;

import com.b2b.dubbo.sso.service.UserService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.common.util.MD5Util;
import com.b2b.mall.db.mapper.TbUserMapper;
import com.b2b.mall.db.model.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author kiwi
 */
@Service(version = "${Dubbo_Version}")
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${REDIS_SESSION_KEY}")
    private String REDIS_SESSION_KEY;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    /**
     * 根据数据类型校验数据
     * @param param
     * @param type
     * @return
     */
    @Override
    public Result checkData(String param, Integer type) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("param", param);
        paramMap.put("type", type);
        List<TbUser> tbUsers = userMapper.selectByKey(paramMap);
        if (tbUsers == null || tbUsers.isEmpty()) {
            return Result.ok(true);
        } else {
            return Result.ok(false);
        }
    }

    @Override
    public Result register(TbUser tbUser) {
        // 校验数据
        if (StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())) {
            return Result.build(400, "用户名或密码不能为空");
        }
        Result result = checkData(tbUser.getUsername(), 1);
        if (!(boolean) result.getData()) {
            return Result.build(400, "用户名重复");
        }
        if (tbUser.getPhone() != null) {
            result = checkData(tbUser.getPhone(), 2);
            if (!(boolean) result.getData()) {
                return Result.build(400, "手机号重复");
            }
        }
        if (tbUser.getEmail() != null) {
            result = checkData(tbUser.getEmail(), 3);
            if (!(boolean) result.getData()) {
                return Result.build(400, "邮箱重复");
            }
        }
        // 插入数据
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setPassword(MD5Util.encrypt(tbUser.getUsername(), tbUser.getPassword()));
        userMapper.insert(tbUser);

        return Result.build(200,null,null);

    }

    @Override
    public Result login(String username, String password) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", username);
        List<TbUser> users = userMapper.selectUserByNameOrPwd(paramMap);

        if (users == null || users.isEmpty()) {
            return Result.build(400, "该用户不存在");
        }

        TbUser tbUser = users.get(0);
        // 校验密码
        if (!tbUser.getPassword().equals(MD5Util.encrypt(tbUser.getUsername(), password))) {
            return Result.build(400, "密码错误");
        }

        // 登录成功
        String token = UUID.randomUUID().toString();
        tbUser.setPassword(null);
        redisTemplate.opsForValue().set(REDIS_SESSION_KEY + ":" + token, tbUser);
        redisTemplate.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);

        return Result.build(200,null,token);

    }

    @Override
    public Result getUserByToken(String token) {
        TbUser tbUser = (TbUser) redisTemplate.opsForValue().get(REDIS_SESSION_KEY + ":" + token);
        if (tbUser == null) {
            return Result.build(201, "用户登录信息已经过期！");
        }
        redisTemplate.expire(REDIS_SESSION_KEY + ":" + token, SESSION_EXPIRE, TimeUnit.MINUTES);
        return Result.build(200,null,tbUser);
    }
}
