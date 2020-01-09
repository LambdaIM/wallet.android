package com.lambda.wallet.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.modules.home.HomeFragment;
import com.lambda.wallet.modules.me.MeFragment;
import com.lambda.wallet.modules.mining.MiningFragment;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.CheckRoot;
import com.lambda.wallet.util.ToastUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.dialog.confimdialog.Callback;
import com.lambda.wallet.view.dialog.confimdialog.ConfirmDialog;

import butterknife.BindView;

public class MainActivity extends BaseAcitvity<NormalView, NormalPresenter> implements View.OnClickListener, NormalView {

    @BindView(R.id.content)
    FrameLayout mContent;
    @BindView(R.id.ll_home)
    LinearLayout mLlHome;
    @BindView(R.id.ll_mining)
    LinearLayout mLlMining;
    @BindView(R.id.ll_me)
    LinearLayout mLlMe;
    private HomeFragment homeFragment;
    private MiningFragment mMiningFragment;
    private MeFragment mMeFragment;
    private long exitTime = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        selectedFragment(0);
        tabSelected(mLlHome);
        if (CheckRoot.isDeviceRooted()) {
            ConfirmDialog confirmDialog = new ConfirmDialog(this, new Callback() {
                @Override
                public void sure() {
                }
            });
            confirmDialog.setCancelable(false);
            confirmDialog.setContent("检测到您的设备已root,用户信息存在丢失的风险，请慎重!");
            confirmDialog.show();
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    public void initEvent() {
        mLlHome.setOnClickListener(this);
        mLlMining.setOnClickListener(this);
        mLlMe.setOnClickListener(this);
    }

    private void selectedFragment(int position) {

        try {
            MyApplication.getInstance().setUserBean(MyApplication.getDbController().searchByWhere(Utils.getSpUtils().getString(Constants.SpInfo.FIRSTUSER)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:

                if (mMiningFragment == null) {
                    mMiningFragment = new MiningFragment();
                    transaction.add(R.id.content, mMiningFragment);
                } else {
                    transaction.show(mMiningFragment);
                }

                break;
            case 2:
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                    transaction.add(R.id.content, mMeFragment);
                } else {
                    transaction.show(mMeFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void tabSelected(LinearLayout linearLayout) {
        mLlHome.setSelected(false);
        mLlMining.setSelected(false);
        mLlMe.setSelected(false);
        linearLayout.setSelected(true);
    }

    private void hideFragment(FragmentTransaction transaction) {

        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (mMiningFragment != null) {
            transaction.hide(mMiningFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                selectedFragment(0);
                tabSelected(mLlHome);
                break;
            case R.id.ll_mining:
                selectedFragment(1);
                tabSelected(mLlMining);
                break;
            case R.id.ll_me:
                selectedFragment(2);
                tabSelected(mLlMe);
                break;
        }
    }


    /**
     * 二次退出确认 结束软件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO 按两次返回键退出应用程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 判断间隔时间 大于2秒就退出应用
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                String msg1 = getString(R.string.drop_two_to_home);
                ToastUtils.showLongToast(msg1);
                // 计算两次返回键按下的时间差
                exitTime = System.currentTimeMillis();
            } else {
                // 返回桌面操作
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
