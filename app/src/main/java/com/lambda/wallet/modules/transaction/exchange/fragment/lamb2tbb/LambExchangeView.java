package com.lambda.wallet.modules.transaction.exchange.fragment.lamb2tbb;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;


public interface LambExchangeView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

}
