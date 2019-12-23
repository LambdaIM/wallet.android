package com.lambda.wallet.lambda.bean.gas;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/5
 * Time: 16:56
 */
public class PostMiningAwardGasBean {

    /**
     * base_req : {"sequence":"56096","from":"lambda1k6rxrmly7hz0ewh7gth2dj48mv3xs9yz8ffauw","account_number":"535","chain_id":"lambda-chain-test4.0","simulate":true,"memo":""}
     * delegator_address : lambda1k6rxrmly7hz0ewh7gth2dj48mv3xs9yz8ffauw
     */

    private BaseReqBean base_req;
    private String delegator_address;

    public BaseReqBean getBase_req() {
        return base_req;
    }

    public void setBase_req(BaseReqBean base_req) {
        this.base_req = base_req;
    }

    public String getDelegator_address() {
        return delegator_address;
    }

    public void setDelegator_address(String delegator_address) {
        this.delegator_address = delegator_address;
    }

    public static class BaseReqBean {
        /**
         * sequence : 56096
         * from : lambda1k6rxrmly7hz0ewh7gth2dj48mv3xs9yz8ffauw
         * account_number : 535
         * chain_id : lambda-chain-test4.0
         * simulate : true
         * memo :
         */

        private String sequence;
        private String from;
        private String account_number;
        private String chain_id;
        private boolean simulate;
        private String memo;

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

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

        public boolean isSimulate() {
            return simulate;
        }

        public void setSimulate(boolean simulate) {
            this.simulate = simulate;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
}
