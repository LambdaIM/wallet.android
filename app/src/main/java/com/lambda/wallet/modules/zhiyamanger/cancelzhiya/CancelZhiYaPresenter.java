package com.lambda.wallet.modules.zhiyamanger.cancelzhiya;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostZhiYaGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class CancelZhiYaPresenter extends BasePresent<CancelZhiYaView> {
    private Context mContext;

    public CancelZhiYaPresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostZhiYaGasBean postZhiYaGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_zhiya_gas + address + "/unbonding_delegations", mContext, new Gson().toJson(postZhiYaGasBean), new JsonCallback<GasBean>() {
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

