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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
