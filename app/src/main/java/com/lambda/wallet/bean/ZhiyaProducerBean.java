package com.lambda.wallet.bean;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/5
 * Time: 16:14
 */
public class ZhiyaProducerBean {

    /**
     * delegator_address : lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8
     * validator_address : lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl
     * shares : 1000000.000000000000000000
     */

    private String delegator_address;
    private String validator_address;
    private String shares;

    public String getDelegator_address() {
        return delegator_address;
    }

    public void setDelegator_address(String delegator_address) {
        this.delegator_address = delegator_address;
    }

    public String getValidator_address() {
        return validator_address;
    }

    public void setValidator_address(String validator_address) {
        this.validator_address = validator_address;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
