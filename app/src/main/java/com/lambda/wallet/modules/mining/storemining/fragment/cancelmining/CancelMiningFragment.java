package com.lambda.wallet.modules.mining.storemining.fragment.cancelmining;


import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.wrapper.EmptyWrapper;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.LazyLoadFragment;
import com.lambda.wallet.bean.CancelZhiyaBean;
import com.lambda.wallet.bean.MyCancelZhiYaBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 取消质押列表
 */
public class CancelMiningFragment extends LazyLoadFragment<CancelMiningView, CancelMiningPresenter> implements CancelMiningView {

    @BindView(R.id.list)
    XRecyclerView mList;

    List<MyCancelZhiYaBean> mMyCancelZhiYaBeans = new ArrayList<>();//我的取消质押列表
    EmptyWrapper mCommonAdapter;


    @Override
    protected int setContentView() {
        return R.layout.fragment_cancel_mining;
    }

    @Override
    public CancelMiningPresenter initPresenter() {
        return new CancelMiningPresenter(getActivity());
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
                mMyCancelZhiYaBeans.clear();
                mCommonAdapter.notifyDataSetChanged();
                presenter.getCancelData(MyApplication.getInstance().getUserBean().getAddress());
            }

            @Override
            public void onLoadMore() {
            }
        });

        mCommonAdapter = new EmptyWrapper(AdapterManger.getMyCancelZhiyaListAdapter(getActivity(), mMyCancelZhiYaBeans));
        mCommonAdapter.setEmptyView(R.layout.empty);
        mList.setAdapter(mCommonAdapter);


    }


    @Override
    public void getCancelZhiyaDataHttp(List<CancelZhiyaBean> cancelZhiyaBeanList) {
        mList.refreshComplete();
        mMyCancelZhiYaBeans.clear();
        for (int i = 0; i < cancelZhiyaBeanList.size(); i++) {
            for (int j = 0; j < cancelZhiyaBeanList.get(i).getEntries().size(); j++) {
                MyCancelZhiYaBean myCancelZhiYaBean = new MyCancelZhiYaBean();
                myCancelZhiYaBean.setDelegator_address(cancelZhiyaBeanList.get(i).getDelegator_address());
                myCancelZhiYaBean.setEntriesBean(cancelZhiyaBeanList.get(i).getEntries().get(j));
                mMyCancelZhiYaBeans.add(myCancelZhiYaBean);
            }
        }
        Collections.reverse(mMyCancelZhiYaBeans);//倒叙排列一下,按照时间顺序排列
        mCommonAdapter.notifyDataSetChanged();
    }

    @Override
    public void getCancelZhiyaDataHttp() {
        mList.refreshComplete();
        mMyCancelZhiYaBeans.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getCancelData(MyApplication.getInstance().getUserBean().getAddress());
    }
}
