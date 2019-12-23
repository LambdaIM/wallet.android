package com.lambda.wallet.modules.mining.orderdetails;

import android.os.Bundle;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.MyStoreOrderBean;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.DateUtils;
import com.lambda.wallet.util.StringUtils;

import butterknife.BindView;

// TODO: 2019/12/9 订单详情
public class OrderDetailsActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.market_address)
    TextView mMarketAddress;
    @BindView(R.id.order_type)
    TextView mOrderType;
    @BindView(R.id.order_status)
    TextView mOrderStatus;
    @BindView(R.id.kuanggong_address)
    TextView mKuanggongAddress;
    @BindView(R.id.user_address)
    TextView mUserAddress;
    @BindView(R.id.order_id)
    TextView mOrderId;
    @BindView(R.id.space)
    TextView mSpace;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.start_time)
    TextView mStartTime;
    @BindView(R.id.end_time)
    TextView mEndTime;
    @BindView(R.id.shebei)
    TextView mShebei;
    @BindView(R.id.peifu_amount)
    TextView mPeifuAmount;
    @BindView(R.id.amount)
    TextView mAmount;

    MyStoreOrderBean mMyStoreOrderBean = new MyStoreOrderBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_details;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.order_details));
    }

    @Override
    protected void initData() {
        mMyStoreOrderBean = getIntent().getParcelableExtra("order");
        mMarketAddress.setText(StringUtils.lambdaAddress(mMyStoreOrderBean.getMatchOrder().getMarketAddress()));
        if (mMyStoreOrderBean.getMatchOrder().getAskAddress().equals(MyApplication.getInstance().getUserBean().getAddress())) {//卖方地址和自己地址一致则为卖单
            mOrderType.setText(R.string.sell_order);
        } else {
            mOrderType.setText(R.string.buy_order);
        }

        if (mMyStoreOrderBean.getMatchOrder().getStatus().equals("0")) {
            mOrderStatus.setText(R.string.active);
        } else {
            mOrderStatus.setText(R.string.done);
        }

        mKuanggongAddress.setText(StringUtils.lambdaAddress(mMyStoreOrderBean.getMatchOrder().getAskAddress()));
        mUserAddress.setText(StringUtils.lambdaAddress(mMyStoreOrderBean.getMatchOrder().getBuyAddress()));
        mOrderId.setText(mMyStoreOrderBean.getMatchOrder().getOrderId());
        mSpace.setText(mMyStoreOrderBean.getMatchOrder().getSize() + "GB");
        mPrice.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(mMyStoreOrderBean.getMatchOrder().getPrice()).toString()) + "LAMB/GB/Month");
        mStartTime.setText(DateUtils.GTMToLocal(mMyStoreOrderBean.getMatchOrder().getCreateTime()));
        mEndTime.setText(DateUtils.GTMToLocal(mMyStoreOrderBean.getMatchOrder().getEndTime()));
        mShebei.setText(mMyStoreOrderBean.getMatchOrder().getMachineName());
        mAmount.setText( StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(mMyStoreOrderBean.getMatchOrder().getUserPay().getAmount()).toString())+"LAMB");
        mPeifuAmount.setText("0");
    }

    @Override
    public void initEvent() {

    }


}
