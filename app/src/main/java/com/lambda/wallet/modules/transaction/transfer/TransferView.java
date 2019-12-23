package com.lambda.wallet.modules.transaction.transfer;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;


public interface TransferView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

}
