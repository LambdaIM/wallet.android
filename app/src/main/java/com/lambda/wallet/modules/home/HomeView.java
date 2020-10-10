package com.lambda.wallet.modules.home;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.HomeAssetBean;
import com.lambda.wallet.bean.ProducerAwardBean;

import java.util.List;


public interface HomeView extends BaseView {

    void getAddressDetailsDataHttp(HomeAddressDetailsBean homeAddressDetailsBean);

    void getServerAssetsFundList(List<HomeAssetBean> homeAssetBean);

}
