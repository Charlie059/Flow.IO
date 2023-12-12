package org.flowio.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate template;

    public void addKeyValue(String key, Long value, long timeout) {
        template.opsForValue().set(key, value.toString(), timeout, TimeUnit.MILLISECONDS);
    }

    public Long getValue(String key) {
        String value = template.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : null;
    }
}

