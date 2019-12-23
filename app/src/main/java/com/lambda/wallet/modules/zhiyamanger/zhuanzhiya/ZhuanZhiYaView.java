package com.lambda.wallet.modules.zhiyamanger.zhuanzhiya;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.ProducersDetailsBean;

import java.util.List;


public interface ZhuanZhiYaView extends BaseView {

    void getGasDataHttp(GasBean gasBean);

    void getBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean);
    void getUnBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean);
    void getUnBondingProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean);

}
