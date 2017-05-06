package com.xxx.xing.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author xing
 * @Created by 2017-04-26 下午3:22.
 */
@Service
@CacheConfig(cacheNames = "token")
public class TokenService {

    @CachePut(key = "'token-'+#token")
    public String saveToken(String openid, String token) {
        return openid;
    }

    @Cacheable(key = "'token-'+#token")
    public String findByToken(String token) {
        return null;
    }
}
