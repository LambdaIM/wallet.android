package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * 我参与
 */
public class FundInfoMineBean implements Parcelable {


    private String funder;

    private HomeAssetBean.AssetBean invest;//我参与预挖矿的额度

    private HomeAssetBean.AssetBean stake;//预计我收到的资产额度


    public String getFunder() {
        return funder;
    }

    public void setFunder(String funder) {
        this.funder = funder;
    }

    public HomeAssetBean.AssetBean getInvest() {
        return invest;
    }

    public void setInvest(HomeAssetBean.AssetBean invest) {
        this.invest = invest;
    }

    public HomeAssetBean.AssetBean getStake() {
        return stake;
    }

    public void setStake(HomeAssetBean.AssetBean stake) {
        this.stake = stake;
    }

    public static Creator<FundInfoMineBean> getCREATOR() {
        return CREATOR;
    }

    protected FundInfoMineBean(Parcel in) {
        funder = in.readString();
        invest = in.readParcelable(HomeAssetBean.AssetBean.class.getClassLoader());
        stake = in.readParcelable(HomeAssetBean.AssetBean.class.getClassLoader());
    }

    public static final Creator<FundInfoMineBean> CREATOR = new Creator<FundInfoMineBean>() {
        @Override
        public FundInfoMineBean createFromParcel(Parcel in) {
            return new FundInfoMineBean(in);
        }

        @Override
        public FundInfoMineBean[] newArray(int size) {
            return new FundInfoMineBean[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {

        dest.writeString(this.funder);
        dest.writeParcelable(this.invest, flag);
        dest.writeParcelable(this.stake, flag);
    }
}
