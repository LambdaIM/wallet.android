package com.lambda.wallet.modules.zhiyamanger.zhiya;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;


public interface ZhiYaView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

}
