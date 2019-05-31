package com.lindj.boot.config.redisson;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @author lindj
 * @date 2019/5/31 0031
 * @description
 */
public class MyRedissonShiroCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return null;
    }
}
