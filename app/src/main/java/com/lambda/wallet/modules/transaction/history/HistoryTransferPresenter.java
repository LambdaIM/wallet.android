package com.lambda.wallet.modules.transaction.history;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.TransferHistoryBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class HistoryTransferPresenter extends BasePresent<HistoryTransferView> {
    private Context mContext;

    public HistoryTransferPresenter(Context context) {
        this.mContext = context;
    }

    public void getSendData(String address) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("sender",address);
        hashMap.put("page","10000");
        HttpUtils.getRequets(BaseUrl.getHTTP_get_history , mContext, hashMap, new JsonCallback<List<TransferHistoryBean>>() {
            @Override
            public void onSuccess(Response<List<TransferHistoryBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getSendHistoryDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void getReceiveData(String address) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("recipient",address);
        hashMap.put("page","10000");
        HttpUtils.getRequets(BaseUrl.getHTTP_get_history , mContext, hashMap, new JsonCallback<List<TransferHistoryBean>>() {
            @Override
            public void onSuccess(Response<List<TransferHistoryBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getReceiveHistoryDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

