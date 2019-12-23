package com.lambda.wallet.bean.proposal;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/10
 * Time: 16:07
 */
public class TouPiaoBean {


    /**
     * yes : 0
     * abstain : 0
     * no : 0
     * no_with_veto : 3136000
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
