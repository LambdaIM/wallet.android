package com.lambda.wallet.modules.transaction.award.fragment.produceraward;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostProducerAwardGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class ProducerAwardPresenter extends BasePresent<ProducerAwardView> {
    private Context mContext;

    public ProducerAwardPresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostProducerAwardGasBean postProducerAwardGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_mining_award_gas + address + "/rewards", mContext, new Gson().toJson(postProducerAwardGasBean), new JsonCallback<GasBean>() {
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

