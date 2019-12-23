package com.lambda.wallet.modules.transaction.history;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.TransferHistoryBean;

import java.util.List;


public interface HistoryTransferView extends BaseView {

    void getSendHistoryDataHttp(List<TransferHistoryBean> transferHistoryBeans);

    void getReceiveHistoryDataHttp(List<TransferHistoryBean> transferHistoryBeans);

}
