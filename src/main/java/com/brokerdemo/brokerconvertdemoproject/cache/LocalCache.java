package com.brokerdemo.brokerconvertdemoproject.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
public class LocalCache implements Cache {

    private static final com.google.common.cache.Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(1024)
            .expireAfterAccess(Duration.ofMinutes(30L))
            .build();

    @Override
    public String get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void set(String key, String value) {
        cache.put(key, value);
    }

    @Override
    public void remove(String key) {
        cache.invalidate(key);
    }
}
