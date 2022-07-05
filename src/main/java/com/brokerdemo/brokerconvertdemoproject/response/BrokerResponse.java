package com.brokerdemo.brokerconvertdemoproject.response;
/**
 * @author: bowen
 * @description:
 * @date: 2022/7/5  10:20 AM
 **/
public class BrokerResponse {

    int code;
    String data;
    String message;

    public BrokerResponse(int code,String data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BrokerResponse() {
        this.code = 0;
        this.data = "";
        this.message = "";
    }

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code +
                "\", \"data\":" + data +
                ", \"message\":\"" + message + '\"' +
                '}';
    }
}
