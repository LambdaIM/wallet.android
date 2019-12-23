package com.lambda.wallet.lambda.bean.msg;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/4
 * Time: 18:24
 */
public class TransferMsgBean {


    /**
     * type : cosmos-sdk/MsgSend
     * value : {"amount":[{"amount":"1000000","denom":"ulamb"}],"from_address":"lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt","to_address":"lambda1hynqrp2f80jqs86gu8nd5wwcnek2wwd3esszg0"}
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
         * amount : [{"amount":"1000000","denom":"ulamb"}]
         * from_address : lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt
         * to_address : lambda1hynqrp2f80jqs86gu8nd5wwcnek2wwd3esszg0
         */

        private String from_address;
        private String to_address;
        private List<AmountBean> amount;

        public String getFrom_address() {
            return from_address;
        }

        public void setFrom_address(String from_address) {
            this.from_address = from_address;
        }

        public String getTo_address() {
            return to_address;
        }

        public void setTo_address(String to_address) {
            this.to_address = to_address;
        }

        public List<AmountBean> getAmount() {
            return amount;
        }

        public void setAmount(List<AmountBean> amount) {
            this.amount = amount;
        }

        public static class AmountBean {
            /**
             * amount : 1000000
             * denom : ulamb
             */

            private String amount;
            private String denom;

            public AmountBean(String amount, String denom) {
                this.amount = amount;
                this.denom = denom;
            }

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
