package com.w.utils;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Result {

    private int code = ResultCode.SUCCESS.getCode();
    private String message = ResultCode.SUCCESS.getMessage();
    private boolean success = ResultCode.SUCCESS.getSuccess();
    private Object data;


    public Result(int code, String message,boolean success) {
        this.code = code;
        this.message = message;
        this.success=success;
    }

    public Result(int code, String message,boolean success, Object data) {
        this.code = code;
        this.message = message;
        this.success=success;
        this.data = data;
    }


}
