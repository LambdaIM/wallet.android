package com.lambda.wallet.lambda.bean.gas;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/8
 * Time: 18:07
 */
public class PostBuyStoreGasBean {

    /**
     * base_req : {"account_number":"689","chain_id":"lambda-chain-test4.0","from":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8","memo":"","sequence":"42","simulate":true}
     * address : lambda1k6rxrmly7hz0ewh7gth2dj48mv3xs9yz8ffauw
     * duration : 2592000000000000
     * marketName : LambdaMarket
     * sellOrderId : 00A482D80ACAAA0BEDABB0AA6BE25598967E69DF
     * size : 1
     */

    private BaseReqBean base_req;
    private String address;
    private String duration;
    private String marketName;
    private String sellOrderId;
    private String size;

    public BaseReqBean getBase_req() {
        return base_req;
    }

    public void setBase_req(BaseReqBean base_req) {
        this.base_req = base_req;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(String sellOrderId) {
        this.sellOrderId = sellOrderId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
}
