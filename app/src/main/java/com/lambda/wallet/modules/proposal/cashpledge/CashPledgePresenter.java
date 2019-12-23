package com.lambda.wallet.modules.proposal.cashpledge;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostCashPledgeGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class CashPledgePresenter extends BasePresent<CashPledgeView> {
    private Context mContext;

    public CashPledgePresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String id, PostCashPledgeGasBean postZhiYaGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_cash_pledge_gas + id+ "/deposits", mContext, new Gson().toJson(postZhiYaGasBean), new JsonCallback<GasBean>() {
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

