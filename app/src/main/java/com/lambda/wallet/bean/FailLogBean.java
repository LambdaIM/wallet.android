package com.lambda.wallet.bean;

/**
 * Created by coder.
 * User: blue
 * Date: 2020/3/16
 * Time: 17:26
 */
public class FailLogBean {

    /**
     * codespace : sdk
     * code : 10
     * message : insufficient account funds; 5ulamb,5utbb < 10ulamb
     */

    private String codespace;
    private int code;
    private String message;

    public String getCodespace() {
        return codespace;
    }

    public void setCodespace(String codespace) {
        this.codespace = codespace;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
