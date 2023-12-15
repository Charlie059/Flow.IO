package org.flowio.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    // TODO read from yml
    private final long accessTokenExpirationTime = 1000 * 60 * 10; // 10 mins
    private final long refreshTokenExpirationTime = 1000 * 60 * 60 * 24; // 1 day

    private final int lockTimeout = 1000;

    @Autowired
    private StringRedisTemplate template;


    // Locking methods
    public boolean acquireLock(String lockKey) {
        String lockValue = "lock";
        return Boolean.TRUE.equals(template.opsForValue().setIfAbsent(lockKey, lockValue, lockTimeout, TimeUnit.MILLISECONDS));
    }

    public void releaseLock(String lockKey) {
        template.delete(lockKey);
    }

    private void addKeyValue(String key, Long value, long timeout) {
        template.opsForValue().set(key, value.toString(), timeout, TimeUnit.MILLISECONDS);
    }

    private Long getValue(String key) {
        String value = template.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : null;
    }

    public Long getSetWithTimeout(String key, Long newValue, long timeout) {
        String oldValueAsString = template.opsForValue().getAndSet(key, newValue.toString());
        template.expire(key, timeout, TimeUnit.MILLISECONDS);
        return oldValueAsString != null ? Long.parseLong(oldValueAsString) : null;
    }

    Long getAndUpdateTokenLastUsage(String userEmail, String familyId, Character appendix) {
        String Key = userEmail + familyId + appendix;
        return getSetWithTimeout(Key, System.currentTimeMillis(), refreshTokenExpirationTime);
    }


    void addTokenLastUsage(String userEmail, String familyId, Character appendix){
        String valueKey = userEmail + familyId + appendix;
        addKeyValue(
                valueKey,
                System.currentTimeMillis(),
                refreshTokenExpirationTime
        );
    }

}

