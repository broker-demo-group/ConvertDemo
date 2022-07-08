package com.brokerdemo.brokerconvertdemoproject.cache;

public interface Cache {

    String get(String key);

    String set(String key,String value);
}
