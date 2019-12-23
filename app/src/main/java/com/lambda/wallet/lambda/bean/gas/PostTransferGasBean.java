package com.lambda.wallet.lambda.bean.gas;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/4
 * Time: 15:57
 */
public class PostTransferGasBean {

    /**
     * base_req : {"sequence":"208","from":"lambda163q4m634nq8les4nuvdvz49tk6aeh926t0ccsc","account_number":"6","chain_id":"lambda-chain-test4.0","simulate":true,"memo":""}
     * amount : [{"amount":"1000000","denom":"ulamb"}]
     * from_address : lambda163q4m634nq8les4nuvdvz49tk6aeh926t0ccsc
     * to_address : lambda16cheh6j34ncyunwgfkq2940cs8222jka0fsp4k
     */

    private BaseReqBean base_req;
    private String from_address;
    private String to_address;
    private List<AmountBean> amount;

    public BaseReqBean getBase_req() {
        return base_req;
    }

    public void setBase_req(BaseReqBean base_req) {
        this.base_req = base_req;
    }

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

    public static class BaseReqBean {
        /**
         * sequence : 208
         * from : lambda163q4m634nq8les4nuvdvz49tk6aeh926t0ccsc
         * account_number : 6
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
