package com.lambda.wallet.modules.mining.storeorder;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.MyStoreOrderBean;

import java.util.List;


public interface StoreOrderView extends BaseView {

    void getMyStoreOrderDataHttp(List<MyStoreOrderBean> myStoreOrderBeanList);

}
