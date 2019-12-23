package com.lambda.wallet.modules.me.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.AppManager;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.modules.wallet.login.LoginActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.change_chain)
    TextView mChangeChain;
    @BindView(R.id.change_wallet)
    Button mChangeWallet;
    @BindView(R.id.version)
    TextView mVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.settings));
        mVersion.setText("V"+Utils.getAppVersionName(this));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.change_chain, R.id.change_wallet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.change_chain:
                ActivityUtils.next(SettingActivity.this, ChangeChainActivity.class, true);
                break;
            case R.id.change_wallet:
                String address =  Utils.getSpUtils().getString("url");
                String addurl = Utils.getSpUtils().getString("addUrl");
                AppManager.getAppManager().finishAllActivity();
                Utils.getSpUtils().clear();
                Utils.getSpUtils().put("url", address);
                Utils.getSpUtils().put("addUrl",addurl);
                ActivityUtils.next(SettingActivity.this, LoginActivity.class, true);
                break;
        }
    }

}
