package com.lambda.wallet.base;

import java.io.Serializable;


public class ResponseBean<T> implements Serializable {

    public String code;
    public String msg;
    public String extraMsg;
    public T data = null;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExtraMsg() {
        return extraMsg == null ? "" : extraMsg;
    }

    public void setExtraMsg(String extraMsg) {
        this.extraMsg = extraMsg;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
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