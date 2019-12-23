package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/5
 * Time: 20:06
 */
public class MarketSellListBean implements Parcelable {

    /**
     * orderId : 00A482D80ACAAA0BEDABB0AA6BE25598967E69DF
     * address : lambdamineroper1sxew7dhy55zpv2wsvalva0u3dy94xx2shal9za
     * price : 5000000
     * rate : 3.000000000000000000
     * amount : [{"denom":"ulamb","amount":"15000000"}]
     * sellSize : 10
     * unUseSize : 1
     * status : 0
     * createTime : 2019-11-22T01:44:42.990629769Z
     * cancelTimeDuration : 3600000000000
     * marketAddress : lambdamarketoper1thj5fv8d0dsh3aealhpxm9mvgxjfh87srk3887
     * machineName : machine01
     * minBuySize : 1
     * minDuration : 2592000000000000
     * maxDuration : 7776000000000000
     * reserved1 :
     */

    private String orderId;
    private String address;
    private String price;
    private String rate;
    private String sellSize;
    private String unUseSize;
    private String status;
    private String createTime;
    private String cancelTimeDuration;
    private String marketAddress;
    private String machineName;
    private String minBuySize;
    private String minDuration;
    private String maxDuration;
    private String reserved1;
    private List<AmountBean> amount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSellSize() {
        return sellSize;
    }

    public void setSellSize(String sellSize) {
        this.sellSize = sellSize;
    }

    public String getUnUseSize() {
        return unUseSize;
    }

    public void setUnUseSize(String unUseSize) {
        this.unUseSize = unUseSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCancelTimeDuration() {
        return cancelTimeDuration;
    }

    public void setCancelTimeDuration(String cancelTimeDuration) {
        this.cancelTimeDuration = cancelTimeDuration;
    }

    public String getMarketAddress() {
        return marketAddress;
    }

    public void setMarketAddress(String marketAddress) {
        this.marketAddress = marketAddress;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getMinBuySize() {
        return minBuySize;
    }

    public void setMinBuySize(String minBuySize) {
        this.minBuySize = minBuySize;
    }

    public String getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(String minDuration) {
        this.minDuration = minDuration;
    }

    public String getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(String maxDuration) {
        this.maxDuration = maxDuration;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public List<AmountBean> getAmount() {
        return amount;
    }

    public void setAmount(List<AmountBean> amount) {
        this.amount = amount;
    }

    public static class AmountBean implements Parcelable {
        /**
         * denom : ulamb
         * amount : 15000000
         */

        private String denom;
        private String amount;

        public String getDenom() {
            return denom;
        }

        public void setDenom(String denom) {
            this.denom = denom;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.denom);
            dest.writeString(this.amount);
        }

        public AmountBean() {
        }

        protected AmountBean(Parcel in) {
            this.denom = in.readString();
            this.amount = in.readString();
        }

        public static final Creator<AmountBean> CREATOR = new Creator<AmountBean>() {
            @Override
            public AmountBean createFromParcel(Parcel source) {
                return new AmountBean(source);
            }

            @Override
            public AmountBean[] newArray(int size) {
                return new AmountBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.address);
        dest.writeString(this.price);
        dest.writeString(this.rate);
        dest.writeString(this.sellSize);
        dest.writeString(this.unUseSize);
        dest.writeString(this.status);
        dest.writeString(this.createTime);
        dest.writeString(this.cancelTimeDuration);
        dest.writeString(this.marketAddress);
        dest.writeString(this.machineName);
        dest.writeString(this.minBuySize);
        dest.writeString(this.minDuration);
        dest.writeString(this.maxDuration);
        dest.writeString(this.reserved1);
        dest.writeList(this.amount);
    }

    public MarketSellListBean() {
    }

    protected MarketSellListBean(Parcel in) {
        this.orderId = in.readString();
        this.address = in.readString();
        this.price = in.readString();
        this.rate = in.readString();
        this.sellSize = in.readString();
        this.unUseSize = in.readString();
        this.status = in.readString();
        this.createTime = in.readString();
        this.cancelTimeDuration = in.readString();
        this.marketAddress = in.readString();
        this.machineName = in.readString();
        this.minBuySize = in.readString();
        this.minDuration = in.readString();
        this.maxDuration = in.readString();
        this.reserved1 = in.readString();
        this.amount = new ArrayList<AmountBean>();
        in.readList(this.amount, AmountBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MarketSellListBean> CREATOR = new Parcelable.Creator<MarketSellListBean>() {
        @Override
        public MarketSellListBean createFromParcel(Parcel source) {
            return new MarketSellListBean(source);
        }

        @Override
        public MarketSellListBean[] newArray(int size) {
            return new MarketSellListBean[size];
        }
    };
}
