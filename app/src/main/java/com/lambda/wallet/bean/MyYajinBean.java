package com.lambda.wallet.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/10
 * Time: 15:26
 */
public class MyYajinBean {

    /**
     * proposal_id : 9
     * depositor : lambda163q4m634nq8les4nuvdvz49tk6ae**********
     * amount : [{"denom":"ulamb","amount":"10002000000"}]
     */

    private String proposal_id;
    private String depositor;
    private List<AmountBean> amount;

    public String getProposal_id() {
        return proposal_id;
    }

    public void setProposal_id(String proposal_id) {
        this.proposal_id = proposal_id;
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public List<AmountBean> getAmount() {
        return amount;
    }

    public void setAmount(List<AmountBean> amount) {
        this.amount = amount;
    }

    public static class AmountBean {
        /**
         * denom : ulamb
         * amount : 10002000000
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
    }
}
