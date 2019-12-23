package com.lambda.wallet.modules.mining.storeorder;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.MyStoreOrderBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class StoreOrderPresenter extends BasePresent<StoreOrderView> {
    private Context mContext;

    public StoreOrderPresenter(Context context) {
        this.mContext = context;
    }

    public void getStoreOrderData(String address) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_my_order_store + address + "/1/" + "100", mContext, new HashMap<>(), new JsonCallback<List<MyStoreOrderBean>>() {
            @Override
            public void onSuccess(Response<List<MyStoreOrderBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getMyStoreOrderDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

