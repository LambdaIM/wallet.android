package com.lambda.wallet.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/5
 * Time: 15:11
 */
public class ProducerAwardBean {

    /**
     * operator_address : lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt
     * self_bond_rewards : [{"denom":"ulamb","amount":"1318824629026.957070122504282575"}]
     * val_commission : [{"denom":"ulamb","amount":"827774754064.482428092893265856"}]
     */

    private String operator_address;
    private List<SelfBondRewardsBean> self_bond_rewards;
    private List<ValCommissionBean> val_commission;

    public String getOperator_address() {
        return operator_address;
    }

    public void setOperator_address(String operator_address) {
        this.operator_address = operator_address;
    }

    public List<SelfBondRewardsBean> getSelf_bond_rewards() {
        return self_bond_rewards;
    }

    public void setSelf_bond_rewards(List<SelfBondRewardsBean> self_bond_rewards) {
        this.self_bond_rewards = self_bond_rewards;
    }

    public List<ValCommissionBean> getVal_commission() {
        return val_commission;
    }

    public void setVal_commission(List<ValCommissionBean> val_commission) {
        this.val_commission = val_commission;
    }

    public static class SelfBondRewardsBean {
        /**
         * denom : ulamb
         * amount : 1318824629026.957070122504282575
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

    public static class ValCommissionBean {
        /**
         * denom : ulamb
         * amount : 827774754064.482428092893265856
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
