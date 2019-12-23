package com.lambda.wallet.modules.proposal.cashpledge;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;


public interface CashPledgeView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

}
