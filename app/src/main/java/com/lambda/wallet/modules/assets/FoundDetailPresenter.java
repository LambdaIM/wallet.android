package com.lambda.wallet.modules.assets;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.FundInfoMineBean;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.HomeAssetBean;
import com.lambda.wallet.bean.PreMiningDetail;
import com.lambda.wallet.lambda.bean.gas.PostPreMiningGasbean;
import com.lambda.wallet.modules.home.HomeView;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

public class  FoundDetailPresenter extends BasePresent<FoundDetailView> {

    private Context mContext;

    public FoundDetailPresenter(Context context) {
        this.mContext = context;
    }


    public void getAssetDetailsData(String address) {

        HttpUtils.getRequets(BaseUrl.HTTP_Get_Asset_Detail + "/" + address, mContext, new HashMap<>(), new JsonCallback<HomeAssetBean>() {
            @Override
            public void onSuccess(Response<HomeAssetBean> response) {
                try {
                    if (response.body() != null) {
                        view.getAssetDetailsDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /***
     * 获取挖矿信息
     * @param address
     */
    public void getFundInfo(String address) {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_Mining_Detail + "/" + address, mContext, new HashMap<>(), new JsonCallback<PreMiningDetail>() {
            @Override
            public void onSuccess(Response<PreMiningDetail> response) {
                try {
                    if (response.body() != null) {
                        view.getFundDetailsDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    /***
     * 我的挖矿信息
     * @param address
     */
    public void getFundInfoMIME(String address) {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_Fund_Info_Mine + "/" +address +"/funder/" + MyApplication.getInstance().getUserBean().getAddress(), mContext, new HashMap<>(), new JsonCallback<FundInfoMineBean>() {
            @Override
            public void onSuccess(Response<FundInfoMineBean> response) {
                try {
                    if (response.body() != null) {
                        view.getFundInfoMineDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<FundInfoMineBean> response) {
                super.onError(response);
                view.getFundInfoMineDataHttpError();



            }
        });

    }

    /***
     * 获取预挖矿的GAS
     */
    public void getPreMiningGas(PostPreMiningGasbean postPreMiningGasbean) {
        Log.d("getPreMiningGas",new Gson().toJson(postPreMiningGasbean));

        HttpUtils.postRequest(BaseUrl.HTTP_Get_Asset_INVEST, mContext, new Gson().toJson(postPreMiningGasbean), new JsonCallback<GasBean>() {
            @Override
            public void onSuccess(Response<GasBean> response) {
                try {
                    if (response.body() != null) {
                        view.getMineGasDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

}
