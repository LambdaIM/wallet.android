package com.lambda.wallet.modules.transaction.exchange.fragment.tbb2lamb;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostLamb2TbbGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class TbbExchangePresenter extends BasePresent<TbbExchangeView> {
    private Context mContext;

    public TbbExchangePresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostLamb2TbbGasBean postLamb2TbbGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_tbb2lamb_gas , mContext, new Gson().toJson(postLamb2TbbGasBean), new JsonCallback<GasBean>() {
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

}

