package com.lambda.wallet.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/7
 * Time: 15:59
 */
public class CancelZhiyaBean {

    /**
     * delegator_address : lambda1v664znyhztfx3m0v0uua497r5cptg3rd2ytnm8
     * validator_address : lambdavaloper1prrcl9674j4aqrgrzmys5e28lkcxmntxuvjpcl
     * entries : [{"creation_height":"491709","completion_time":"2019-12-10T06:48:55.689505397Z","initial_balance":"1000000","balance":"1000000"},{"creation_height":"492325","completion_time":"2019-12-10T07:57:29.545590531Z","initial_balance":"100000","balance":"100000"},{"creation_height":"492336","completion_time":"2019-12-10T07:58:32.709835036Z","initial_balance":"134000","balance":"134000"}]
     */

    private String delegator_address;
    private String validator_address;
    private List<EntriesBean> entries;

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

    public List<EntriesBean> getEntries() {
        return entries;
    }

    public void setEntries(List<EntriesBean> entries) {
        this.entries = entries;
    }

    public static class EntriesBean {
        /**
         * creation_height : 491709
         * completion_time : 2019-12-10T06:48:55.689505397Z
         * initial_balance : 1000000
         * balance : 1000000
         */

        private String creation_height;
        private String completion_time;
        private String initial_balance;
        private String balance;

        public String getCreation_height() {
            return creation_height;
        }

        public void setCreation_height(String creation_height) {
            this.creation_height = creation_height;
        }

        public String getCompletion_time() {
            return completion_time;
        }

        public void setCompletion_time(String completion_time) {
            this.completion_time = completion_time;
        }

        public String getInitial_balance() {
            return initial_balance;
        }

        public void setInitial_balance(String initial_balance) {
            this.initial_balance = initial_balance;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
