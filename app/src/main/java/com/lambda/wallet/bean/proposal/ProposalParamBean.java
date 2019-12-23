package com.lambda.wallet.bean.proposal;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/10
 * Time: 11:27
 */
public class ProposalParamBean {

    /**
     * min_deposit : [{"denom":"ulamb","amount":"10000000000"}]
     * max_deposit_period : 172800000000000
     */

    private String max_deposit_period;
    private List<MinDepositBean> min_deposit;

    public String getMax_deposit_period() {
        return max_deposit_period;
    }

    public void setMax_deposit_period(String max_deposit_period) {
        this.max_deposit_period = max_deposit_period;
    }

    public List<MinDepositBean> getMin_deposit() {
        return min_deposit;
    }

    public void setMin_deposit(List<MinDepositBean> min_deposit) {
        this.min_deposit = min_deposit;
    }

    public static class MinDepositBean {
        /**
         * denom : ulamb
         * amount : 10000000000
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
