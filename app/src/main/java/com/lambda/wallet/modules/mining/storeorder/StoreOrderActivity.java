package com.lambda.wallet.modules.mining.storeorder;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.wrapper.EmptyWrapper;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.MyStoreOrderBean;
import com.lambda.wallet.modules.mining.storemarket.StoreMarketActivity;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class StoreOrderActivity extends BaseAcitvity<StoreOrderView, StoreOrderPresenter> implements StoreOrderView {
    @BindView(R.id.list)
    XRecyclerView mList;


    List<MyStoreOrderBean> mMyStoreOrderBeans = new ArrayList<>();//订单记录列表
    EmptyWrapper mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_order;
    }

    @Override
    public StoreOrderPresenter initPresenter() {
        return new StoreOrderPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.transfer_history));
        setRightTitle("购买空间",true);

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
                mMyStoreOrderBeans.clear();
                mCommonAdapter.notifyDataSetChanged();
                presenter.getStoreOrderData(MyApplication.getInstance().getUserBean().getAddress());
            }

            @Override
            public void onLoadMore() {
            }
        });

        mCommonAdapter = new EmptyWrapper(AdapterManger.getStoreOrderListAdapter(this, mMyStoreOrderBeans));
        mCommonAdapter.setEmptyView(R.layout.empty);
        mList.setAdapter(mCommonAdapter);
    }

    @Override
    protected void initData() {
        presenter.getStoreOrderData(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void initEvent() {
        getId(R.id.tv_right_text).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ActivityUtils.next(StoreOrderActivity.this, StoreMarketActivity.class);
            }
        });
    }

    @Override
    public void getMyStoreOrderDataHttp(List<MyStoreOrderBean> myStoreOrderBeanList) {
        mList.refreshComplete();
        for (int i = 0; i < myStoreOrderBeanList.size(); i++) {
            mMyStoreOrderBeans.add(myStoreOrderBeanList.get(i));
        }
        Collections.reverse(mMyStoreOrderBeans);//倒叙排列一下
        mCommonAdapter.notifyDataSetChanged();
    }
}
