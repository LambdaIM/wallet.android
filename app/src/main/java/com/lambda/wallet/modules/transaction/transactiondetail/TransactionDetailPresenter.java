package com.lambda.wallet.modules.transaction.transactiondetail;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.TransactionDetailBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;


public class TransactionDetailPresenter extends BasePresent<TransactionDetailView> {
    private Context mContext;

    public TransactionDetailPresenter(Context context) {
        this.mContext = context;
    }

    public void getDetailData(String hash) {

        HttpUtils.getRequets(BaseUrl.getHTTP_Get_transaction_detail + hash , mContext, new HashMap<>(), new JsonCallback<TransactionDetailBean>() {
            @Override
            public void onSuccess(Response<TransactionDetailBean> response) {
                try {
                    if (response.body() != null) {
                        view.getDetailDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

