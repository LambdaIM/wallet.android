package com.lambda.wallet.modules.mining.storemarket;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.MarketSellListBean;
import com.lambda.wallet.modules.mining.storemarket.buystore.BuyStoreActivity;
import com.lambda.wallet.view.AppBlackDefeatHeadView;
import com.lambda.wallet.view.ClearEditText;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2019/12/2 存储市场
public class StoreMarketActivity extends BaseAcitvity<StoreMarketView, StoreMarketPresenter> implements StoreMarketView {


    @BindView(R.id.space)
    ClearEditText mSpace;
    @BindView(R.id.time)
    ClearEditText mTime;
    @BindView(R.id.sell_list)
    RecyclerView mSellList;
    @BindView(R.id.tv_right_text)
    TextView mTvRightText;
    @BindView(R.id.go)
    Button mGo;
    @BindView(R.id.spring)
    SpringView mSpring;


    List<MarketSellListBean> mMarketSellListBeans = new ArrayList<>();//出售列表
    CommonAdapter mCommonAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_store_market;
    }

    @Override
    public StoreMarketPresenter initPresenter() {
        return new StoreMarketPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.store_market));
        //系统刷新
        mSpring.setHeader(new AppBlackDefeatHeadView(this));
        mSpring.setGive(SpringView.Give.TOP);
        mSpring.setType(SpringView.Type.FOLLOW);
        mSpring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mMarketSellListBeans.clear();
                mCommonAdapter.notifyDataSetChanged();
                presenter.getMarketSellListData();
            }

            @Override
            public void onLoadmore() {
                mSpring.onFinishFreshAndLoad();
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mSellList.setLayoutManager(layoutManager);


        mCommonAdapter = AdapterManger.getMarketSellListAdapter(this, mMarketSellListBeans);
        mSellList.setAdapter(mCommonAdapter);

    }

    @Override
    protected void initData() {
        presenter.getMarketSellListData();
    }

    @Override
    public void initEvent() {

    }


    @OnClick({ R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.go:
                if (TextUtils.isEmpty(mSpace.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_space));
                    return;
                }
                if (TextUtils.isEmpty(mTime.getText().toString().trim())) {
                    toast(getString(R.string.toast_mo_time));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putInt("type",0);//0一键购买1自选交易
                bundle.putString("space",mSpace.getText().toString());
                bundle.putString("time",mTime.getText().toString());
                ActivityUtils.next(this, BuyStoreActivity.class,bundle);
                break;
        }
    }

    @Override
    public void getMarketSellListDataHttp(List<MarketSellListBean> marketSellListBeans) {
        mSpring.onFinishFreshAndLoad();
        for (int i = 0; i < marketSellListBeans.size(); i++) {
            mMarketSellListBeans.add(marketSellListBeans.get(i));
        }
        mCommonAdapter.notifyDataSetChanged();
    }
}
