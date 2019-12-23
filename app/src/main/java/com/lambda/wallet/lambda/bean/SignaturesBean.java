package com.lambda.wallet.lambda.bean;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/4
 * Time: 18:28
 */
public class SignaturesBean {
    private String signature;
    private PubKeyBean pub_key;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public PubKeyBean getPub_key() {
        return pub_key;
    }

    public void setPub_key(PubKeyBean pub_key) {
        this.pub_key = pub_key;
    }

    public static class PubKeyBean {
        /**
         * type : tendermint/PubKeySecp256k1
         * value : AjmQ01Z+IoHuKLdPaFzV6IJQB88ahW2qv2rEw2H4B5dq
         */

        private String type;
        private String value;

        public PubKeyBean(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
