package org.flowio.authenticationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    // TODO read from yml
    private final long accessTokenExpirationTime = 1000 * 60 * 10; // 10 mins
    private final long refreshTokenExpirationTime = 1000 * 60 * 60 * 24; // 1 day

    @Autowired
    private StringRedisTemplate template;

    private void addKeyValue(String key, Long value, long timeout) {
        template.opsForValue().set(key, value.toString(), timeout, TimeUnit.MILLISECONDS);
    }

    private Long getValue(String key) {
        String value = template.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : null;
    }


    void addRefreshTLastUsage(String userEmail, String familyId){
        String key = userEmail + familyId + 'R';
        addKeyValue(
                key,
                System.currentTimeMillis(),
                refreshTokenExpirationTime
        );
    }

    void addAccessTLastUsage(String userEmail, String familyId){
        String key = userEmail + familyId + 'A';
        addKeyValue(
                key,
                System.currentTimeMillis(),
                accessTokenExpirationTime
        );
    }

    Long getRefreshTLastUsage(String userEmail, String familyId){
        String key = userEmail + familyId + 'R';
        return getValue(key);
    }

    Long getAccessTLastUsage(String userEmail, String familyId){
        String key = userEmail + familyId + 'A';
        return getValue(key);
    }
}

