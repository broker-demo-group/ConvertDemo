package com.brokerdemo.brokerconvertdemoproject.cache;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class LocalCache implements Cache {
    private static final Map<String, String> cache = new HashMap<>();

    @Override
    public String get(String key) {
        return cache.get(key);
    }

    @Override
    public String set(String key, String value) {
        return cache.put(key, value);
    }
}
