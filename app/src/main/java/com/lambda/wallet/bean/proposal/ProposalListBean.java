package com.lambda.wallet.bean.proposal;

import java.io.Serializable;
import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/9
 * Time: 14:56
 */
public class ProposalListBean  {

    /**
     * content : {"type":"cosmos-sdk/TextProposal","value":{"title":"文本提案中文换行测试","description":"诺贝尔化学奖揭晓。"}}
     * id : 1
     * proposal_status : Rejected
     * final_tally_result : {"yes":"90000","abstain":"16190009","no":"0","no_with_veto":"0"}
     * submit_time : 2019-10-10T07:21:22.988264879Z
     * deposit_end_time : 2019-10-12T07:21:22.988264879Z
     * total_deposit : [{"denom":"ulamb","amount":"11023000000"}]
     * voting_start_time : 2019-10-11T03:48:59.199943178Z
     * voting_end_time : 2019-10-13T03:48:59.199943178Z
     */

    private ContentBean content;
    private String id;
    private String proposal_status;
    private FinalTallyResultBean final_tally_result;
    private String submit_time;
    private String deposit_end_time;
    private String voting_start_time;
    private String voting_end_time;
    private List<TotalDepositBean> total_deposit;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProposal_status() {
        return proposal_status;
    }

    public void setProposal_status(String proposal_status) {
        this.proposal_status = proposal_status;
    }

    public FinalTallyResultBean getFinal_tally_result() {
        return final_tally_result;
    }

    public void setFinal_tally_result(FinalTallyResultBean final_tally_result) {
        this.final_tally_result = final_tally_result;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getDeposit_end_time() {
        return deposit_end_time;
    }

    public void setDeposit_end_time(String deposit_end_time) {
        this.deposit_end_time = deposit_end_time;
    }

    public String getVoting_start_time() {
        return voting_start_time;
    }

    public void setVoting_start_time(String voting_start_time) {
        this.voting_start_time = voting_start_time;
    }

    public String getVoting_end_time() {
        return voting_end_time;
    }

    public void setVoting_end_time(String voting_end_time) {
        this.voting_end_time = voting_end_time;
    }

    public List<TotalDepositBean> getTotal_deposit() {
        return total_deposit;
    }

    public void setTotal_deposit(List<TotalDepositBean> total_deposit) {
        this.total_deposit = total_deposit;
    }

    public static class ContentBean implements Serializable {
        /**
         * type : cosmos-sdk/TextProposal
         * value : {"title":"文本提案中文换行测试","description":"诺贝尔化学奖揭晓。"}
         */

        private String type;
        private Object value;

        private List<BurnAmountBean> burn_amount;
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public List<BurnAmountBean> getBurn_amount() {
            return burn_amount;
        }

        public void setBurn_amount(List<BurnAmountBean> burn_amount) {
            this.burn_amount = burn_amount;
        }
    }

    public static class FinalTallyResultBean {
        /**
         * yes : 90000
         * abstain : 16190009
         * no : 0
         * no_with_veto : 0
         */

        private String yes;
        private String abstain;
        private String no;
        private String no_with_veto;

        public String getYes() {
            return yes;
        }

        public void setYes(String yes) {
            this.yes = yes;
        }

        public String getAbstain() {
            return abstain;
        }

        public void setAbstain(String abstain) {
            this.abstain = abstain;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getNo_with_veto() {
            return no_with_veto;
        }

        public void setNo_with_veto(String no_with_veto) {
            this.no_with_veto = no_with_veto;
        }
    }

    public static class BurnAmountBean {


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

    public static class TotalDepositBean {
        /**
         * denom : ulamb
         * amount : 11023000000
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
