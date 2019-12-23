package com.lambda.wallet.modules.zhiyamanger.zhuanzhiya;

import android.content.Context;

import com.google.gson.Gson;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.lambda.bean.gas.PostZhuanZhiYaGasBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class ZhuanZhiYaPresenter extends BasePresent<ZhuanZhiYaView> {
    private Context mContext;

    public ZhuanZhiYaPresenter(Context context) {
        this.mContext = context;
    }

    public void getGasData(String address, PostZhuanZhiYaGasBean postZhuanZhiYaGasBean) {

        HttpUtils.postRequest(BaseUrl.HTTP_Get_zhiya_gas + address + "/redelegations", mContext, new Gson().toJson(postZhuanZhiYaGasBean), new JsonCallback<GasBean>() {
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


    public void getProducersBondedData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_producers + "bonded", mContext, new HashMap<>(), new JsonCallback<List<ProducersDetailsBean>>() {
            @Override
            public void onSuccess(Response<List<ProducersDetailsBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getBondedProducerListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getProducersunBondedData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_producers + "unbonded", mContext, new HashMap<>(), new JsonCallback<List<ProducersDetailsBean>>() {
            @Override
            public void onSuccess(Response<List<ProducersDetailsBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getUnBondedProducerListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getProducersBondedingData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_producers + "unbonding", mContext, new HashMap<>(), new JsonCallback<List<ProducersDetailsBean>>() {
            @Override
            public void onSuccess(Response<List<ProducersDetailsBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getUnBondingProducerListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

