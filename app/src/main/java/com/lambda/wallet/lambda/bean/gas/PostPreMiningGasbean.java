package com.lambda.wallet.lambda.bean.gas;

import java.util.List;

/****
 * 预挖矿获取gas 的bean
 * {
 *  "base_req": {
 *   "sequence": "30",
 *   "from": "lambda1ymms062l3v55tyfkeqp605psvdh4za78k6ufcw",
 *   "account_number": "0",
 *   "chain_id": "lambda-bdd",
 *   "simulate": true,
 *   "memo": ""
 *  },
 *  "address": "lambda1ymms062l3v55tyfkeqp605psvdh4za78k6ufcw",
 *  "asset": "utest31",
 *  "token": {
 *   "amount": "1000000",
 *   "denom": "utbb"
 *  }
 * }
 */
public class PostPreMiningGasbean {


    private BaseReqBean base_req;

    private String address;
    private String asset;
    private AmountBean token;


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

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public AmountBean getToken() {
        return token;
    }

    public void setToken(AmountBean token) {
        this.token = token;
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
