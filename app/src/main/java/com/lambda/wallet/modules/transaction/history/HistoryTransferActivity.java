package com.lambda.wallet.modules.transaction.history;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.wrapper.EmptyWrapper;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.TransferHistoryBean;
import com.lambda.wallet.util.DateUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class HistoryTransferActivity extends BaseAcitvity<HistoryTransferView, HistoryTransferPresenter> implements HistoryTransferView {


    @BindView(R.id.list)
    XRecyclerView mList;


    List<TransferHistoryBean> mTransferHistoryBeans = new ArrayList<>();//交易记录列表
    EmptyWrapper mCommonAdapter;

    int type = 1; //1:代表转账记录2：代表兑换记录3：代表所有的交易记录

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    public HistoryTransferPresenter initPresenter() {
        return new HistoryTransferPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.transfer_history));

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
                mTransferHistoryBeans.clear();
                mCommonAdapter.notifyDataSetChanged();
                presenter.getSendData(MyApplication.getInstance().getUserBean().getAddress());
            }

            @Override
            public void onLoadMore() {
            }
        });

        mCommonAdapter = new EmptyWrapper(AdapterManger.getTransferHistoryAdapter(this, mTransferHistoryBeans));
        mCommonAdapter.setEmptyView(R.layout.empty);
        mList.setAdapter(mCommonAdapter);
    }

    @Override
    protected void initData() {
        presenter.getSendData(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void getSendHistoryDataHttp(List<TransferHistoryBean> transferHistoryBeans) {
        for (int i = 0; i < transferHistoryBeans.size(); i++) {
            if (transferHistoryBeans.get(i).getTags().get(0).getValue().equals("send")) {//只筛选发送
                mTransferHistoryBeans.add(transferHistoryBeans.get(i));
            }
        }
        presenter.getReceiveData(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void getReceiveHistoryDataHttp(List<TransferHistoryBean> transferHistoryBeans) {
        mList.refreshComplete();
        for (int i = 0; i < transferHistoryBeans.size(); i++) {
            if (transferHistoryBeans.get(i).getTags().get(0).getValue().equals("send")) {//只筛选发送的
                mTransferHistoryBeans.add(transferHistoryBeans.get(i));
            }
        }
        time_sorting_List(mTransferHistoryBeans);
        mCommonAdapter.notifyDataSetChanged();
    }


    @SuppressLint("SimpleDateFormat")
    private List<TransferHistoryBean> time_sorting_List(List<TransferHistoryBean> time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1;
        Date d2;
        TransferHistoryBean temp_r = new TransferHistoryBean();
        //做一个冒泡排序，大的在数组的前列
        for (int i = 0; i < time.size() - 1; i++) {
            for (int j = i + 1; j < time.size(); j++) {
                ParsePosition pos1 = new ParsePosition(0);
                ParsePosition pos2 = new ParsePosition(0);
                String timei = "";

                timei = DateUtils.GTMToLocal(time.get(i).getTimestamp());
                String timej = "";
                timej = DateUtils.GTMToLocal(time.get(j).getTimestamp());

                d1 = sdf.parse(timei, pos1);
                d2 = sdf.parse(timej, pos2);
                if (d1.before(d2)) {//如果队前日期靠前，调换顺序
                    temp_r = time.get(i);
                    time.set(i, time.get(j));
                    time.set(j, temp_r);
                }
            }
        }
        return time;
    }


}
