package com.lambda.wallet.modules.zhiyamanger.zhiya;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostZhiYaGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class ZhiYaPresenter extends BasePresent<ZhiYaView> {
    private Context mContext;

    public ZhiYaPresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostZhiYaGasBean postZhiYaGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_zhiya_gas + address + "/delegations", mContext, new Gson().toJson(postZhiYaGasBean), new JsonCallback<GasBean>() {
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

