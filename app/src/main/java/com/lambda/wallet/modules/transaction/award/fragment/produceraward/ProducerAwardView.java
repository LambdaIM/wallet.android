package com.lambda.wallet.modules.transaction.award.fragment.produceraward;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;


public interface ProducerAwardView extends BaseView {

    void getGasDataHttp(GasBean gasBean);


}
