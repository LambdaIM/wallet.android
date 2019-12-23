package com.lambda.wallet.bean.proposal;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/9
 * Time: 16:19
 */
public class ParameterChangeProposalBean {


    /**
     * title : Market Param Change
     * description : Modify min withdraw duration
     * changes : [{"subspace":"market","key":"OrderWithDrawMinDuration","value":"\"86400000000000\""}]
     */

    private String title;
    private String description;
    private List<ChangesBean> changes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ChangesBean> getChanges() {
        return changes;
    }

    public void setChanges(List<ChangesBean> changes) {
        this.changes = changes;
    }

    public static class ChangesBean {
        /**
         * subspace : market
         * key : OrderWithDrawMinDuration
         * value : "86400000000000"
         */

        private String subspace;
        private String key;
        private String value;

        public String getSubspace() {
            return subspace;
        }

        public void setSubspace(String subspace) {
            this.subspace = subspace;
        }

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
