package com.brokerdemo.brokerconvertdemoproject.configuration;

import com.brokerdemo.brokerconvertdemoproject.utils.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class LRUCacheFactory {
    @Value("${broker.cache.maxsize}")
    int size;
    @Bean
    public LRUCache initLruCache(){
        return new LRUCache(size);
    }
}
