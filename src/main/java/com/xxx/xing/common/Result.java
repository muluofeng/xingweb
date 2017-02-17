package com.xxx.xing.common;

/**
 * Created by xing on 2017/2/17.
 */
public class Result<T> {

    private Boolean isSuccess;
    private T data;
    private String message;

    public Result() {
    }

    public Result(Boolean isSuccess, String message, T data) {
        this(isSuccess,message);
        this.data = data;
    }

    public Result(Boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
