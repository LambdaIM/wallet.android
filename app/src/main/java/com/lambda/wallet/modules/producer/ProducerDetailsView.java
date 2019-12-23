package com.lambda.wallet.modules.producer;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.AllZhiyaTokenBean;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;

import java.util.List;


public interface ProducerDetailsView extends BaseView {

    void getProducerDataHttp(ProducersDetailsBean producersDetailsBean);

    void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans);

    void getAllZhiyaTokenDataHttp(AllZhiyaTokenBean allZhiyaTokenBean);

    void getForProducerAwardDataHttp(List<AwardBean> awardBeanList, String address);

}
