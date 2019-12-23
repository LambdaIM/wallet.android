package com.lambda.wallet.lambda.bean.gas;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/10
 * Time: 14:58
 */
public class PostCashPledgeGasBean {


    /**
     * base_req : {"account_number":"689","chain_id":"lambda-chain-test4.0","from":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8","memo":"","sequence":"42","simulate":true}
     * amount : [{"amount":"1000000","denom":"ulamb"}]
     * depositor : lambda163q4m634nq8les4nuvdvz49tk6ae**********
     * proposal_id : 9
     */

    private BaseReqBean base_req;
    private String depositor;
    private String proposal_id;
    private List<AmountBean> amount;

    public BaseReqBean getBase_req() {
        return base_req;
    }

    public void setBase_req(BaseReqBean base_req) {
        this.base_req = base_req;
    }

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

    public static class BaseReqBean {
        /**
         * account_number : 689
         * chain_id : lambda-chain-test4.0
         * from : lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8
         * memo :
         * sequence : 42
         * simulate : true
         */

        private String account_number;
        private String chain_id;
        private String from;
        private String memo;
        private String sequence;
        private boolean simulate;

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getChain_id() {
            return chain_id;
        }

        public void setChain_id(String chain_id) {
            this.chain_id = chain_id;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public boolean isSimulate() {
            return simulate;
        }

        public void setSimulate(boolean simulate) {
            this.simulate = simulate;
        }
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
