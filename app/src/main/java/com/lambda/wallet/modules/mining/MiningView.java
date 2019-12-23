package com.lambda.wallet.modules.mining;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.AllZhiyaTokenBean;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.ProducerAwardBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;

import java.util.List;


public interface MiningView extends BaseView {

    void getBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean);
    void getUnBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean);
    void getUnBondingProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean);

    void getAllZhiyaTokenDataHttp(AllZhiyaTokenBean allZhiyaTokenBean);


    void getAwardDataHttp(List<AwardBean> awardBeanList);

    void getProducerAwardDataHttp(ProducerAwardBean producerAwardBean);

    void getFailDataHttp();

    void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans);

    void getProducerDetailsDataHttp(ProducersDetailsBean producersDetailsBean);


}
