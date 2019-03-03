package com.net.rx_retrofit_network.location.model;

/**
 * Created by baozi on 2016/12/5.
 */
public class BaseResponse<T>{
    private boolean success;
    private int code;
    private String msg;
    private T data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
