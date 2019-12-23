package com.lambda.wallet.lambda.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/4
 * Time: 18:20
 */
public class FeeBean {


    /**
     * amount : [{"amount":"101745","denom":"ulamb"}]
     * gas : 40698
     */

    private String gas;
    private List<AmountBean> amount;

    public String getGas() {
        return gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public List<AmountBean> getAmount() {
        return amount;
    }

    public void setAmount(List<AmountBean> amount) {
        this.amount = amount;
    }

    public static class AmountBean {
        /**
         * amount : 101745
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
