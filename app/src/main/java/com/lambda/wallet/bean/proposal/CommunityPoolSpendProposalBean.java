package com.lambda.wallet.bean.proposal;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/9
 * Time: 16:20
 */
public class CommunityPoolSpendProposalBean {
    /**
     * title : Community Pool Spend
     * description : Pay me some lamb!
     * recipient : lambda1thj5fv8d0dsh3aealhpxm9mvgxjfh87s224esr
     * amount : [{"denom":"ulamb","amount":"10000"}]
     */

    private String title;
    private String description;
    private String recipient;
    private List<AmountBean> amount;

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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public List<AmountBean> getAmount() {
        return amount;
    }

    public void setAmount(List<AmountBean> amount) {
        this.amount = amount;
    }

    public static class AmountBean {
        /**
         * denom : ulamb
         * amount : 10000
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
