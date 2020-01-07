package com.lambda.wallet.modules.producer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.AllZhiyaTokenBean;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;
import com.lambda.wallet.modules.zhiyamanger.cancelzhiya.CancelZhiYaActivity;
import com.lambda.wallet.modules.zhiyamanger.zhiya.ZhiYaActivity;
import com.lambda.wallet.modules.zhiyamanger.zhuanzhiya.ZhuanZhiYaActivity;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2019/11/29 验证节点详情
public class ProducerDetailsActivity extends BaseAcitvity<ProducerDetailsView, ProducerDetailsPresenter> implements ProducerDetailsView {

    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.my_zhiya)
    TextView mMyZhiya;
    @BindView(R.id.my_award)
    TextView mMyAward;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.details)
    TextView mDetails;
    @BindView(R.id.rate)
    TextView mRate;
    @BindView(R.id.max_rate)
    TextView mMaxRate;
    @BindView(R.id.max_change_rate)
    TextView mMaxChangeRate;
    @BindView(R.id.toupiao)
    TextView mToupiao;
    @BindView(R.id.zhiya)
    TextView mZhiya;
    @BindView(R.id.zhuanzhiya)
    TextView mZhuanzhiya;
    @BindView(R.id.cancel_zhiya)
    TextView mCancelZhiya;

    ProducersDetailsBean mProducersDetailsBean = new ProducersDetailsBean();
    String zhiya = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_producer_details;
    }

    @Override
    public ProducerDetailsPresenter initPresenter() {
        return new ProducerDetailsPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.producer_details));
        mCancelZhiya.setVisibility(View.GONE);
        mZhuanzhiya.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }


    @Override
    public void getProducerDataHttp(ProducersDetailsBean producersDetailsBean) {

        mProducersDetailsBean = producersDetailsBean;
        mName.setText(producersDetailsBean.getDescription().getMoniker());
        mAddress.setText(StringUtils.lambdaAddress(producersDetailsBean.getOperator_address()));
        mDetails.setText(producersDetailsBean.getDescription().getDetails());
        mRate.setText(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(producersDetailsBean.getCommission().getRate()), new BigDecimal(100)).toString()) + "%");
        mMaxRate.setText(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(producersDetailsBean.getCommission().getMax_rate()), new BigDecimal(100)).toString()) + "%");
        mMaxChangeRate.setText(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(producersDetailsBean.getCommission().getMax_change_rate()), new BigDecimal(100)).toString()) + "%");
        mMyZhiya.setText("0TBB");
        mMyAward.setText("0LAMB");
        presenter.getProducerData(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans) {
        if (zhiyaProducerBeans.size()==0){
            mMyZhiya.setText(StringUtils.addComma("0") + "TBB");
            zhiya = null;
        }else {
            String tbb = null;
                /*shares 和tbb转换【个人质押量计算】
                个人质押后获取到质押分份额后，显示如果是显示质押的tbb数量需要进行转换计算

                shares/delegator_shares*tokens

                用个人的shares 除以节点总的shares 再乘以节点总的tokens*/
            try {
                for (int i = 0; i < zhiyaProducerBeans.size(); i++) {
                    if (zhiyaProducerBeans.get(i).getValidator_address().equals(getIntent().getStringExtra("address"))) {
                        tbb = StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(BigDecimalUtil.multiply(BigDecimalUtil.divide(new BigDecimal(mProducersDetailsBean.getTokens()), new BigDecimal(mProducersDetailsBean.getDelegator_shares())), new BigDecimal(zhiyaProducerBeans.get(i).getShares())).toString()).toString());
                        mMyZhiya.setText(StringUtils.addComma(tbb) + "TBB");
                        zhiya = tbb;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                mMyZhiya.setText(StringUtils.addComma(tbb) + "TBB");
                zhiya = null;
            }
        }
        presenter.getAllZhiYaTokenData();

    }

    @Override
    public void getAllZhiyaTokenDataHttp(AllZhiyaTokenBean allZhiyaTokenBean) {
        String bonded_tokens = null;
        bonded_tokens = allZhiyaTokenBean.getBonded_tokens();//全网所有的质押数量
        if (TextUtils.isEmpty(bonded_tokens)) {
            mToupiao.setText("0");
        } else {
            mToupiao.setText(StringUtils.deletzero(BigDecimalUtil.multiply(BigDecimalUtil.divide(new BigDecimal(mProducersDetailsBean.getTokens()), new BigDecimal(bonded_tokens)), new BigDecimal(100),2).toString()) + "%");
        }
        presenter.getForProducerAwardData(getIntent().getStringExtra("address"));
    }

    @Override
    public void getForProducerAwardDataHttp(List<AwardBean> awardBeanList, String address) {
        hideProgress();
        try {
            mMyAward.setText(StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(awardBeanList.get(0).getAmount()).toString() )+ StringUtils.lambdaToken(awardBeanList.get(0).getDenom()));
        } catch (Exception e) {
            e.printStackTrace();
            mMyAward.setText("0"+"LAMB");
        }

        if (TextUtils.isEmpty(zhiya)) {
            mCancelZhiya.setVisibility(View.GONE);
            mZhuanzhiya.setVisibility(View.GONE);
        } else {
            mCancelZhiya.setVisibility(View.VISIBLE);
            mZhuanzhiya.setVisibility(View.VISIBLE);
        }

    }


    @OnClick({R.id.zhiya, R.id.zhuanzhiya, R.id.cancel_zhiya})
    public void onClick(View view) {
        Bundle bundle= new Bundle();
        bundle.putParcelable("details",mProducersDetailsBean);
        switch (view.getId()) {
            case R.id.zhiya:
                ActivityUtils.next(this, ZhiYaActivity.class,bundle);
                break;
            case R.id.zhuanzhiya:
                ActivityUtils.next(this, ZhuanZhiYaActivity.class,bundle);
                break;
            case R.id.cancel_zhiya:
                bundle.putString("zhiya",zhiya);
                ActivityUtils.next(this, CancelZhiYaActivity.class,bundle);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        presenter.getProducersData(getIntent().getStringExtra("address"));
    }
}
