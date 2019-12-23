package com.lambda.wallet.modules.mining.storemining.fragment.cancelmining;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.CancelZhiyaBean;

import java.util.List;


public interface CancelMiningView extends BaseView {


    void getCancelZhiyaDataHttp(List<CancelZhiyaBean> cancelZhiyaBeanList);

}
