package com.lambda.wallet.modules.transaction.exchange.fragment.tbb2lamb;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;


public interface TbbExchangeView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

}
