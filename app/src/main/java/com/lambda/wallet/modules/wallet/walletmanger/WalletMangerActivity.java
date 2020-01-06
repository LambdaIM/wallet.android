package com.lambda.wallet.modules.wallet.walletmanger;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.lambda.PasswordToKeyUtils;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.JsonUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.view.dialog.importprivatekeydialog.ImportPrivateKeyDialog;
import com.lambda.wallet.view.dialog.nogaspassword.NoGasPasswordCallback;
import com.lambda.wallet.view.dialog.nogaspassword.NoGasPasswordDialog;

import java.io.UnsupportedEncodingException;

import butterknife.BindView;
import butterknife.OnClick;

public class WalletMangerActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.wallet_name)
    TextView mWalletName;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.back_up)
    TextView mBackUp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet_manger;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//当前页面防截屏录屏
        setCenterTitle(getString(R.string.wallet_manger));
    }

    @Override
    protected void initData() {
        mWalletName.setText(MyApplication.getInstance().getUserBean().getName());
        mAddress.setText(StringUtils.lambdaAddress(MyApplication.getInstance().getUserBean().getAddress()));
    }

    @Override
    public void initEvent() {

    }


    @OnClick(R.id.back_up)
    public void onClick() {
        NoGasPasswordDialog mPasswordDialog = new NoGasPasswordDialog(WalletMangerActivity.this, new NoGasPasswordCallback() {
            @Override
            public void sure(final String password) {
                // TODO: 2019/12/11 忽略密码校验
                try {
                    if (!TextUtils.isEmpty(PasswordToKeyUtils.dncryptPwdForPrivate(password, MyApplication.getInstance().getUserBean().getPrivateKey(), MyApplication.getInstance().getUserBean().getSalt()))) {
                        ImportPrivateKeyDialog mImportPrivateKeyDialog = new ImportPrivateKeyDialog(WalletMangerActivity.this);
                        mImportPrivateKeyDialog.setCancelable(false);
                        String info = new GsonBuilder().enableComplexMapKeySerialization().disableHtmlEscaping().create().toJson(MyApplication.getInstance().getUserBean());
                        mImportPrivateKeyDialog.setContent(JsonUtil.stringToJSON(info));
                        mImportPrivateKeyDialog.show();
                    } else {
                        toast(getString(R.string.toast_rerror_password));
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    toast(getString(R.string.toast_rerror_password));
                }

            }
        });
        mPasswordDialog.setCancelable(false);
        mPasswordDialog.show();
    }
}
