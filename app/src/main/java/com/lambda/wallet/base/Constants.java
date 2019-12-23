package com.lambda.wallet.base;

public class Constants {

    public  final static String GAS_PRICE = "2.5";
    public  final static String MARKETNAME = "LambdaMarket";

    public final static  class SpInfo {

        public  final static String FIRSTUSER = "firstUser";
        public  final static String LAMB = "lamb";
        public  final static String TBB = "tbb";
        public  final static String AWARD1 = "award1";//质押奖励
        public  final static String AWARD2 = "award2";//节点收益
        public  final static String TOKEN = "token";//收益基础token
    }
    public final static  class SignType {
        public  final static String TRANSFER = "cosmos-sdk/MsgSend";
        public  final static String MiningAward = "cosmos-sdk/MsgWithdrawDelegationReward";
        public  final static String ProducerAward = "cosmos-sdk/MsgWithdrawValidatorCommission";
        public  final static String ZHIYA = "lambda/MsgDelegate";
        public  final static String CANCELZHIYA = "lambda/MsgUndelegate";
        public  final static String ZHUANZHIYAZHIYA = "lambda/MsgBeginRedelegate";
        public  final static String LAMB2TBB = "lambda/MsgAssetPledge";
        public  final static String TBB2LAMMB = "lambda/MsgAssetDrop";
        public  final static String BUYSTORE = "lambda/MsgCreateBuyOrder";
        public  final static String MSGDEPOSIT = "cosmos-sdk/MsgDeposit";
        public  final static String MSGVOTE = "cosmos-sdk/MsgVote";
        public  final static String PUB_TYPE = "tendermint/PubKeySecp256k1";
        public  final static String TX_MODE = "block";

    }
}
