package com.lambda.wallet.modules.transaction.transactiondetail;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.TransactionDetailBean;


public interface TransactionDetailView extends BaseView {

    void getDetailDataHttp(TransactionDetailBean transactionDetailBean);

}
