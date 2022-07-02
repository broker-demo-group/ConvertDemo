package com.brokerdemo.brokerconvertdemoproject.dao;


import com.google.gson.Gson;
import org.okxbrokerdemo.service.APIRequestPayload;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiParam implements APIRequestPayload {
    Map<String,Object> paramMap;
    public ApiParam(){
        paramMap = new LinkedHashMap<>();
    }
    public void addParam(String key,Object value){
        paramMap.put(key,value);
    }

    @Override
    public String getPayLoadJson() {
        Gson gson = new Gson();
        return gson.toJson(this.paramMap);
    }
}
