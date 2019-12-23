package com.lambda.wallet.modules.transaction.award.fragment.miningaward;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;
import com.lambda.wallet.lambda.bean.gas.PostMiningAwardGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class MiningAwardPresenter extends BasePresent<MiningAwardView> {
    private Context mContext;

    public MiningAwardPresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostMiningAwardGasBean postMiningAwardGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_mining_award_gas + address + "/rewards", mContext, new Gson().toJson(postMiningAwardGasBean), new JsonCallback<GasBean>() {
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
    public void getProducerData(String address) {

        HttpUtils.getRequets(BaseUrl.getHTTP_get_zhiya_producer + address + "/delegations", mContext, new HashMap<>(), new JsonCallback<List<ZhiyaProducerBean>>() {
            @Override
            public void onSuccess(Response<List<ZhiyaProducerBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getZhiyaDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

