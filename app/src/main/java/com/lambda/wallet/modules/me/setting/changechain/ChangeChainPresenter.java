package com.lambda.wallet.modules.me.setting.changechain;

import android.content.Context;

import com.lambda.wallet.R;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.bean.ChainInfoBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;


public class ChangeChainPresenter extends BasePresent<ChangeChainView> {
    private Context mContext;

    public ChangeChainPresenter(Context context) {
        this.mContext = context;
    }

    public void getChainInfoData(String url) {
        HttpUtils.getRequets(url+"/node_info", mContext, new JsonCallback<ChainInfoBean>() {
            @Override
            public void onSuccess(Response<ChainInfoBean> response) {
                try {
                    if (response.body() != null) {
                        view.getChainInfoDataHttp(response.body(),url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Response<ChainInfoBean> response) {
                super.onError(response);
                view.getFailDataHttp(mContext.getString(R.string.chain_info_erro));
            }
        });
    }
}

