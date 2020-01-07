package com.lambda.wallet.modules;

import android.content.Intent;
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
import com.lambda.wallet.util.CheckRoot;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.dialog.confimdialog.Callback;
import com.lambda.wallet.view.dialog.confimdialog.ConfirmDialog;

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
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        } else {
            if (CheckRoot.isDeviceRooted()) {
                ConfirmDialog confirmDialog = new ConfirmDialog(this, new Callback() {
                    @Override
                    public void sure() {
                        check();
                    }
                });
                confirmDialog.setCancelable(false);
                confirmDialog.setContent("检测到您的设备已root,用户信息存在丢失的风险，请慎重!");
                confirmDialog.show();
            } else {
                check();
            }
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

    private void check() {
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

}
