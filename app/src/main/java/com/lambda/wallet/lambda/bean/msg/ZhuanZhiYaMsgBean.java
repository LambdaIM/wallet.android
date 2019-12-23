package com.lambda.wallet.lambda.bean.msg;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/8
 * Time: 17:47
 */
public class ZhuanZhiYaMsgBean {

    /**
     * type : lambda/MsgBeginRedelegate
     * value : {"amount":{"amount":"1000000","denom":"utbb"},"delegator_address":"lambda163q4m634nq8les4nuvdvz49tk6ae**********","validator_dst_address":"lambdavaloper1r340rrv9fs95gqy5087e2mtz82vvwrglt6amx3","validator_src_address":"lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl","validator_type":1}
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
         * delegator_address : lambda163q4m634nq8les4nuvdvz49tk6ae**********
         * validator_dst_address : lambdavaloper1r340rrv9fs95gqy5087e2mtz82vvwrglt6amx3
         * validator_src_address : lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl
         * validator_type : 1
         */

        private AmountBean amount;
        private String delegator_address;
        private String validator_dst_address;
        private String validator_src_address;
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

        public String getValidator_dst_address() {
            return validator_dst_address;
        }

        public void setValidator_dst_address(String validator_dst_address) {
            this.validator_dst_address = validator_dst_address;
        }

        public String getValidator_src_address() {
            return validator_src_address;
        }

        public void setValidator_src_address(String validator_src_address) {
            this.validator_src_address = validator_src_address;
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
