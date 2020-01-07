package com.lambda.wallet.modules.mining;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.gyf.barlibrary.BarHide;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseFragment;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.AllZhiyaTokenBean;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.MyMiningListBean;
import com.lambda.wallet.bean.ProducerAwardBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;
import com.lambda.wallet.lambda.WalletManger;
import com.lambda.wallet.modules.mining.storemining.activity.StoreMiningActivity;
import com.lambda.wallet.modules.mining.storeorder.StoreOrderActivity;
import com.lambda.wallet.modules.transaction.award.activity.AwardActivity;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.AppBlackDefeatHeadView;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;
import com.liaoinstan.springview.widget.SpringView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 挖矿
 */
public class MiningFragment extends BaseFragment<MiningView, MiningPresenter> implements MiningView {


    @BindView(R.id.title)
    View mTitle;
    @BindView(R.id.producer_list)
    RecyclerView mProducerList;
    @BindView(R.id.award)
    TextView mAward;
    @BindView(R.id.go_award)
    TextView mGoAward;
    @BindView(R.id.zhiya)
    TextView mZhiya;
    @BindView(R.id.go_zhiya)
    TextView mGoZhiya;
    @BindView(R.id.store)
    TextView mStore;
    @BindView(R.id.go_store)
    TextView mGoStore;
    @BindView(R.id.spring)
    SpringView mSpring;


    private String bonded_tokens = null;
    List<ProducersDetailsBean> mProducersListBeanDetails = new ArrayList<>();//节点列表
    CommonAdapter mCommonAdapter;

    BigDecimal allAward, award1, award2, allZhiya = new BigDecimal(0);
    List<MyMiningListBean> mMyMiningListBeans = new ArrayList<>();//我的质押列表

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mining;
    }

    @Override
    public MiningPresenter initPresenter() {
        return new MiningPresenter(getActivity());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).statusBarDarkFont(true, 0.2f).titleBarMarginTop(mTitle).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.hideBar(BarHide.FLAG_SHOW_BAR).statusBarDarkFont(true, 0.2f).titleBarMarginTop(mTitle).init();
            mProducersListBeanDetails.clear();
            mMyMiningListBeans.clear();
            mCommonAdapter.notifyDataSetChanged();
            allAward = new BigDecimal(0);
            award1 = new BigDecimal(0);
            award2 = new BigDecimal(0);
            allZhiya = new BigDecimal(0);
            presenter.getAllZhiYaTokenData();
            presenter.getAwardData(MyApplication.getInstance().getUserBean().getAddress());
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        //系统刷新
        mSpring.setHeader(new AppBlackDefeatHeadView(getActivity()));
        mSpring.setGive(SpringView.Give.TOP);
        mSpring.setType(SpringView.Type.FOLLOW);
        mSpring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                allAward = new BigDecimal(0);
                award1 = new BigDecimal(0);
                award2 = new BigDecimal(0);
                allZhiya = new BigDecimal(0);
                mProducersListBeanDetails.clear();
                mMyMiningListBeans.clear();
                mCommonAdapter.notifyDataSetChanged();
                presenter.getAllZhiYaTokenData();
                presenter.getAwardData(MyApplication.getInstance().getUserBean().getAddress());
            }

            @Override
            public void onLoadmore() {
                mSpring.onFinishFreshAndLoad();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mProducerList.setLayoutManager(layoutManager);


    }

    @Override
    protected void initData() {
        showProgress();
        presenter.getAllZhiYaTokenData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyMiningListBeans.clear();
        allAward = new BigDecimal(0);
        award1 = new BigDecimal(0);
        award2 = new BigDecimal(0);
        allZhiya = new BigDecimal(0);
        presenter.getAwardData(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void initEvent() {

        mGoAward.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ActivityUtils.next(getActivity(), AwardActivity.class);
            }
        });
        mGoZhiya.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ActivityUtils.next(getActivity(), StoreMiningActivity.class);
            }
        });

        mGoStore.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ActivityUtils.next(getActivity(), StoreOrderActivity.class);
            }
        });
    }


    @Override
    public void getBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean) {
        for (int i = 0; i < producersDetailsBean.size(); i++) {
            mProducersListBeanDetails.add(producersDetailsBean.get(i));
        }
        presenter.getProducersunBondedData();
    }

    @Override
    public void getUnBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean) {
        for (int i = 0; i < producersDetailsBean.size(); i++) {
            mProducersListBeanDetails.add(producersDetailsBean.get(i));
        }
        presenter.getProducersBondedingData();
    }

    @Override
    public void getUnBondingProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean) {
        mSpring.onFinishFreshAndLoad();
        hideProgress();
        for (int i = 0; i < producersDetailsBean.size(); i++) {
            mProducersListBeanDetails.add(producersDetailsBean.get(i));
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void getAllZhiyaTokenDataHttp(AllZhiyaTokenBean allZhiyaTokenBean) {
        bonded_tokens = allZhiyaTokenBean.getBonded_tokens();
        mCommonAdapter = AdapterManger.getProducerListAdapter(getActivity(), mProducersListBeanDetails, bonded_tokens);
        mProducerList.setAdapter(mCommonAdapter);
        presenter.getProducersBondedData();
    }


    @Override
    public void getAwardDataHttp(List<AwardBean> awardBeanList) {
        for (int i = 0; i < awardBeanList.size(); i++) {
            awardBeanList.get(i).setAmount(BigDecimalUtil.toLambdaBigDecimal(awardBeanList.get(i).getAmount()).toString());
            award1 = BigDecimalUtil.add(award1, new BigDecimal(awardBeanList.get(i).getAmount()));
        }
        Utils.getSpUtils().put(Constants.SpInfo.AWARD1, award1.toString());
        Utils.getSpUtils().put(Constants.SpInfo.AWARD2, award2.toString());
        mAward.setText(TextUtils.isEmpty(BigDecimalUtil.add(award1, award2).toString())?"0":StringUtils.addComma(BigDecimalUtil.add(award1, award2).toString()));
        presenter.getProducerAwardData(WalletManger.getDevAddress(MyApplication.getInstance().getUserBean().getAddress()));
    }

    @Override
    public void getProducerAwardDataHttp(ProducerAwardBean producerAwardBean) {
     /*   for (int i = 0; i < producerAwardBean.getSelf_bond_rewards().size(); i++) {
            producerAwardBean.getSelf_bond_rewards().get(i).setAmount(BigDecimalUtil.toLambdaBigDecimal(producerAwardBean.getSelf_bond_rewards().get(i).getAmount()).toString());
            award2 = BigDecimalUtil.add(award2, new BigDecimal(producerAwardBean.getSelf_bond_rewards().get(i).getAmount()));
        }*/
        for (int i = 0; i < producerAwardBean.getVal_commission().size(); i++) {
            producerAwardBean.getVal_commission().get(i).setAmount(BigDecimalUtil.toLambdaBigDecimal(producerAwardBean.getVal_commission().get(i).getAmount()).toString());
            award2 = BigDecimalUtil.add(award2, new BigDecimal(producerAwardBean.getVal_commission().get(i).getAmount()));
        }
        Utils.getSpUtils().put(Constants.SpInfo.AWARD2, award2.toString());
        mAward.setText(TextUtils.isEmpty(BigDecimalUtil.add(award1, award2).toString())?"0":StringUtils.addComma(BigDecimalUtil.add(award1, award2).toString()));
        presenter.getProducerData(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void getFailDataHttp() {
        Utils.getSpUtils().put(Constants.SpInfo.AWARD2, award2.toString());
        mAward.setText(TextUtils.isEmpty(BigDecimalUtil.add(award1, award2).toString())?"0":StringUtils.addComma(BigDecimalUtil.add(award1, award2).toString()));
        presenter.getProducerData(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans) {
        if (zhiyaProducerBeans != null && zhiyaProducerBeans.size() != 0) {
            for (int i = 0; i < zhiyaProducerBeans.size(); i++) {
                MyMiningListBean miningListBean = new MyMiningListBean();
                miningListBean.setDelegator_address(zhiyaProducerBeans.get(i).getDelegator_address());
                miningListBean.setShares(zhiyaProducerBeans.get(i).getShares());
                miningListBean.setValidator_address(zhiyaProducerBeans.get(i).getValidator_address());
                mMyMiningListBeans.add(miningListBean);
                presenter.getProducerDetailsData(zhiyaProducerBeans.get(i).getValidator_address());
            }
        }else{
            allZhiya = new BigDecimal(0);
            mZhiya.setText(TextUtils.isEmpty(StringUtils.deletzero(allZhiya.toString()))?"0":StringUtils.addComma(allZhiya.toString()) );
        }
    }

    @Override
    public void getProducerDetailsDataHttp(ProducersDetailsBean producersDetailsBean) {
        allZhiya = new BigDecimal(0);
        for (int i = 0; i < mMyMiningListBeans.size(); i++) {
            if (mMyMiningListBeans.get(i).getValidator_address().equals(producersDetailsBean.getOperator_address())) {
                mMyMiningListBeans.get(i).setProducersDetailsBean(producersDetailsBean);
            }

            String tbb = null;
                /*shares 和tbb转换【个人质押量计算】
                个人质押后获取到质押分份额后，显示如果是显示质押的tbb数量需要进行转换计算

                shares/delegator_shares*tokens

                用个人的shares 除以节点总的shares 再乘以节点总的tokens*/
            if (mMyMiningListBeans.get(i).getProducersDetailsBean() != null) {
                tbb = StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(BigDecimalUtil.multiply(BigDecimalUtil.divide(new BigDecimal(mMyMiningListBeans.get(i).getProducersDetailsBean().getTokens()), new BigDecimal(mMyMiningListBeans.get(i).getProducersDetailsBean().getDelegator_shares())), new BigDecimal(mMyMiningListBeans.get(i).getShares())).toString()).toString());
                allZhiya = BigDecimalUtil.add(allZhiya, new BigDecimal(tbb));
            }
        }

        mZhiya.setText(TextUtils.isEmpty(StringUtils.deletzero(allZhiya.toString()))?"0":StringUtils.addComma(allZhiya.toString()) );
    }

}
