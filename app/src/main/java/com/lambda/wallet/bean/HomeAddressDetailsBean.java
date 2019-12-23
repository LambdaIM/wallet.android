package com.lambda.wallet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/3
 * Time: 15:11
 */
public class HomeAddressDetailsBean implements Parcelable {

    /**
     * type : auth/Account
     * value : {"address":"lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt","coins":[{"denom":"ulamb","amount":"10848193224664"},{"denom":"utbb","amount":"10031000004"},{"denom":"ufeiniao","amount":"10031000004"}],"public_key":{"type":"tendermint/PubKeySecp256k1","value":"AjmQ01Z+IoHuKLdPaFzV6IJQB88ahW2qv2rEw2H4B5dq"},"account_number":"1","sequence":"114"}
     */

    private String type;
    private ValueBean value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public static class ValueBean implements Parcelable {
        /**
         * address : lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt
         * coins : [{"denom":"ulamb","amount":"10848193224664"},{"denom":"utbb","amount":"10031000004"},{"denom":"ufeiniao","amount":"10031000004"}]
         * public_key : {"type":"tendermint/PubKeySecp256k1","value":"AjmQ01Z+IoHuKLdPaFzV6IJQB88ahW2qv2rEw2H4B5dq"}
         * account_number : 1
         * sequence : 114
         */

        private String address;
        private PublicKeyBean public_key;
        private String account_number;
        private String sequence;
        private List<CoinsBean> coins;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public PublicKeyBean getPublic_key() {
            return public_key;
        }

        public void setPublic_key(PublicKeyBean public_key) {
            this.public_key = public_key;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public List<CoinsBean> getCoins() {
            return coins;
        }

        public void setCoins(List<CoinsBean> coins) {
            this.coins = coins;
        }

        public static class PublicKeyBean implements Parcelable {
            /**
             * type : tendermint/PubKeySecp256k1
             * value : AjmQ01Z+IoHuKLdPaFzV6IJQB88ahW2qv2rEw2H4B5dq
             */

            private String type;
            private String value;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.type);
                dest.writeString(this.value);
            }

            public PublicKeyBean() {
            }

            protected PublicKeyBean(Parcel in) {
                this.type = in.readString();
                this.value = in.readString();
            }

            public static final Creator<PublicKeyBean> CREATOR = new Creator<PublicKeyBean>() {
                @Override
                public PublicKeyBean createFromParcel(Parcel source) {
                    return new PublicKeyBean(source);
                }

                @Override
                public PublicKeyBean[] newArray(int size) {
                    return new PublicKeyBean[size];
                }
            };
        }

        public static class CoinsBean implements Parcelable {
            /**
             * denom : ulamb
             * amount : 10848193224664
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

            public CoinsBean() {
            }

            protected CoinsBean(Parcel in) {
                this.denom = in.readString();
                this.amount = in.readString();
            }

            public static final Parcelable.Creator<CoinsBean> CREATOR = new Parcelable.Creator<CoinsBean>() {
                @Override
                public CoinsBean createFromParcel(Parcel source) {
                    return new CoinsBean(source);
                }

                @Override
                public CoinsBean[] newArray(int size) {
                    return new CoinsBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeParcelable(this.public_key, flags);
            dest.writeString(this.account_number);
            dest.writeString(this.sequence);
            dest.writeTypedList(this.coins);
        }

        public ValueBean() {
        }

        protected ValueBean(Parcel in) {
            this.address = in.readString();
            this.public_key = in.readParcelable(PublicKeyBean.class.getClassLoader());
            this.account_number = in.readString();
            this.sequence = in.readString();
            this.coins = in.createTypedArrayList(CoinsBean.CREATOR);
        }

        public static final Creator<ValueBean> CREATOR = new Creator<ValueBean>() {
            @Override
            public ValueBean createFromParcel(Parcel source) {
                return new ValueBean(source);
            }

            @Override
            public ValueBean[] newArray(int size) {
                return new ValueBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeParcelable(this.value, flags);
    }

    public HomeAddressDetailsBean() {
    }

    protected HomeAddressDetailsBean(Parcel in) {
        this.type = in.readString();
        this.value = in.readParcelable(ValueBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeAddressDetailsBean> CREATOR = new Parcelable.Creator<HomeAddressDetailsBean>() {
        @Override
        public HomeAddressDetailsBean createFromParcel(Parcel source) {
            return new HomeAddressDetailsBean(source);
        }

        @Override
        public HomeAddressDetailsBean[] newArray(int size) {
            return new HomeAddressDetailsBean[size];
        }
    };
}
