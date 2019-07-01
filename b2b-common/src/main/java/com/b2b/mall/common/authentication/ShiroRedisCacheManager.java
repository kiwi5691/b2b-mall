package com.b2b.mall.common.authentication;

import org.apache.ibatis.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;

/**
 * @auther kiwi
 * @Date 2019/6/29 16:46
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {

//    private RedisTemplate<byte[],byte[]> redisTemplate;
//
//    public ShiroRedisCacheManager(RedisTemplate redisTemplate){
//        this.redisTemplate = redisTemplate;
//    }
//    @Override
//    protected Cache createCache(String name) throws CacheException {
//        return new ShiroRedisCache(redisTemplate,name);
//    }
//
    @Override
    protected Collection<? extends Cache> loadCaches() {
        return null;
    }
}