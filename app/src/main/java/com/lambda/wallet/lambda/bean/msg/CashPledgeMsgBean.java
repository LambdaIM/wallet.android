package com.lambda.wallet.lambda.bean.msg;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/10
 * Time: 15:07
 */
public class CashPledgeMsgBean {

    /**
     * type : cosmos-sdk/MsgDeposit
     * value : {"amount":[{"amount":"1000000","denom":"ulamb"}],"depositor":"lambda163q4m634nq8les4nuvdvz49tk6ae**********","proposal_id":"9"}
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
         * depositor : lambda163q4m634nq8les4nuvdvz49tk6ae**********
         * proposal_id : 9
         */

        private String depositor;
        private String proposal_id;
        private List<AmountBean> amount;

        public String getDepositor() {
            return depositor;
        }

        public void setDepositor(String depositor) {
            this.depositor = depositor;
        }

        public String getProposal_id() {
            return proposal_id;
        }

        public void setProposal_id(String proposal_id) {
            this.proposal_id = proposal_id;
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
