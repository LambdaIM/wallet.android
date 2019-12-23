package com.lambda.wallet.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/6
 * Time: 18:19
 */
public class TransferHistoryBean {

    /**
     * height : 455227
     * txhash : 79A44A70B7F11FE2F952F45DEA34F6C4B075E5357D55B3098F632AC6974FC15F
     * raw_log : [{"msg_index":"0","success":true,"log":""}]
     * logs : [{"msg_index":"0","success":true,"log":""}]
     * gas_wanted : 36188
     * gas_used : 27816
     * tags : [{"key":"action","value":"send"},{"key":"sender","value":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8"},{"key":"recipient","value":"lambda18n5288wlgu2ggxk0pf78phu0cz4dscfctxtasf"}]
     * tx : {"type":"auth/StdTx","value":{"msg":[{"type":"cosmos-sdk/MsgSend","value":{"from_address":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8","to_address":"lambda18n5288wlgu2ggxk0pf78phu0cz4dscfctxtasf","amount":[{"denom":"ulamb","amount":"100000"}]}}],"fee":{"amount":[{"denom":"ulamb","amount":"90470"}],"gas":"36188"},"signatures":[{"pub_key":{"type":"tendermint/PubKeySecp256k1","value":"AjoPLqQ2P8UI9fYi0EiLq85CZSyaFSSFcBak2jKv9K5r"},"signature":"nGXFq/qwNtolmvevNQLQGlDXqFbhwM+N4j6zUnxMOrUeQQZBrwRK3DHaYzsl7HyZwdk6VQlf60u4qIDtl0Hs/w=="}],"memo":""}}
     * timestamp : 2019-12-04T11:37:05Z
     */

    private String height;
    private String txhash;
    private String raw_log;
    private String gas_wanted;
    private String gas_used;
    private TxBean tx;
    private String timestamp;
    private List<LogsBean> logs;
    private List<TagsBean> tags;

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTxhash() {
        return txhash;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public String getRaw_log() {
        return raw_log;
    }

    public void setRaw_log(String raw_log) {
        this.raw_log = raw_log;
    }

    public String getGas_wanted() {
        return gas_wanted;
    }

    public void setGas_wanted(String gas_wanted) {
        this.gas_wanted = gas_wanted;
    }

    public String getGas_used() {
        return gas_used;
    }

    public void setGas_used(String gas_used) {
        this.gas_used = gas_used;
    }

    public TxBean getTx() {
        return tx;
    }

    public void setTx(TxBean tx) {
        this.tx = tx;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public List<LogsBean> getLogs() {
        return logs;
    }

    public void setLogs(List<LogsBean> logs) {
        this.logs = logs;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public static class TxBean {
        /**
         * type : auth/StdTx
         * value : {"msg":[{"type":"cosmos-sdk/MsgSend","value":{"from_address":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8","to_address":"lambda18n5288wlgu2ggxk0pf78phu0cz4dscfctxtasf","amount":[{"denom":"ulamb","amount":"100000"}]}}],"fee":{"amount":[{"denom":"ulamb","amount":"90470"}],"gas":"36188"},"signatures":[{"pub_key":{"type":"tendermint/PubKeySecp256k1","value":"AjoPLqQ2P8UI9fYi0EiLq85CZSyaFSSFcBak2jKv9K5r"},"signature":"nGXFq/qwNtolmvevNQLQGlDXqFbhwM+N4j6zUnxMOrUeQQZBrwRK3DHaYzsl7HyZwdk6VQlf60u4qIDtl0Hs/w=="}],"memo":""}
         */

        private String type;
        private ValueBeanX value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ValueBeanX getValue() {
            return value;
        }

        public void setValue(ValueBeanX value) {
            this.value = value;
        }

        public static class ValueBeanX {
            /**
             * msg : [{"type":"cosmos-sdk/MsgSend","value":{"from_address":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8","to_address":"lambda18n5288wlgu2ggxk0pf78phu0cz4dscfctxtasf","amount":[{"denom":"ulamb","amount":"100000"}]}}]
             * fee : {"amount":[{"denom":"ulamb","amount":"90470"}],"gas":"36188"}
             * signatures : [{"pub_key":{"type":"tendermint/PubKeySecp256k1","value":"AjoPLqQ2P8UI9fYi0EiLq85CZSyaFSSFcBak2jKv9K5r"},"signature":"nGXFq/qwNtolmvevNQLQGlDXqFbhwM+N4j6zUnxMOrUeQQZBrwRK3DHaYzsl7HyZwdk6VQlf60u4qIDtl0Hs/w=="}]
             * memo :
             */

            private FeeBean fee;
            private String memo;
            private List<MsgBean> msg;
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

            public List<MsgBean> getMsg() {
                return msg;
            }

            public void setMsg(List<MsgBean> msg) {
                this.msg = msg;
            }

            public List<SignaturesBean> getSignatures() {
                return signatures;
            }

            public void setSignatures(List<SignaturesBean> signatures) {
                this.signatures = signatures;
            }

            public static class FeeBean {
                /**
                 * amount : [{"denom":"ulamb","amount":"90470"}]
                 * gas : 36188
                 */

                private String gas;
                private List<AmountBean> amount;

                public String getGas() {
                    return gas;
                }

                public void setGas(String gas) {
                    this.gas = gas;
                }

                public List<AmountBean> getAmount() {
                    return amount;
                }

                public void setAmount(List<AmountBean> amount) {
                    this.amount = amount;
                }

                public static class AmountBean {
                    /**
                     * denom : ulamb
                     * amount : 90470
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

            public static class MsgBean {
                /**
                 * type : cosmos-sdk/MsgSend
                 * value : {"from_address":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8","to_address":"lambda18n5288wlgu2ggxk0pf78phu0cz4dscfctxtasf","amount":[{"denom":"ulamb","amount":"100000"}]}
                 */

                private String type;
                private ValueBean value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public ValueBean getValue() {
                    return value;
                }

                public void setValue(ValueBean value) {
                    this.value = value;
                }

                public static class ValueBean {
                    /**
                     * from_address : lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8
                     * to_address : lambda18n5288wlgu2ggxk0pf78phu0cz4dscfctxtasf
                     * amount : [{"denom":"ulamb","amount":"100000"}]
                     */

                    private String from_address;
                    private String to_address;
                    private List<AmountBeanX> amount;

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

                    public List<AmountBeanX> getAmount() {
                        return amount;
                    }

                    public void setAmount(List<AmountBeanX> amount) {
                        this.amount = amount;
                    }

                    public static class AmountBeanX {
                        /**
                         * denom : ulamb
                         * amount : 100000
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
            }

            public static class SignaturesBean {
                /**
                 * pub_key : {"type":"tendermint/PubKeySecp256k1","value":"AjoPLqQ2P8UI9fYi0EiLq85CZSyaFSSFcBak2jKv9K5r"}
                 * signature : nGXFq/qwNtolmvevNQLQGlDXqFbhwM+N4j6zUnxMOrUeQQZBrwRK3DHaYzsl7HyZwdk6VQlf60u4qIDtl0Hs/w==
                 */

                private PubKeyBean pub_key;
                private String signature;

                public PubKeyBean getPub_key() {
                    return pub_key;
                }

                public void setPub_key(PubKeyBean pub_key) {
                    this.pub_key = pub_key;
                }

                public String getSignature() {
                    return signature;
                }

                public void setSignature(String signature) {
                    this.signature = signature;
                }

                public static class PubKeyBean {
                    /**
                     * type : tendermint/PubKeySecp256k1
                     * value : AjoPLqQ2P8UI9fYi0EiLq85CZSyaFSSFcBak2jKv9K5r
                     */

                    private String type;
                    private String value;

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
        }
    }

    public static class LogsBean {
        /**
         * msg_index : 0
         * success : true
         * log :
         */

        private String msg_index;
        private boolean success;
        private String log;

        public String getMsg_index() {
            return msg_index;
        }

        public void setMsg_index(String msg_index) {
            this.msg_index = msg_index;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }

    public static class TagsBean {
        /**
         * key : action
         * value : send
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
