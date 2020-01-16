package com.lambda.wallet.modules.me.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.AppManager;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.modules.main.MainActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.LocalManageUtil;
import com.lambda.wallet.util.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class LanguageActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.language_cn)
    TextView mLanguageCn;
    @BindView(R.id.language_en)
    TextView mLanguageEn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
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

    }

    @Override
    public void initEvent() {

    }



    @OnClick({R.id.language_cn, R.id.language_en})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.language_cn:
                if (SPUtil.getInstance(this).getSelectLanguage() == 1) {
                    toast(getString(R.string.choosed_cn));
                } else {
                    LocalManageUtil.saveSelectLanguage(this, 1);
                    AppManager.getAppManager().finishAllActivity();
                    ActivityUtils.next(this, MainActivity.class);
                    toast(getString(R.string.swich_language_success));
                }
                break;
            case R.id.language_en:
                if (SPUtil.getInstance(this).getSelectLanguage() == 2) {
                    toast(getString(R.string.choosed_en));
                } else {
                    LocalManageUtil.saveSelectLanguage(this, 2);
                    AppManager.getAppManager().finishAllActivity();
                    ActivityUtils.next(this, MainActivity.class);
                    toast(getString(R.string.swich_language_success));
                }
                break;
        }
    }
}
