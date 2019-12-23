package com.lambda.wallet.modules.transaction.award.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.adapter.Find_tab_Adapter;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.modules.transaction.award.fragment.miningaward.MiningAwardFragment;
import com.lambda.wallet.modules.transaction.award.fragment.produceraward.ProducerAwardFragment;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

// TODO: 2019/11/29 提取奖励
public class AwardActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.viewpager)
    ViewPager mViewpager;

    List<Fragment> mFragments;
    List<String> mTitles = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_award;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle("提取奖励");
        mFragments = new ArrayList<>();
        mTitles.add(getString(R.string.mining_award));
        mTitles.add(getString(R.string.producer_award));

        MiningAwardFragment miningAwardFragment = new MiningAwardFragment();
        mFragments.add(miningAwardFragment);
        ProducerAwardFragment producerAwardFragment = new ProducerAwardFragment();
        mFragments.add(producerAwardFragment);


        Find_tab_Adapter adapter = new Find_tab_Adapter(this.getSupportFragmentManager(), mFragments, mTitles);
        mViewpager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewpager);

        mTabs.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = mTabs.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);
                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(mTabs);
                    int dp10 = DensityUtil.dip2px(mTabs.getContext(), 10);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }
}
