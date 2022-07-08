package com.brokerdemo.brokerconvertdemoproject.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private int code;
    private T data;
    private String desc;


    public static Result success() {
        return new Result(0, null, "success");
    }

    public static <T> Result success(T data) {
        return new Result(0, data, "success");
    }


    public static Result error(int errorCode,String msg) {
        return new Result(errorCode, null, msg);
    }
}
