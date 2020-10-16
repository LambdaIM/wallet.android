package com.lambda.wallet.modules.mining.storemining.fragment.mymining;


import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.wrapper.EmptyWrapper;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.LazyLoadFragment;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.MyMiningListBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的质押
 */
public class MyMiningFragment extends LazyLoadFragment<MyMiningView, MyMiningPresenter> implements MyMiningView {

    @BindView(R.id.list)
    XRecyclerView mList;

    List<MyMiningListBean> mMyMiningListBeans = new ArrayList<>();//我的质押列表
    EmptyWrapper mCommonAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_my_mining;
    }

    @Override
    public MyMiningPresenter initPresenter() {
        return new MyMiningPresenter(getActivity());
    }

    @Override
    protected void onFragmentFirstVisible() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mList.setLayoutManager(layoutManager);

        mList.setPullRefreshEnabled(true);
        mList.setLoadingMoreEnabled(false);
        mList.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mList.setLoadingMoreProgressStyle(ProgressStyle.CubeTransition);
        mList.setArrowImageView(R.mipmap.blackarrow);

        mList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mMyMiningListBeans.clear();
                mCommonAdapter.notifyDataSetChanged();
                presenter.getProducerData(MyApplication.getInstance().getUserBean().getAddress());
            }

            @Override
            public void onLoadMore() {
            }
        });

        mCommonAdapter = new EmptyWrapper(AdapterManger.getMyZhiyaListAdapter(getActivity(), mMyMiningListBeans));
        mCommonAdapter.setEmptyView(R.layout.empty);
        mList.setAdapter(mCommonAdapter);

    }


    @Override
    public void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans) {
        if (zhiyaProducerBeans != null && zhiyaProducerBeans.size() != 0) {
            mMyMiningListBeans.clear();
            for (int i = 0; i < zhiyaProducerBeans.size(); i++) {
                MyMiningListBean miningListBean = new MyMiningListBean();
                miningListBean.setDelegator_address(zhiyaProducerBeans.get(i).getDelegator_address());
                miningListBean.setShares(zhiyaProducerBeans.get(i).getShares());
                miningListBean.setValidator_address(zhiyaProducerBeans.get(i).getValidator_address());
                mMyMiningListBeans.add(miningListBean);
                presenter.getProducerDetailsData(zhiyaProducerBeans.get(i).getValidator_address());
            }
        }
    }

    @Override
    public void getZhiyaDataHttpError() {
        mList.refreshComplete();
    }

    @Override
    public void getProducerDetailsDataHttp(ProducersDetailsBean producersDetailsBean) {
        for (int i = 0; i < mMyMiningListBeans.size(); i++) {
            if (mMyMiningListBeans.get(i).getValidator_address().equals(producersDetailsBean.getOperator_address())) {
                mMyMiningListBeans.get(i).setProducersDetailsBean(producersDetailsBean);
                if (mMyMiningListBeans.get(i).getAwardBean() == null) {
                    presenter.getForProducerAwardData(mMyMiningListBeans.get(i).getValidator_address());
                }
            }
        }
    }

    @Override
    public void getForProducerAwardDataHttp(List<AwardBean> awardBeanList, String address) {
        mList.refreshComplete();
        for (int i = 0; i < mMyMiningListBeans.size(); i++) {
            if (mMyMiningListBeans.get(i).getValidator_address().equals(address)) {
                mMyMiningListBeans.get(i).setAwardBean(awardBeanList.get(0));
            }
        }
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.getProducerData(MyApplication.getInstance().getUserBean().getAddress());
    }
}
