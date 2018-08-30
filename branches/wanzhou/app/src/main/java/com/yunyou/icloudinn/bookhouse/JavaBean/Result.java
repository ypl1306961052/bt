package com.yunyou.icloudinn.bookhouse.JavaBean;

/**
 * Created by Administrator on 2017/8/24.
 */

public class Result<T> {

    private int code;
    private String msg;
    private T   data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
