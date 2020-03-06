package com.lambda.wallet.modules.me.setting.changechain;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.ChainInfoBean;


public interface ChangeChainView extends BaseView {

    void getChainInfoDataHttp(ChainInfoBean chainInfoBean,String url);
    void getFailDataHttp(String url);



}
