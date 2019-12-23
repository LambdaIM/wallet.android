package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/4
 * Time: 10:42
 */
public class ProducersDetailsBean implements Parcelable {

    /**
     * operator_address : lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl
     * consensus_pubkey : lambdavalconspub1zcjduepqp7ccjeshlpslrzjj75je5kn6dhtq8l5nnl6h5jtavgsf5tyekfsqe4mnh9
     * jailed : false
     * status : 2
     * tokens : 1313966657
     * delegator_shares : 1313966657.000000000000000000
     * description : {"moniker":"cv-moniker-2","identity":"","website":"","details":""}
     * unbonding_height : 0
     * unbonding_time : 2019-09-14T06:15:39.017728976Z
     * commission : {"rate":"0.250000000000000000","max_rate":"0.250000000000000000","max_change_rate":"0.010000000000000000","update_time":"2019-08-27T02:48:18.857634087Z"}
     * min_self_delegation : 666666666
     */

    private String operator_address;
    private String consensus_pubkey;
    private boolean jailed;
    private int status;
    private String tokens;
    private String delegator_shares;
    private DescriptionBean description;
    private String unbonding_height;
    private String unbonding_time;
    private CommissionBean commission;
    private String min_self_delegation;

    public String getOperator_address() {
        return operator_address;
    }

    public void setOperator_address(String operator_address) {
        this.operator_address = operator_address;
    }

    public String getConsensus_pubkey() {
        return consensus_pubkey;
    }

    public void setConsensus_pubkey(String consensus_pubkey) {
        this.consensus_pubkey = consensus_pubkey;
    }

    public boolean isJailed() {
        return jailed;
    }

    public void setJailed(boolean jailed) {
        this.jailed = jailed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTokens() {
        return tokens;
    }

    public void setTokens(String tokens) {
        this.tokens = tokens;
    }

    public String getDelegator_shares() {
        return delegator_shares;
    }

    public void setDelegator_shares(String delegator_shares) {
        this.delegator_shares = delegator_shares;
    }

    public DescriptionBean getDescription() {
        return description;
    }

    public void setDescription(DescriptionBean description) {
        this.description = description;
    }

    public String getUnbonding_height() {
        return unbonding_height;
    }

    public void setUnbonding_height(String unbonding_height) {
        this.unbonding_height = unbonding_height;
    }

    public String getUnbonding_time() {
        return unbonding_time;
    }

    public void setUnbonding_time(String unbonding_time) {
        this.unbonding_time = unbonding_time;
    }

    public CommissionBean getCommission() {
        return commission;
    }

    public void setCommission(CommissionBean commission) {
        this.commission = commission;
    }

    public String getMin_self_delegation() {
        return min_self_delegation;
    }

    public void setMin_self_delegation(String min_self_delegation) {
        this.min_self_delegation = min_self_delegation;
    }

    public static class DescriptionBean implements Parcelable {
        /**
         * moniker : cv-moniker-2
         * identity :
         * website :
         * details :
         */

        private String moniker;
        private String identity;
        private String website;
        private String details;

        public String getMoniker() {
            return moniker;
        }

        public void setMoniker(String moniker) {
            this.moniker = moniker;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.moniker);
            dest.writeString(this.identity);
            dest.writeString(this.website);
            dest.writeString(this.details);
        }

        public DescriptionBean() {
        }

        protected DescriptionBean(Parcel in) {
            this.moniker = in.readString();
            this.identity = in.readString();
            this.website = in.readString();
            this.details = in.readString();
        }

        public static final Creator<DescriptionBean> CREATOR = new Creator<DescriptionBean>() {
            @Override
            public DescriptionBean createFromParcel(Parcel source) {
                return new DescriptionBean(source);
            }

            @Override
            public DescriptionBean[] newArray(int size) {
                return new DescriptionBean[size];
            }
        };
    }

    public static class CommissionBean implements Parcelable {
        /**
         * rate : 0.250000000000000000
         * max_rate : 0.250000000000000000
         * max_change_rate : 0.010000000000000000
         * update_time : 2019-08-27T02:48:18.857634087Z
         */

        private String rate;
        private String max_rate;
        private String max_change_rate;
        private String update_time;

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getMax_rate() {
            return max_rate;
        }

        public void setMax_rate(String max_rate) {
            this.max_rate = max_rate;
        }

        public String getMax_change_rate() {
            return max_change_rate;
        }

        public void setMax_change_rate(String max_change_rate) {
            this.max_change_rate = max_change_rate;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.rate);
            dest.writeString(this.max_rate);
            dest.writeString(this.max_change_rate);
            dest.writeString(this.update_time);
        }

        public CommissionBean() {
        }

        protected CommissionBean(Parcel in) {
            this.rate = in.readString();
            this.max_rate = in.readString();
            this.max_change_rate = in.readString();
            this.update_time = in.readString();
        }

        public static final Creator<CommissionBean> CREATOR = new Creator<CommissionBean>() {
            @Override
            public CommissionBean createFromParcel(Parcel source) {
                return new CommissionBean(source);
            }

            @Override
            public CommissionBean[] newArray(int size) {
                return new CommissionBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.operator_address);
        dest.writeString(this.consensus_pubkey);
        dest.writeByte(this.jailed ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status);
        dest.writeString(this.tokens);
        dest.writeString(this.delegator_shares);
        dest.writeParcelable(this.description, flags);
        dest.writeString(this.unbonding_height);
        dest.writeString(this.unbonding_time);
        dest.writeParcelable(this.commission, flags);
        dest.writeString(this.min_self_delegation);
    }

    public ProducersDetailsBean() {
    }

    protected ProducersDetailsBean(Parcel in) {
        this.operator_address = in.readString();
        this.consensus_pubkey = in.readString();
        this.jailed = in.readByte() != 0;
        this.status = in.readInt();
        this.tokens = in.readString();
        this.delegator_shares = in.readString();
        this.description = in.readParcelable(DescriptionBean.class.getClassLoader());
        this.unbonding_height = in.readString();
        this.unbonding_time = in.readString();
        this.commission = in.readParcelable(CommissionBean.class.getClassLoader());
        this.min_self_delegation = in.readString();
    }

    public static final Parcelable.Creator<ProducersDetailsBean> CREATOR = new Parcelable.Creator<ProducersDetailsBean>() {
        @Override
        public ProducersDetailsBean createFromParcel(Parcel source) {
            return new ProducersDetailsBean(source);
        }

        @Override
        public ProducersDetailsBean[] newArray(int size) {
            return new ProducersDetailsBean[size];
        }
    };
}
