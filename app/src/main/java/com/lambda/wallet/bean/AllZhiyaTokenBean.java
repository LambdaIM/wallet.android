package com.lambda.wallet.bean;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/7
 * Time: 17:30
 * 全网质押的TBB数
 */
public class AllZhiyaTokenBean {


    /**
     * not_bonded_tokens : 999395910677
     * bonded_tokens : 2847822659
     */

    private String not_bonded_tokens;
    private String bonded_tokens;

    public String getNot_bonded_tokens() {
        return not_bonded_tokens;
    }

    public void setNot_bonded_tokens(String not_bonded_tokens) {
        this.not_bonded_tokens = not_bonded_tokens;
    }

    public String getBonded_tokens() {
        return bonded_tokens;
    }

    public void setBonded_tokens(String bonded_tokens) {
        this.bonded_tokens = bonded_tokens;
    }
}
