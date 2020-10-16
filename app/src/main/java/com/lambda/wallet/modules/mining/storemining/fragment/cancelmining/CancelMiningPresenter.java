package com.lambda.wallet.modules.mining.storemining.fragment.cancelmining;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.CancelZhiyaBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class CancelMiningPresenter extends BasePresent<CancelMiningView> {
    private Context mContext;

    public CancelMiningPresenter(Context context) {
        this.mContext = context;
    }


    public void getCancelData(String address) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_cancel_zhiya + address + "/unbonding_delegations", mContext, new HashMap<>(), new JsonCallback<List<CancelZhiyaBean>>() {
            @Override
            public void onSuccess(Response<List<CancelZhiyaBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getCancelZhiyaDataHttp(response.body());
                    }else {
                        view.getCancelZhiyaDataHttp();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<List<CancelZhiyaBean>> response) {
                super.onError(response);
                view.getCancelZhiyaDataHttp();
            }
        });

    }

}

