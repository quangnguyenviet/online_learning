package com.vitube.online_learning.service;

public interface TokenBlacklistService {
    void blacklist(String jti, long ttlSeconds);

    boolean isBlacklisted(String jti);
}
