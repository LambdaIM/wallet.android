package com.lambda.wallet.modules.producer;

import android.content.Context;

import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.AllZhiyaTokenBean;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class ProducerDetailsPresenter extends BasePresent<ProducerDetailsView> {
    private Context mContext;

    public ProducerDetailsPresenter(Context context) {
        this.mContext = context;
    }


    public void getProducersData(String address) {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_producers_details + address, mContext, new HashMap<>(), new JsonCallback<ProducersDetailsBean>() {
            @Override
            public void onSuccess(Response<ProducersDetailsBean> response) {
                try {
                    if (response.body() != null) {
                        view.getProducerDataHttp(response.body());
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

    public void getForProducerAwardData(String address) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_for_producers_award + MyApplication.getInstance().getUserBean().getAddress()+"/rewards/"+address, mContext, new HashMap<>(), new JsonCallback<List<AwardBean>>() {
            @Override
            public void onSuccess(Response<List<AwardBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getForProducerAwardDataHttp(response.body(),address);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAllZhiYaTokenData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_chain_all_zhiya_token, mContext, new HashMap<>(), new JsonCallback<AllZhiyaTokenBean>() {
            @Override
            public void onSuccess(Response<AllZhiyaTokenBean> response) {
                try {
                    if (response.body() != null) {
                        view.getAllZhiyaTokenDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

