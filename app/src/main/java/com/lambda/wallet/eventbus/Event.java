package com.lambda.wallet.eventbus;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/6/20
 * Time: 10:06
 */
public class Event<T> {
    private int code;
    private T data;

    public Event(int code) {
        this.code = code;
    }

    public Event(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}