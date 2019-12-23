package com.lambda.wallet.modules.proposal.toupiao;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostTouPiaoGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class TouPiaoPresenter extends BasePresent<TouPiaoView> {
    private Context mContext;

    public TouPiaoPresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String id, PostTouPiaoGasBean postTouPiaoGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_cash_pledge_gas + id + "/votes", mContext, new Gson().toJson(postTouPiaoGasBean), new JsonCallback<GasBean>() {
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

