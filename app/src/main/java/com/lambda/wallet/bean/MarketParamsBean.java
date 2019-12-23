package com.lambda.wallet.bean;

import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/9
 * Time: 14:13
 */
public class MarketParamsBean {

    /**
     * market_min_rate : 0.000000000000000000
     * market_max_rate : 1.000000000000000000
     * market_min_price : 5000000
     * order_normal_price : 5000000
     * order_normal_rate : 1.000000000000000000
     * order_premium_rate : 3.000000000000000000
     * order_cancel_time_duration : 3600000000000
     * order_min_buy_size : 1
     * order_min_buy_duration : 2592000000000000
     * order_max_buy_duration : 155520000000000000
     * order_with_draw_min_duration : 86400000000000
     * price_unit_set : ["ulamb"]
     * size_unit_set : ["GB"]
     * duration_unit_set : ["month"]
     */

    private String market_min_rate;
    private String market_max_rate;
    private String market_min_price;
    private String order_normal_price;
    private String order_normal_rate;
    private String order_premium_rate;
    private String order_cancel_time_duration;
    private String order_min_buy_size;
    private String order_min_buy_duration;
    private String order_max_buy_duration;
    private String order_with_draw_min_duration;
    private List<String> price_unit_set;
    private List<String> size_unit_set;
    private List<String> duration_unit_set;

    public String getMarket_min_rate() {
        return market_min_rate;
    }

    public void setMarket_min_rate(String market_min_rate) {
        this.market_min_rate = market_min_rate;
    }

    public String getMarket_max_rate() {
        return market_max_rate;
    }

    public void setMarket_max_rate(String market_max_rate) {
        this.market_max_rate = market_max_rate;
    }

    public String getMarket_min_price() {
        return market_min_price;
    }

    public void setMarket_min_price(String market_min_price) {
        this.market_min_price = market_min_price;
    }

    public String getOrder_normal_price() {
        return order_normal_price;
    }

    public void setOrder_normal_price(String order_normal_price) {
        this.order_normal_price = order_normal_price;
    }

    public String getOrder_normal_rate() {
        return order_normal_rate;
    }

    public void setOrder_normal_rate(String order_normal_rate) {
        this.order_normal_rate = order_normal_rate;
    }

    public String getOrder_premium_rate() {
        return order_premium_rate;
    }

    public void setOrder_premium_rate(String order_premium_rate) {
        this.order_premium_rate = order_premium_rate;
    }

    public String getOrder_cancel_time_duration() {
        return order_cancel_time_duration;
    }

    public void setOrder_cancel_time_duration(String order_cancel_time_duration) {
        this.order_cancel_time_duration = order_cancel_time_duration;
    }

    public String getOrder_min_buy_size() {
        return order_min_buy_size;
    }

    public void setOrder_min_buy_size(String order_min_buy_size) {
        this.order_min_buy_size = order_min_buy_size;
    }

    public String getOrder_min_buy_duration() {
        return order_min_buy_duration;
    }

    public void setOrder_min_buy_duration(String order_min_buy_duration) {
        this.order_min_buy_duration = order_min_buy_duration;
    }

    public String getOrder_max_buy_duration() {
        return order_max_buy_duration;
    }

    public void setOrder_max_buy_duration(String order_max_buy_duration) {
        this.order_max_buy_duration = order_max_buy_duration;
    }

    public String getOrder_with_draw_min_duration() {
        return order_with_draw_min_duration;
    }

    public void setOrder_with_draw_min_duration(String order_with_draw_min_duration) {
        this.order_with_draw_min_duration = order_with_draw_min_duration;
    }

    public List<String> getPrice_unit_set() {
        return price_unit_set;
    }

    public void setPrice_unit_set(List<String> price_unit_set) {
        this.price_unit_set = price_unit_set;
    }

    public List<String> getSize_unit_set() {
        return size_unit_set;
    }

    public void setSize_unit_set(List<String> size_unit_set) {
        this.size_unit_set = size_unit_set;
    }

    public List<String> getDuration_unit_set() {
        return duration_unit_set;
    }

    public void setDuration_unit_set(List<String> duration_unit_set) {
        this.duration_unit_set = duration_unit_set;
    }
}
