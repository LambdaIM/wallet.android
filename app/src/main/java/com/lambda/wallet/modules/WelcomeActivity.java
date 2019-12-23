package com.lambda.wallet.modules;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.modules.main.MainActivity;
import com.lambda.wallet.modules.wallet.login.LoginActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.Utils;

public class WelcomeActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {
    private static final int TIME = 500;
    private static final int GO_HOME = 200;
    private static final int GO_LOGIN = 300;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GO_HOME:
                    goHome();
                    break;
                case GO_LOGIN:
                    goLogin();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        try {
            if (TextUtils.isEmpty(Utils.getSpUtils().getString("url"))) {
                Utils.getSpUtils().put("url", "http://39.107.247.86:13659");//默认设置主网
            }
            if (!TextUtils.isEmpty(Utils.getSpUtils().getString(Constants.SpInfo.FIRSTUSER))) {
                mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
                return;
            } else {
                mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, TIME);
        }


    }

    @Override
    public void initEvent() {

    }

    private void goHome() {
        ActivityUtils.next(WelcomeActivity.this, MainActivity.class, true);
    }


    private void goLogin() {
        ActivityUtils.next(WelcomeActivity.this, LoginActivity.class, true);
    }

}
