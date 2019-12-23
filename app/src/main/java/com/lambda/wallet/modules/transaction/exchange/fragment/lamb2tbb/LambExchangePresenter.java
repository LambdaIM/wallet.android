package com.lambda.wallet.modules.transaction.exchange.fragment.lamb2tbb;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostLamb2TbbGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class LambExchangePresenter extends BasePresent<LambExchangeView> {
    private Context mContext;

    public LambExchangePresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostLamb2TbbGasBean postLamb2TbbGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_lamb2tbb_gas , mContext, new Gson().toJson(postLamb2TbbGasBean), new JsonCallback<GasBean>() {
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

