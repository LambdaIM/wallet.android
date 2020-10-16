package com.lambda.wallet.modules.home;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.HomeAssetBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class HomePresenter extends BasePresent<HomeView> {
    private Context mContext;

    public HomePresenter(Context context) {
        this.mContext = context;
    }

    public void getAddressDetailsData(String address) {

        HttpUtils.getRequets(BaseUrl.HTTP_Get_account + "/" + address, mContext, new HashMap<>(), new JsonCallback<HomeAddressDetailsBean>() {
            @Override
            public void onSuccess(Response<HomeAddressDetailsBean> response) {
                try {
                    if (response.body() != null) {
                        view.getAddressDetailsDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<HomeAddressDetailsBean> response) {
                super.onError(response);
            }
        });

    }

    /***
     * 获取资产列表
     */
    public void getServerAssetsFundList(){
        HttpUtils.getRequets(BaseUrl.HTTP_Get_Asset_Fund_List , mContext, new HashMap<>(), new JsonCallback<List <HomeAssetBean>>() {
            @Override
            public void onSuccess(Response <List <HomeAssetBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getServerAssetsFundList(response.body());
                    }else {
                        view.getServerAssetsError();
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            @Override
            public void onError(Response<List<HomeAssetBean>> response) {
                super.onError(response);
                view.getServerAssetsError();
            }
        });
    };


}

