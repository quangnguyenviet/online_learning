package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {
    private static final String BLACKLIST_VALUE = "1";

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${auth.blacklist.prefix:auth:blacklist:}")
    private String keyPrefix;

    @Override
    public void blacklist(String jti, long ttlSeconds) {
        String key = buildKey(jti);

        if (ttlSeconds <= 0) {
            return;
        }

        stringRedisTemplate.opsForValue().set(key, BLACKLIST_VALUE, Duration.ofSeconds(ttlSeconds));
    }

    @Override
    public boolean isBlacklisted(String jti) {
        String key = buildKey(jti);
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    private String buildKey(String jti) {
        if (jti == null || jti.isBlank()) {
            throw new IllegalArgumentException("jti must not be blank");
        }

        return keyPrefix + jti;
    }
}
