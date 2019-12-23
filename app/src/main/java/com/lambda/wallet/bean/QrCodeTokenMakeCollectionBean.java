package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/17
 * Time: 14:10
 */
public class QrCodeTokenMakeCollectionBean implements Parcelable {
    private String chainUrl;//交易所在验证节点
    private String address;//接收地址
    private String type;//二维码类型：收款
    private String token;//接收代币

    public String getChainUrl() {
        return chainUrl == null ? "" : chainUrl;
    }

    public void setChainUrl(String chainUrl) {
        this.chainUrl = chainUrl;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token == null ? "" : token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.chainUrl);
        dest.writeString(this.address);
        dest.writeString(this.type);
        dest.writeString(this.token);
    }

    public QrCodeTokenMakeCollectionBean() {
    }

    protected QrCodeTokenMakeCollectionBean(Parcel in) {
        this.chainUrl = in.readString();
        this.address = in.readString();
        this.type = in.readString();
        this.token = in.readString();
    }

    public static final Parcelable.Creator<QrCodeTokenMakeCollectionBean> CREATOR = new Parcelable.Creator<QrCodeTokenMakeCollectionBean>() {
        @Override
        public QrCodeTokenMakeCollectionBean createFromParcel(Parcel source) {
            return new QrCodeTokenMakeCollectionBean(source);
        }

        @Override
        public QrCodeTokenMakeCollectionBean[] newArray(int size) {
            return new QrCodeTokenMakeCollectionBean[size];
        }
    };
}
