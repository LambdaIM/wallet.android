package com.lambda.wallet.modules.transaction.transactiondetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.wrapper.EmptyWrapper;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.TransactionDetailBean;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.DateUtils;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2020/2/24 交易记录详情
public class TransactionDetailActivity extends BaseAcitvity<TransactionDetailView, TransactionDetailPresenter> implements TransactionDetailView {


    @BindView(R.id.hash)
    TextView mHash;
    @BindView(R.id.copy_hash)
    LinearLayout mCopyHash;
    @BindView(R.id.block_number)
    TextView mBlockNumber;
    @BindView(R.id.fee)
    TextView mFee;
    @BindView(R.id.gas)
    TextView mGas;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.activity)
    RecyclerView mList;

    List<TransactionDetailBean.TxBean.ValueBeanX.MsgBean> mTransferHistoryBeans = new ArrayList<>();//交易记录列表
    EmptyWrapper mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_detail;
    }

    @Override
    public TransactionDetailPresenter initPresenter() {
        return new TransactionDetailPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mList.setLayoutManager(layoutManager);


        mCommonAdapter = new EmptyWrapper(AdapterManger.getTransferHistoryDetailsAdapter(this, mTransferHistoryBeans));
        mList.setAdapter(mCommonAdapter);
    }

    @Override
    protected void initData() {
        setCenterTitle(getString(R.string.order_details));
        showProgress();
        presenter.getDetailData(getIntent().getStringExtra("hash"));
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void getDetailDataHttp(TransactionDetailBean transactionDetailBean) {
        hideProgress();
        mHash.setText(transactionDetailBean.getTxhash());
        mBlockNumber.setText(transactionDetailBean.getHeight());
        if (transactionDetailBean!=null&&transactionDetailBean.getTx().getValue().getFee().getAmount()!=null) {
            mFee.setText(BigDecimalUtil.toLambdaBigDecimal(transactionDetailBean.getTx().getValue().getFee().getAmount().get(0).getAmount())+ StringUtils.lambdaToken(transactionDetailBean.getTx().getValue().getFee().getAmount().get(0).getDenom()));
        }else {
            mFee.setText("--");
        }
        mGas.setText(transactionDetailBean.getGas_used()+"/"+transactionDetailBean.getGas_wanted());
        mTime.setText( DateUtils.GTMToLocal(transactionDetailBean.getTimestamp()));

        for (int i = 0; i < transactionDetailBean.getTx().getValue().getMsg().size(); i++) {
            if (transactionDetailBean.getTx().getValue().getMsg().get(i).getType().equals("cosmos-sdk/MsgSend")) {//只筛选发送接收的
                mTransferHistoryBeans.add(transactionDetailBean.getTx().getValue().getMsg().get(i));
            }
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.copy_hash)
    public void onClick() {
        ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(mHash.getText().toString());
        ToastUtils.showShortToast(R.string.copy_success);
    }
}
