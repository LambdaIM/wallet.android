package com.lambda.wallet.lambda.bean.msg;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/5
 * Time: 17:51
 */
public class ProducerAwardMsgBean {


    /**
     * type : cosmos-sdk/MsgWithdrawValidatorCommission
     * value : {"validator_address":"lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl"}
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
         * validator_address : lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl
         */

        private String validator_address;

        public String getValidator_address() {
            return validator_address;
        }

        public void setValidator_address(String validator_address) {
            this.validator_address = validator_address;
        }
    }
}
