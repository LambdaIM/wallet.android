package com.lambda.wallet.modules.proposal;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.wrapper.EmptyWrapper;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.proposal.ProposalListBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

// TODO: 2019/12/9 提案列表
public class ProposalActivity extends BaseAcitvity<ProposalView, ProposalPresenter> implements ProposalView {
    @BindView(R.id.list)
    XRecyclerView mList;


    List<ProposalListBean> mProposalListBeans = new ArrayList<>();//提案列表
    EmptyWrapper mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_proposal;
    }

    @Override
    public ProposalPresenter initPresenter() {
        return new ProposalPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.proposal));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
                presenter.getProposalData();
            }

            @Override
            public void onLoadMore() {
            }
        });

        mCommonAdapter = new EmptyWrapper(AdapterManger.getProposalListAdapter(this, mProposalListBeans));
        mCommonAdapter.setEmptyView(R.layout.empty);
        mList.setAdapter(mCommonAdapter);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        presenter.getProposalData();
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void getProposalListDataHttp(List<ProposalListBean> proposalListBeans) {
        hideProgress();
        mList.refreshComplete();
        mProposalListBeans.clear();
        for (int i = 0; i < proposalListBeans.size(); i++) {
            mProposalListBeans.add(proposalListBeans.get(i));
        }
        Collections.reverse(mProposalListBeans);//倒叙排列一下
        mCommonAdapter.notifyDataSetChanged();
    }
}
