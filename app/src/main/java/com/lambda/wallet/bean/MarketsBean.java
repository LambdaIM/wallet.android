package com.lambda.wallet.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/9
 * Time: 14:08
 */
public class MarketsBean {

    /**
     * marketAddress : lambdamarketoper1thj5fv8d0dsh3aealhpxm9mvgxjfh87srk3887
     * name : LambdaMarket
     * owner : lambda1thj5fv8d0dsh3aealhpxm9mvgxjfh87s224esr
     * profit : lambda1thj5fv8d0dsh3aealhpxm9mvgxjfh87s224esr
     * feeRate : 0.020000000000000000
     * commissionRate : 0.020000000000000000
     * inCome : {"feeInCome":[{"denom":"ulamb","amount":"25934490000"}],"commissionInCome":[{"denom":"ulamb","amount":"8574090000"}]}
     * payMent : [{"denom":"ulamb","amount":"1436813500000"}]
     */

    private String marketAddress;
    private String name;
    private String owner;
    private String profit;
    private String feeRate;
    private String commissionRate;
    private InComeBean inCome;
    private List<PayMentBean> payMent;

    public String getMarketAddress() {
        return marketAddress;
    }

    public void setMarketAddress(String marketAddress) {
        this.marketAddress = marketAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public InComeBean getInCome() {
        return inCome;
    }

    public void setInCome(InComeBean inCome) {
        this.inCome = inCome;
    }

    public List<PayMentBean> getPayMent() {
        return payMent;
    }

    public void setPayMent(List<PayMentBean> payMent) {
        this.payMent = payMent;
    }

    public static class InComeBean {
        private List<FeeInComeBean> feeInCome;
        private List<CommissionInComeBean> commissionInCome;

        public List<FeeInComeBean> getFeeInCome() {
            return feeInCome;
        }

        public void setFeeInCome(List<FeeInComeBean> feeInCome) {
            this.feeInCome = feeInCome;
        }

        public List<CommissionInComeBean> getCommissionInCome() {
            return commissionInCome;
        }

        public void setCommissionInCome(List<CommissionInComeBean> commissionInCome) {
            this.commissionInCome = commissionInCome;
        }

        public static class FeeInComeBean {
            /**
             * denom : ulamb
             * amount : 25934490000
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

        public static class CommissionInComeBean {
            /**
             * denom : ulamb
             * amount : 8574090000
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

    public static class PayMentBean {
        /**
         * denom : ulamb
         * amount : 1436813500000
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
