package com.lambda.wallet.modules.transaction.transfer;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.bean.gas.PostTransferGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class TransferPresenter extends BasePresent<TransferView> {
    private Context mContext;

    public TransferPresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostTransferGasBean postTransferGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_transfer_gas + address + "/transfers", mContext, new Gson().toJson(postTransferGasBean), new JsonCallback<GasBean>() {
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

