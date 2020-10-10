package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * 预挖矿详情
 * {
 *     "asset": {
 *         "denom": "u1cva1",
 *         "amount": "2000000"
 *     },
 *     "fund_asset": {
 *         "denom": "ulamb",
 *         "amount": "10000"
 *     },
 *     "status": 1,
 *     "amount": "0",
 *     "end_time": "2020-09-24T05:58:31.609695406Z"
 * }
 */
public class PreMiningDetail implements Parcelable {

    private String status;
    private String amount;
    private String end_time;

    //    预挖矿的总量
    private HomeAssetBean.AssetBean asset;
    // 使用何种资产预挖矿 /额度
    private HomeAssetBean.AssetBean fund_asset;


    protected PreMiningDetail(Parcel in) {
        status = in.readString();
        amount = in.readString();
        end_time = in.readString();
        asset = in.readParcelable(HomeAssetBean.AssetBean.class.getClassLoader());
        fund_asset = in.readParcelable(HomeAssetBean.AssetBean.class.getClassLoader());
    }

    public static final Creator<PreMiningDetail> CREATOR = new Creator<PreMiningDetail>() {
        @Override
        public PreMiningDetail createFromParcel(Parcel in) {
            return new PreMiningDetail(in);
        }

        @Override
        public PreMiningDetail[] newArray(int size) {
            return new PreMiningDetail[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public HomeAssetBean.AssetBean getAsset() {
        return asset;
    }

    public void setAsset(HomeAssetBean.AssetBean asset) {
        this.asset = asset;
    }

    public HomeAssetBean.AssetBean getFund_asset() {
        return fund_asset;
    }

    public void setFund_asset(HomeAssetBean.AssetBean fund_asset) {
        this.fund_asset = fund_asset;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {



        dest.writeString(this.status);
        dest.writeString(this.amount);
        dest.writeString(this.end_time);

        dest.writeParcelable(this.asset, flag);
        dest.writeParcelable(this.fund_asset, flag);
    }
}
