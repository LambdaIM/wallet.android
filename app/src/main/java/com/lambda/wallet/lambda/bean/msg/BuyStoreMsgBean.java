package com.lambda.wallet.lambda.bean.msg;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/8
 * Time: 18:15
 */
public class BuyStoreMsgBean {

    /**
     * type : lambda/MsgCreateBuyOrder
     * value : {"address":"lambda1k6rxrmly7hz0ewh7gth2dj48mv3xs9yz8ffauw","duration":"2592000000000000","marketName":"LambdaMarket","sellOrderId":"00A482D80ACAAA0BEDABB0AA6BE25598967E69DF","size":"1"}
     */

    private String type;
    private ValueBean value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValueBean getValue() {
        return value;
    }

    public void setValue(ValueBean value) {
        this.value = value;
    }

    public static class ValueBean {
        /**
         * address : lambda1k6rxrmly7hz0ewh7gth2dj48mv3xs9yz8ffauw
         * duration : 2592000000000000
         * marketName : LambdaMarket
         * sellOrderId : 00A482D80ACAAA0BEDABB0AA6BE25598967E69DF
         * size : 1
         */

        private String address;
        private String duration;
        private String marketName;
        private String sellOrderId;
        private String size;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getMarketName() {
            return marketName;
        }

        public void setMarketName(String marketName) {
            this.marketName = marketName;
        }

        public String getSellOrderId() {
            return sellOrderId;
        }

        public void setSellOrderId(String sellOrderId) {
            this.sellOrderId = sellOrderId;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}
