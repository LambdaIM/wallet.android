package com.lambda.wallet.lambda.bean.msg;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/6
 * Time: 15:02
 */
public class Lamb2TbbMsgBean {

    /**
     * type : lambda/MsgAssetPledge
     * value : {"address":"lambda163q4m634nq8les4nuvdvz49tk6aeh926t0ccsc","asset":{"amount":"1000000","denom":"utbb"},"token":{"amount":"3000000000","denom":"ulamb"}}
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

    public static class ValueBean {
        /**
         * address : lambda163q4m634nq8les4nuvdvz49tk6aeh926t0ccsc
         * asset : {"amount":"1000000","denom":"utbb"}
         * token : {"amount":"3000000000","denom":"ulamb"}
         */

        private String address;
        private AssetBean asset;
        private TokenBean token;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public AssetBean getAsset() {
            return asset;
        }

        public void setAsset(AssetBean asset) {
            this.asset = asset;
        }

        public TokenBean getToken() {
            return token;
        }

        public void setToken(TokenBean token) {
            this.token = token;
        }

        public static class AssetBean {
            /**
             * amount : 1000000
             * denom : utbb
             */

            private String amount;
            private String denom;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDenom() {
                return denom;
            }

            public void setDenom(String denom) {
                this.denom = denom;
            }
        }

        public static class TokenBean {
            /**
             * amount : 3000000000
             * denom : ulamb
             */

            private String amount;
            private String denom;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDenom() {
                return denom;
            }

            public void setDenom(String denom) {
                this.denom = denom;
            }
        }
    }
}
