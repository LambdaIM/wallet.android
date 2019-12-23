package com.lambda.wallet.lambda.bean.msg;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/8
 * Time: 16:41
 */
public class ZhiYaMsgBean {

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
        /**
         * amount : {"amount":"1000000","denom":"utbb"}
         * delegator_address : lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt
         * validator_address : lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl
         * validator_type : 1
         */

        private AmountBean amount;
        private String delegator_address;
        private String validator_address;
        private int validator_type;

        public AmountBean getAmount() {
            return amount;
        }

        public void setAmount(AmountBean amount) {
            this.amount = amount;
        }

        public String getDelegator_address() {
            return delegator_address;
        }

        public void setDelegator_address(String delegator_address) {
            this.delegator_address = delegator_address;
        }

        public String getValidator_address() {
            return validator_address;
        }

        public void setValidator_address(String validator_address) {
            this.validator_address = validator_address;
        }

        public int getValidator_type() {
            return validator_type;
        }

        public void setValidator_type(int validator_type) {
            this.validator_type = validator_type;
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
