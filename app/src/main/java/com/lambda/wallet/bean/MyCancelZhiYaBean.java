package com.lambda.wallet.bean;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/7
 * Time: 16:12
 */
public class MyCancelZhiYaBean {
    String delegator_address;
    private CancelZhiyaBean.EntriesBean mEntriesBean ;

    public String getDelegator_address() {
        return delegator_address == null ? "" : delegator_address;
    }

    public void setDelegator_address(String delegator_address) {
        this.delegator_address = delegator_address;
    }

    public CancelZhiyaBean.EntriesBean getEntriesBean() {
        return mEntriesBean;
    }

    public void setEntriesBean(CancelZhiyaBean.EntriesBean entriesBean) {
        mEntriesBean = entriesBean;
    }
}
