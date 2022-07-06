package com.brokerdemo.brokerconvertdemoproject.configuration;

import org.okxbrokerdemo.Client;
import org.okxbrokerdemo.OkxSDK;
import com.brokerdemo.brokerconvertdemoproject.utils.LRUCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LRUCacheFactory {
    @Value("${broker.cache.maxsize}")
    int size;
    @Bean
    public LRUCache<String,Client> initLruCache(){

        return new LRUCache<String,Client>(size);
    }
}
