package com.lambda.wallet.modules.proposal.toupiao;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;


public interface TouPiaoView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

}
