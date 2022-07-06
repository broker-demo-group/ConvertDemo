package com.brokerdemo.brokerconvertdemoproject.utils;

import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LRUCache<K,V> {
    volatile LinkedHashMap<K,V> cacheMap;
    volatile int size;


    public LRUCache(int size){
        this.size = size;
        cacheMap = new LinkedHashMap<>();
    }
    public synchronized V getValue(K key){
        if(cacheMap.containsKey(key)){
            V result = cacheMap.get(key);
            cacheMap.remove(key);
            cacheMap.put(key,result);
            return result;
        }
        return null;
    }

    public synchronized void putValue(K key, V value){
        if (cacheMap.size()<size){
            cacheMap.put(key,value);
        }else{
            K removeKey = (K)cacheMap.keySet().toArray()[0];
            cacheMap.remove(removeKey);
            cacheMap.put(key,value);
        }
    }

}
