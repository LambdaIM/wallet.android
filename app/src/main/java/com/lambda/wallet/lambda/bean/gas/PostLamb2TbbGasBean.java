package com.lambda.wallet.lambda.bean.gas;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/6
 * Time: 15:01
 */
public class PostLamb2TbbGasBean {

    /**
     * base_req : {"sequence":"56096","from":"lambda1k6rxrmly7hz0ewh7gth2dj48mv3xs9yz8ffauw","account_number":"535","chain_id":"lambda-chain-test4.0","simulate":true,"memo":""}
     * address : lambda163q4m634nq8les4nuvdvz49tk6aeh926t0ccsc
     * asset : {"amount":"1000000","denom":"utbb"}
     * token : {"amount":"3000000000","denom":"ulamb"}
     */

    private BaseReqBean base_req;
    private String address;
    private AssetBean asset;
    private TokenBean token;

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

    public AssetBean getAsset() {
        return asset;
    }

    public void setAsset(AssetBean asset) {
        this.asset = asset;
    }

    public TokenBean getToken() {
        return token;
    }

    public void setToken(TokenBean token) {
        this.token = token;
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

    public static class AssetBean {
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

    public static class TokenBean {
        /**
         * amount : 3000000000
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
