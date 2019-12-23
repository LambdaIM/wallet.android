package com.lambda.wallet.lambda.bean.msg;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/10
 * Time: 18:12
 */
public class TouPiaoMsgBean {

    /**
     * type : cosmos-sdk/MsgVote
     * value : {"option":"Yes","proposal_id":"9","voter":"lambda163q4m634nq8les4nuvdvz49tk6ae**********"}
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
         * option : Yes
         * proposal_id : 9
         * voter : lambda163q4m634nq8les4nuvdvz49tk6ae**********
         */

        private String option;
        private String proposal_id;
        private String voter;

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getProposal_id() {
            return proposal_id;
        }

        public void setProposal_id(String proposal_id) {
            this.proposal_id = proposal_id;
        }

        public String getVoter() {
            return voter;
        }

        public void setVoter(String voter) {
            this.voter = voter;
        }
    }
}
