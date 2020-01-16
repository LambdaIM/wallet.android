package com.lambda.wallet.modules.wallet.creatwallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.UserBean;
import com.lambda.wallet.lambda.PasswordToKeyUtils;
import com.lambda.wallet.lambda.WalletManger;
import com.lambda.wallet.modules.wallet.mnemonic.MnemonicActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.RegexUtil;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class CreatWalletActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.wallet_name)
    ClearEditText mWalletName;
    @BindView(R.id.password)
    ClearEditText mPassword;
    @BindView(R.id.password_two)
    ClearEditText mPasswordTwo;
    @BindView(R.id.go)
    Button mGo;

    private byte[] mEntropy;//种子
    private ArrayList<String> mWords = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_creat_wallet;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//当前页面防截屏录屏
        setCenterTitle(getString(R.string.creat_wallet));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        mGo.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (TextUtils.isEmpty(mWalletName.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_wallet_name));
                    return;
                }
                if (TextUtils.isEmpty(mPassword.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_password));
                    return;
                }
                if (TextUtils.isEmpty(mPasswordTwo.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_two_password));
                    return;
                }
                if (!RegexUtil.isPwd(mPasswordTwo.getText().toString().trim())) {
                    toast(getString(R.string.toast_rex_password));
                    return;
                }
                if (!mPasswordTwo.getText().toString().trim().equals(mPassword.getText().toString().trim())) {
                    toast(getString(R.string.toast_error_two_password));
                    return;
                }
                showProgress();
                List<UserBean> userBeans = null;
                try {
                    userBeans = MyApplication.getDbController().searchAll();
                } catch (Exception e) {
                    e.printStackTrace();
                    userBeans = new ArrayList<>();
                }
                for (int i = 0; i < userBeans.size(); i++) {
                    if (userBeans.get(i).getName().equals(mWalletName.getText().toString())) {
                        toast(getString(R.string.toast_wallet_exit));
                        return;
                    }
                }

                onGenWords();

            }
        });
    }

    private void onGenWords() {

        mEntropy = WalletManger.getEntropy();
        mWords = new ArrayList<String>(WalletManger.getRandomMnemonic(mEntropy));

        try {
            HashMap hashMap = new HashMap();
            hashMap = PasswordToKeyUtils.encryptPwdForPrivate(mPassword.getText().toString().trim(),WalletManger.getPrivateKeyFromMnemonicCode(mWords));
            UserBean mUserBean = new UserBean();
            mUserBean.setName(mWalletName.getText().toString().trim());
            mUserBean.setAddress(WalletManger.getDpAddressFromEntropy(mEntropy));
            mUserBean.setPublicKey(WalletManger.getUserDpPubKey(WalletManger.getKeyWithPathfromEntropy(Utils.ByteArrayToHexString(mEntropy)).getPublicKeyAsHex()));
            mUserBean.setPrivateKey(hashMap.get("result").toString());
            mUserBean.setSalt(hashMap.get("userSalt").toString());
            String str = null;
            for (int i = 0; i < mWords.size(); i++) {
                str += " " + mWords.get(i);
            }
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("words", mWords);
            bundle.putParcelable("userBean", mUserBean);
            hideProgress();
            ActivityUtils.next(this, MnemonicActivity.class, bundle);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

}
