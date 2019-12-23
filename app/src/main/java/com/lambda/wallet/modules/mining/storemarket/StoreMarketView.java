package com.lambda.wallet.modules.mining.storemarket;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.MarketSellListBean;

import java.util.List;


public interface StoreMarketView extends BaseView {

    void getMarketSellListDataHttp(List<MarketSellListBean> marketSellListBeans);

}
