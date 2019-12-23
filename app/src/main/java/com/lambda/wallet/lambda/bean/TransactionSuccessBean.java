package com.lambda.wallet.lambda.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/5
 * Time: 10:15
 */
public class TransactionSuccessBean {

    /**
     * height : 456379
     * txhash : 8BD3BD40BB8844D4D66E69A8DC3BCC534718FA7816950F60CB085C27894FD05A
     * raw_log : [{"msg_index":"0","success":true,"log":""}]
     * logs : [{"msg_index":"0","success":true,"log":""}]
     * gas_wanted : 36273
     * gas_used : 28587
     * tags : [{"key":"action","value":"send"},{"key":"sender","value":"lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8"},{"key":"recipient","value":"lambda10lg5tce60kd0p3d5f076emyk90vzm4ltmd66n3"}]
     */

    private String height;
    private String txhash;
    private String raw_log;
    private String gas_wanted;
    private String gas_used;
    private List<LogsBean> logs;
    private List<TagsBean> tags;

    @Override
    public String toString() {
        return "TransactionSuccessBean{" +
                "height='" + height + '\'' +
                ", txhash='" + txhash + '\'' +
                ", raw_log='" + raw_log + '\'' +
                ", gas_wanted='" + gas_wanted + '\'' +
                ", gas_used='" + gas_used + '\'' +
                ", logs=" + logs +
                ", tags=" + tags +
                '}';
    }

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
