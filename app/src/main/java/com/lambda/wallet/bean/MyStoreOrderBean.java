package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/8
 * Time: 18:31
 */
public class MyStoreOrderBean implements Parcelable {


    /**
     * MatchOrder : {"orderId":"C75E9BFB42978EF06032DC5A74D3128EDC3CDB08","askAddress":"lambdamineroper14z4h3wst6gfw7dtam6zk02ppejy54lktymtphm","buyAddress":"lambda18xcep2669x8at435se6e44n56lnwt248apsxzr","sellOrderId":"0B3C3F212533AE13696E23AD3B446ED5366A1678","buyOrderId":"F9998E5C748B2607639FC839FB1F47E590FA6D9B","price":"5000000","size":"1","createTime":"2019-12-16T07:35:35.871912929Z","endTime":"2020-01-15T07:35:35.871912929Z","cancelTimeDuration":"3600000000000","withDrawTime":"2019-12-16T07:35:35.871912929Z","status":"0","marketAddress":"lambdamarketoper1thj5fv8d0dsh3aealhpxm9mvgxjfh87srk3887","machineName":"machine2","userPay":{"denom":"ulamb","amount":"5000000"},"minerPay":{"denom":"ulamb","amount":"5000000"},"reserved1":""}
     * pubKey : 04ef59849007ab517e746aa3d5932669987eb89c679d7827891dba9da5a30b5d56e224d03da139bdb994154287ebe84475e79ecc2ce74019ac28c24abdbbb49b4b
     * peerId :
     * dhtId : ERbJh3bkCQNaK2MnTSTDfxEsFroLhzPUFoLHey7bDYe3
     */

    private MatchOrderBean MatchOrder;
    private String pubKey;
    private String peerId;
    private String dhtId;

    public MatchOrderBean getMatchOrder() {
        return MatchOrder;
    }

    public void setMatchOrder(MatchOrderBean MatchOrder) {
        this.MatchOrder = MatchOrder;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public String getDhtId() {
        return dhtId;
    }

    public void setDhtId(String dhtId) {
        this.dhtId = dhtId;
    }

    public static class MatchOrderBean implements Parcelable {
        /**
         * orderId : C75E9BFB42978EF06032DC5A74D3128EDC3CDB08
         * askAddress : lambdamineroper14z4h3wst6gfw7dtam6zk02ppejy54lktymtphm
         * buyAddress : lambda18xcep2669x8at435se6e44n56lnwt248apsxzr
         * sellOrderId : 0B3C3F212533AE13696E23AD3B446ED5366A1678
         * buyOrderId : F9998E5C748B2607639FC839FB1F47E590FA6D9B
         * price : 5000000
         * size : 1
         * createTime : 2019-12-16T07:35:35.871912929Z
         * endTime : 2020-01-15T07:35:35.871912929Z
         * cancelTimeDuration : 3600000000000
         * withDrawTime : 2019-12-16T07:35:35.871912929Z
         * status : 0
         * marketAddress : lambdamarketoper1thj5fv8d0dsh3aealhpxm9mvgxjfh87srk3887
         * machineName : machine2
         * userPay : {"denom":"ulamb","amount":"5000000"}
         * minerPay : {"denom":"ulamb","amount":"5000000"}
         * reserved1 :
         */

        private String orderId;
        private String askAddress;
        private String buyAddress;
        private String sellOrderId;
        private String buyOrderId;
        private String price;
        private String size;
        private String createTime;
        private String endTime;
        private String cancelTimeDuration;
        private String withDrawTime;
        private String status;
        private String marketAddress;
        private String machineName;
        private UserPayBean userPay;
        private MinerPayBean minerPay;
        private String reserved1;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAskAddress() {
            return askAddress;
        }

        public void setAskAddress(String askAddress) {
            this.askAddress = askAddress;
        }

        public String getBuyAddress() {
            return buyAddress;
        }

        public void setBuyAddress(String buyAddress) {
            this.buyAddress = buyAddress;
        }

        public String getSellOrderId() {
            return sellOrderId;
        }

        public void setSellOrderId(String sellOrderId) {
            this.sellOrderId = sellOrderId;
        }

        public String getBuyOrderId() {
            return buyOrderId;
        }

        public void setBuyOrderId(String buyOrderId) {
            this.buyOrderId = buyOrderId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getCancelTimeDuration() {
            return cancelTimeDuration;
        }

        public void setCancelTimeDuration(String cancelTimeDuration) {
            this.cancelTimeDuration = cancelTimeDuration;
        }

        public String getWithDrawTime() {
            return withDrawTime;
        }

        public void setWithDrawTime(String withDrawTime) {
            this.withDrawTime = withDrawTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public UserPayBean getUserPay() {
            return userPay;
        }

        public void setUserPay(UserPayBean userPay) {
            this.userPay = userPay;
        }

        public MinerPayBean getMinerPay() {
            return minerPay;
        }

        public void setMinerPay(MinerPayBean minerPay) {
            this.minerPay = minerPay;
        }

        public String getReserved1() {
            return reserved1;
        }

        public void setReserved1(String reserved1) {
            this.reserved1 = reserved1;
        }

        public static class UserPayBean implements Parcelable {
            /**
             * denom : ulamb
             * amount : 5000000
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

            public UserPayBean() {
            }

            protected UserPayBean(Parcel in) {
                this.denom = in.readString();
                this.amount = in.readString();
            }

            public static final Creator<UserPayBean> CREATOR = new Creator<UserPayBean>() {
                @Override
                public UserPayBean createFromParcel(Parcel source) {
                    return new UserPayBean(source);
                }

                @Override
                public UserPayBean[] newArray(int size) {
                    return new UserPayBean[size];
                }
            };
        }

        public static class MinerPayBean implements Parcelable {
            /**
             * denom : ulamb
             * amount : 5000000
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

            public MinerPayBean() {
            }

            protected MinerPayBean(Parcel in) {
                this.denom = in.readString();
                this.amount = in.readString();
            }

            public static final Creator<MinerPayBean> CREATOR = new Creator<MinerPayBean>() {
                @Override
                public MinerPayBean createFromParcel(Parcel source) {
                    return new MinerPayBean(source);
                }

                @Override
                public MinerPayBean[] newArray(int size) {
                    return new MinerPayBean[size];
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
            dest.writeString(this.askAddress);
            dest.writeString(this.buyAddress);
            dest.writeString(this.sellOrderId);
            dest.writeString(this.buyOrderId);
            dest.writeString(this.price);
            dest.writeString(this.size);
            dest.writeString(this.createTime);
            dest.writeString(this.endTime);
            dest.writeString(this.cancelTimeDuration);
            dest.writeString(this.withDrawTime);
            dest.writeString(this.status);
            dest.writeString(this.marketAddress);
            dest.writeString(this.machineName);
            dest.writeParcelable(this.userPay, flags);
            dest.writeParcelable(this.minerPay, flags);
            dest.writeString(this.reserved1);
        }

        public MatchOrderBean() {
        }

        protected MatchOrderBean(Parcel in) {
            this.orderId = in.readString();
            this.askAddress = in.readString();
            this.buyAddress = in.readString();
            this.sellOrderId = in.readString();
            this.buyOrderId = in.readString();
            this.price = in.readString();
            this.size = in.readString();
            this.createTime = in.readString();
            this.endTime = in.readString();
            this.cancelTimeDuration = in.readString();
            this.withDrawTime = in.readString();
            this.status = in.readString();
            this.marketAddress = in.readString();
            this.machineName = in.readString();
            this.userPay = in.readParcelable(UserPayBean.class.getClassLoader());
            this.minerPay = in.readParcelable(MinerPayBean.class.getClassLoader());
            this.reserved1 = in.readString();
        }

        public static final Creator<MatchOrderBean> CREATOR = new Creator<MatchOrderBean>() {
            @Override
            public MatchOrderBean createFromParcel(Parcel source) {
                return new MatchOrderBean(source);
            }

            @Override
            public MatchOrderBean[] newArray(int size) {
                return new MatchOrderBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.MatchOrder, flags);
        dest.writeString(this.pubKey);
        dest.writeString(this.peerId);
        dest.writeString(this.dhtId);
    }

    public MyStoreOrderBean() {
    }

    protected MyStoreOrderBean(Parcel in) {
        this.MatchOrder = in.readParcelable(MatchOrderBean.class.getClassLoader());
        this.pubKey = in.readString();
        this.peerId = in.readString();
        this.dhtId = in.readString();
    }

    public static final Parcelable.Creator<MyStoreOrderBean> CREATOR = new Parcelable.Creator<MyStoreOrderBean>() {
        @Override
        public MyStoreOrderBean createFromParcel(Parcel source) {
            return new MyStoreOrderBean(source);
        }

        @Override
        public MyStoreOrderBean[] newArray(int size) {
            return new MyStoreOrderBean[size];
        }
    };
}
