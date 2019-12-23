package com.lambda.wallet.modules.transaction.award.fragment.miningaward;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;

import java.util.List;


public interface MiningAwardView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

    void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans);

}
