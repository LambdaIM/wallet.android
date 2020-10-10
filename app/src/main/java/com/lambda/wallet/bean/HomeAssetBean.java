package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class HomeAssetBean implements Parcelable {


    private String address;
    private String name;
    private String mint_type;
    private String status;
    private String inflation;
    private String mining_ratio;
    private String adjust_rate;
    private String adjust_period;
    private String max_adjust_count;
    private String adjust_count;
    private String genesis_height;
    private String expected_genesis_height;

    private AssetBean asset;

    private AssetBean token;

    private AssetBean curr_supply;

    private AssetBean lock_supply;

    private AssetBean accumulate_supply;


    private AssetBean total_supply;

    public AssetBean getAsset() {
        return asset;
    }

    public void setAsset(AssetBean asset) {
        this.asset = asset;
    }

    public AssetBean getToken() {
        return token;
    }

    public void setToken(AssetBean token) {
        this.token = token;
    }

    public AssetBean getCurr_supply() {
        return curr_supply;
    }

    public void setCurr_supply(AssetBean curr_supply) {
        this.curr_supply = curr_supply;
    }

    public AssetBean getLock_supply() {
        return lock_supply;
    }

    public void setLock_supply(AssetBean lock_supply) {
        this.lock_supply = lock_supply;
    }

    public AssetBean getAccumulate_supply() {
        return accumulate_supply;
    }

    public void setAccumulate_supply(AssetBean accumulate_supply) {
        this.accumulate_supply = accumulate_supply;
    }

    public AssetBean getTotal_supply() {
        return total_supply;
    }

    public void setTotal_supply(AssetBean total_supply) {
        this.total_supply = total_supply;
    }

    protected HomeAssetBean(Parcel in) {
        address = in.readString();
        name = in.readString();
        mint_type = in.readString();
        status = in.readString();
        inflation = in.readString();
        mining_ratio = in.readString();
        adjust_rate = in.readString();
        adjust_period = in.readString();
        max_adjust_count = in.readString();
        adjust_count = in.readString();
        genesis_height = in.readString();
        expected_genesis_height = in.readString();
        asset = in.readParcelable(AssetBean.class.getClassLoader());
        token = in.readParcelable(AssetBean.class.getClassLoader());
        curr_supply = in.readParcelable(AssetBean.class.getClassLoader());
        lock_supply = in.readParcelable(AssetBean.class.getClassLoader());
        accumulate_supply = in.readParcelable(AssetBean.class.getClassLoader());
        total_supply = in.readParcelable(AssetBean.class.getClassLoader());
    }

    public static final Creator<HomeAssetBean> CREATOR = new Creator<HomeAssetBean>() {
        @Override
        public HomeAssetBean createFromParcel(Parcel in) {
            return new HomeAssetBean(in);
        }

        @Override
        public HomeAssetBean[] newArray(int size) {
            return new HomeAssetBean[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMint_type() {
        return mint_type;
    }

    public void setMint_type(String mint_type) {
        this.mint_type = mint_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInflation() {
        return inflation;
    }

    public void setInflation(String inflation) {
        this.inflation = inflation;
    }

    public String getMining_ratio() {
        return mining_ratio;
    }

    public void setMining_ratio(String mining_ratio) {
        this.mining_ratio = mining_ratio;
    }

    public String getAdjust_rate() {
        return adjust_rate;
    }

    public void setAdjust_rate(String adjust_rate) {
        this.adjust_rate = adjust_rate;
    }

    public String getAdjust_period() {
        return adjust_period;
    }

    public void setAdjust_period(String adjust_period) {
        this.adjust_period = adjust_period;
    }

    public String getMax_adjust_count() {
        return max_adjust_count;
    }

    public void setMax_adjust_count(String max_adjust_count) {
        this.max_adjust_count = max_adjust_count;
    }

    public String getAdjust_count() {
        return adjust_count;
    }

    public void setAdjust_count(String adjust_count) {
        this.adjust_count = adjust_count;
    }

    public String getGenesis_height() {
        return genesis_height;
    }

    public void setGenesis_height(String genesis_height) {
        this.genesis_height = genesis_height;
    }

    public String getExpected_genesis_height() {
        return expected_genesis_height;
    }

    public void setExpected_genesis_height(String expected_genesis_height) {
        this.expected_genesis_height = expected_genesis_height;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {


        dest.writeString(this.address);
        dest.writeString(this.name);
        dest.writeString(this.mint_type);
        dest.writeString(this.status);
        dest.writeString(this.inflation);
        dest.writeString(this.mining_ratio);
        dest.writeString(this.status);
        dest.writeString(this.adjust_rate);
        dest.writeString(this.adjust_period);
        dest.writeString(this.max_adjust_count);
        dest.writeString(this.adjust_count);
        dest.writeString(this.genesis_height);
        dest.writeString(this.expected_genesis_height);

        dest.writeParcelable(this.asset, flag);
        dest.writeParcelable(this.token, flag);
        dest.writeParcelable(this.curr_supply, flag);
        dest.writeParcelable(this.lock_supply, flag);
        dest.writeParcelable(this.accumulate_supply, flag);
        dest.writeParcelable(this.total_supply, flag);
    }


    public static class AssetBean implements Parcelable {


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

        public AssetBean() {
        }

        protected AssetBean(Parcel in) {
            this.denom = in.readString();
            this.amount = in.readString();
        }

        public static final Creator<HomeAssetBean.AssetBean> CREATOR = new Creator<HomeAssetBean.AssetBean>() {
            @Override
            public HomeAssetBean.AssetBean createFromParcel(Parcel source) {
                return new HomeAssetBean.AssetBean(source);
            }

            @Override
            public HomeAssetBean.AssetBean[] newArray(int size) {
                return new HomeAssetBean.AssetBean[size];
            }
        };
    }


    public static class TokenBean implements Parcelable {
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

        public TokenBean() {
        }

        protected TokenBean(Parcel in) {
            this.denom = in.readString();
            this.amount = in.readString();
        }

        public static final Creator<HomeAssetBean.TokenBean> CREATOR = new Creator<HomeAssetBean.TokenBean>() {
            @Override
            public HomeAssetBean.TokenBean createFromParcel(Parcel source) {
                return new HomeAssetBean.TokenBean(source);
            }

            @Override
            public HomeAssetBean.TokenBean[] newArray(int size) {
                return new HomeAssetBean.TokenBean[size];
            }
        };
    }
}
