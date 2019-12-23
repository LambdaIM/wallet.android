package com.lambda.wallet.modules.mining.storemining.fragment.mymining;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;

import java.util.List;


public interface MyMiningView extends BaseView {


    void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans);

    void getProducerDetailsDataHttp(ProducersDetailsBean producersDetailsBean);

    void getForProducerAwardDataHttp(List<AwardBean> awardBeanList,String address);



}
