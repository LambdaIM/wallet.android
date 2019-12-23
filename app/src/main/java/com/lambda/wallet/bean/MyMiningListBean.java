package com.lambda.wallet.bean;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/7
 * Time: 13:32
 */
public class MyMiningListBean {
    private String delegator_address;
    private String validator_address;
    private String shares;
    private ProducersDetailsBean mProducersDetailsBean;
    private AwardBean mAwardBean;

    public AwardBean getAwardBean() {
        return mAwardBean;
    }

    public void setAwardBean(AwardBean awardBean) {
        mAwardBean = awardBean;
    }

    public String getDelegator_address() {
        return delegator_address == null ? "" : delegator_address;
    }

    public void setDelegator_address(String delegator_address) {
        this.delegator_address = delegator_address;
    }

    public String getValidator_address() {
        return validator_address == null ? "" : validator_address;
    }

    public void setValidator_address(String validator_address) {
        this.validator_address = validator_address;
    }

    public String getShares() {
        return shares == null ? "" : shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public ProducersDetailsBean getProducersDetailsBean() {
        return mProducersDetailsBean;
    }

    public void setProducersDetailsBean(ProducersDetailsBean producersDetailsBean) {
        mProducersDetailsBean = producersDetailsBean;
    }
}
