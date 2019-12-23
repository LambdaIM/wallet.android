package com.lambda.wallet.modules.mining.storemarket.buystore;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.MarketParamsBean;
import com.lambda.wallet.bean.MarketsBean;
import com.lambda.wallet.lambda.bean.gas.PostBuyStoreGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class BuyStorePresenter extends BasePresent<BuyStoreView> {
    private Context mContext;

    public BuyStorePresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(PostBuyStoreGasBean postBuyStoreGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_buy_store_gas, mContext, new Gson().toJson(postBuyStoreGasBean), new JsonCallback<GasBean>() {
            @Override
            public void onSuccess(Response<GasBean> response) {
                try {
                    if (response.body() != null) {
                        view.getGasDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //获取一键购买相关信息
    public void getMarketsData() {

        HttpUtils.getRequets(BaseUrl.HTTP_Get_markets, mContext, new HashMap<>(), new JsonCallback<List<MarketsBean>>() {
            @Override
            public void onSuccess(Response<List<MarketsBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getMarketsDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getMarketsParamsData() {

        HttpUtils.getRequets(BaseUrl.HTTP_Get_markets_param, mContext, new HashMap<>(), new JsonCallback<MarketParamsBean>() {
            @Override
            public void onSuccess(Response<MarketParamsBean> response) {
                try {
                    if (response.body() != null) {
                        view.getMarketsParamsDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}

