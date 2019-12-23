package com.lambda.wallet.modules.mining.storemarket.buystore;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.MarketParamsBean;
import com.lambda.wallet.bean.MarketsBean;

import java.util.List;


public interface BuyStoreView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

    void getMarketsDataHttp(List<MarketsBean> marketsBeans);

    void getMarketsParamsDataHttp(MarketParamsBean marketParamsBean);

}
