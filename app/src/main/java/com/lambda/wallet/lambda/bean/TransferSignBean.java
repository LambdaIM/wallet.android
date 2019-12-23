package com.lambda.wallet.lambda.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/4
 * Time: 18:17
 */
public class TransferSignBean {

    /**
     * tx : {"msg":[{"type":"cosmos-sdk/MsgSend","value":{"amount":[{"amount":"1000000","denom":"ulamb"}],"from_address":"lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt","to_address":"lambda1hynqrp2f80jqs86gu8nd5wwcnek2wwd3esszg0"}}],"fee":{"amount":[{"amount":"101745","denom":"ulamb"}],"gas":"40698"},"signatures":[{"signature":"fa9bUlNRA3qa9PEYR2py6CgpQbbqVsuKhJRowMdlf90byj7M/2B1YQsu6EPAk1V/tLkKiNwEadkAKNFUxZngGA==","pub_key":{"type":"tendermint/PubKeySecp256k1","value":"AjmQ01Z+IoHuKLdPaFzV6IJQB88ahW2qv2rEw2H4B5dq"}}],"memo":""}
     * mode : async
     */

    private TxBean tx;
    private String mode;

    public TxBean getTx() {
        return tx;
    }

    public void setTx(TxBean tx) {
        this.tx = tx;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public static class TxBean<T> {
        /**
         * msg : [{"type":"cosmos-sdk/MsgSend","value":{"amount":[{"amount":"1000000","denom":"ulamb"}],"from_address":"lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt","to_address":"lambda1hynqrp2f80jqs86gu8nd5wwcnek2wwd3esszg0"}}]
         * fee : {"amount":[{"amount":"101745","denom":"ulamb"}],"gas":"40698"}
         * signatures : [{"signature":"fa9bUlNRA3qa9PEYR2py6CgpQbbqVsuKhJRowMdlf90byj7M/2B1YQsu6EPAk1V/tLkKiNwEadkAKNFUxZngGA==","pub_key":{"type":"tendermint/PubKeySecp256k1","value":"AjmQ01Z+IoHuKLdPaFzV6IJQB88ahW2qv2rEw2H4B5dq"}}]
         * memo :
         */

        private FeeBean fee;
        private String memo;
        private List<T> msg;
        private List<SignaturesBean> signatures;

        public FeeBean getFee() {
            return fee;
        }

        public void setFee(FeeBean fee) {
            this.fee = fee;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public List<T> getMsg() {
            return msg;
        }

        public void setMsg(List<T> msg) {
            this.msg = msg;
        }

        public List<SignaturesBean> getSignatures() {
            return signatures;
        }

        public void setSignatures(List<SignaturesBean> signatures) {
            this.signatures = signatures;
        }

    }
}
