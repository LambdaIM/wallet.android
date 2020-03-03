package com.lambda.wallet.base;


public class BaseUrl {


    // 读取余额和用户信息
    public  static String HTTP_Get_account = "/auth/accounts";
    // 获取节点列表
    public  static String HTTP_Get_producers = "/staking/validators?status=";
    // 获取节点详情
    public  static String HTTP_Get_producers_details ="/staking/validators/";
    // 获取区块信息
    public  static String HTTP_Get_chain_details = "/node_info";
    // 获取全网质押token总量
    public final static String HTTP_Get_chain_all_zhiya_token ="/staking/pool";
    // 获取市场名称列表接口
    public final static String HTTP_Get_markets =  "/market/markets";
    // 获取市场相关指标接口
    public final static String HTTP_Get_markets_param = "/market/params";


    // 获取转账gas
    public final static String HTTP_Get_transfer_gas = "/bank/accounts/";
    // 获取提取质押奖励以及节点收益gas
    public final static String HTTP_Get_mining_award_gas = "/distribution/delegators/";
    // 获取lammmb兑换tbbgas
    public final static String HTTP_Get_lamb2tbb_gas = "/asset/pledge";
    // 获取tbb兑换lamb gas
    public final static String HTTP_Get_tbb2lamb_gas = "/asset/drop";
    // 获取质押 gas
    public final static String HTTP_Get_zhiya_gas ="/staking/delegators/";
    // 获取购买空间 gas
    public final static String HTTP_Get_buy_store_gas =  "/market/create-buyorder";
    // 获取存入押金的gas
    public final static String HTTP_Get_cash_pledge_gas = "/gov/proposals/";


    //转账
    public final static String HTTP_transfer ="/";
    //获取奖励
    public final static String getHTTP_get_award =  "/distribution/delegators/";
    //获取节点奖励
    public final static String getHTTP_get_producer_award = "/distribution/validators/";
    //获取质押的节点列表
    public final static String getHTTP_get_zhiya_producer = "/staking/delegators/";
    //获取交易记录
    public final static String getHTTP_get_history =  "/txs";


    //获取市场优质的卖单
    public final static String getHTTP_get_market_sell = "/market/sellorders/LambdaMarket/premium/1/100";
    //获取节点详情
    public final static String getHTTP_Get_producers_details ="/staking/validators/";
    //获取用户在一个节点下质押的奖励
    public final static String getHTTP_Get_for_producers_award = "/distribution/delegators/";
    //获取用户取消质押的列表
    public final static String getHTTP_Get_cancel_zhiya = "/staking/delegators/";

    //获取用户空间买卖的订单列表接口
    public final static String getHTTP_Get_my_order_store = "/market/matchorders/";

    //获取提案列表
    public final static String getHTTP_Get_proposal_list ="/gov/proposals";
    //获取提案详情
    public final static String getHTTP_Get_proposal_details = "/gov/proposals/";
    //获取提案公共参数
    public final static String getHTTP_Get_proposal_parameters =  "/gov/parameters/deposit";
    //获取提案我的押金
    public final static String getHTTP_Get_proposal_my_yajin = "/gov/proposals/";
    //获取提案投票信息
    public final static String getHTTP_Get_proposal_toupiao ="/gov/proposals/";

    //获取交易详情
    public final static String getHTTP_Get_transaction_detail ="/txs/";
}
