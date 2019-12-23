package com.lambda.wallet.modules.mining.storemarket;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.MarketSellListBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class StoreMarketPresenter extends BasePresent<StoreMarketView> {
    private Context mContext;

    public StoreMarketPresenter(Context context) {
        this.mContext = context;
    }

    public void getMarketSellListData() {
        HttpUtils.getRequets(BaseUrl.getHTTP_get_market_sell, mContext, new HashMap<>(), new JsonCallback<List<MarketSellListBean>>() {
            @Override
            public void onSuccess(Response<List<MarketSellListBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getMarketSellListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

