package com.lambda.wallet.lambda.bean.gas;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/8
 * Time: 16:36
 */
public class PostZhiYaGasBean {


    /**
     * base_req : {"account_number":"689","chain_id":"lambda-chain-test4.0","from":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8","memo":"","sequence":"42","simulate":true}
     * amount : {"amount":"1000000","denom":"utbb"}
     * delegator_address : lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt
     * validator_address : lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl
     * validator_type : 1
     */

    private BaseReqBean base_req;
    private AmountBean amount;
    private String delegator_address;
    private String validator_address;
    private int validator_type;

    public BaseReqBean getBase_req() {
        return base_req;
    }

    public void setBase_req(BaseReqBean base_req) {
        this.base_req = base_req;
    }

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
