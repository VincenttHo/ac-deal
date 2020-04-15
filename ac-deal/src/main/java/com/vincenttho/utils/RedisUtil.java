package com.vincenttho.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

/**
 * @className:com.vincenttho.utils.RedisUtil
 * @description:
 * @version:v1.0.0
 * @author: VincentHo
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/4/15     VincentHo       v1.0.0        create
 */
@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static RedisUtil redisUtil;

    @PostConstruct
    public void init() {
        redisUtil = this;
        redisUtil.stringRedisTemplate = this.stringRedisTemplate;
    }

    public static void set(String key, String value, Duration timeout) throws Exception {
        redisUtil.stringRedisTemplate.opsForValue().set(key, value, timeout);
    }

    public static String get(String key) throws Exception {
        return redisUtil.stringRedisTemplate.opsForValue().get(key);
    }

}