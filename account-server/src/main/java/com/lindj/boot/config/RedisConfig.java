package com.lindj.boot.config;

import org.redisson.api.RedissonClient;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lindj
 * @date 2019/4/17 0017
 * @description
 */
//@Configuration
//@EnableCaching
public class RedisConfig {


    /**
     * redis缓存配置
     *
     * @param redissonClient
     * @return
     */
    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<String, CacheConfig>(16);
        config.put("marketing", new CacheConfig(30 * 60 * 1000L, 15 * 60 * 1000L));
        return new RedissonSpringCacheManager(redissonClient, config);
    }
}
                                                  