package com.lambda.wallet.bean.proposal;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/10
 * Time: 16:17
 */
public class MyTouPiaoBean {

    /**
     * proposal_id : 9
     * voter : lambda163q4m634nq8les4nuvdvz49tk6ae**********
     * option : Yes
     */

    private String proposal_id;
    private String voter;
    private String option;

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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
