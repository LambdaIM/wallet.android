package com.lambda.wallet.lambda.bean.msg;


/***
 * 预挖矿 MSG
 * [
 *     {
 *         "type": "lambda/MsgAssetInvest",
 *         "value": {
 *             "address": "lambda1ymms062l3v55tyfkeqp605psvdh4za78k6ufcw",
 *             "asset": "utest31",
 *             "token": {
 *                 "amount": "1000000",
 *                 "denom": "utbb"
 *             }
 *         }
 *     }
 * ]
 */
public class PreMiningMsgBean {

    /**
     * type : lambda/MsgDelegate
     * value : {"amount":{"amount":"1000000","denom":"utbb"},"delegator_address":"lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt","validator_address":"lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl","validator_type":1}
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
        private AmountBean token;
        private String address;
        private String asset;

        public AmountBean getToken() {
            return token;
        }

        public void setToken(AmountBean token) {
            this.token = token;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAsset() {
            return asset;
        }

        public void setAsset(String asset) {
            this.asset = asset;
        }


        public static class AmountBean {
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
    }
}
