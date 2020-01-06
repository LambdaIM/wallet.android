package com.lambda.wallet.modules.wallet.importwallet.fragment;


import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Button;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.base.LazyLoadFragment;
import com.lambda.wallet.bean.UserBean;
import com.lambda.wallet.lambda.PasswordToKeyUtils;
import com.lambda.wallet.lambda.WalletManger;
import com.lambda.wallet.modules.main.MainActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.RegexUtil;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 助记词导入
 */
public class MnemonicImportFragment extends LazyLoadFragment<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.mnemonic_info)
    ClearEditText mMnemonicInfo;
    @BindView(R.id.wallet_name)
    ClearEditText mWalletName;
    @BindView(R.id.password)
    ClearEditText mPassword;
    @BindView(R.id.password_two)
    ClearEditText mPasswordTwo;
    @BindView(R.id.go)
    Button mGo;

    private ArrayList<String> mWords = new ArrayList<>();


    @Override
    protected int setContentView() {
        return R.layout.fragment_mnemonic_import;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void onFragmentFirstVisible() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//当前页面防截屏录屏
    }


    @OnClick(R.id.go)
    public void onClick() {
        if (TextUtils.isEmpty(mMnemonicInfo.getText().toString().trim())) {
            toast(getString(R.string.toast_no_mnemonic));
            return;
        }
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

        List<UserBean> userBeans = null;
        try {
            userBeans = MyApplication.getDbController().searchAll();
        } catch (Exception e) {
            e.printStackTrace();
            userBeans=new ArrayList<>();
        }
        for (int i = 0; i < userBeans.size(); i++) {
            if (userBeans.get(i).getName().equals(mWalletName.getText().toString())) {
                toast(getString(R.string.toast_wallet_exit));
                return;
            }
        }

        onGenWords();


}


    private void onGenWords() {
        try {
            showProgress();
            mWords.clear();
            String[] newStr = mMnemonicInfo.getText().toString().split(" ");
            Collections.addAll(mWords, newStr);

            if (mWords.size() != 24) {
                hideProgress();
                toast(getString(R.string.toast_error_mnemonic));
                return;
            }
            if (!WalletManger.isMnemonicWords(mWords)) {
                hideProgress();
                toast(getString(R.string.toast_error_mnemonic));
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap = PasswordToKeyUtils.encryptPwdForPrivate(mPassword.getText().toString().trim(),WalletManger.getPrivateKeyFromMnemonicCode(mWords));
            String address0 = WalletManger.getAddressFromPrivateKey(WalletManger.getPrivateKeyFromMnemonicCode(mWords));
            UserBean mUserBean = new UserBean();
            mUserBean.setName(mWalletName.getText().toString().trim());
            mUserBean.setAddress(address0);
            mUserBean.setPublicKey(WalletManger.getPubFromPrivateKey(WalletManger.getPrivateKeyFromMnemonicCode(mWords)));
            mUserBean.setPrivateKey(hashMap.get("result").toString());
            mUserBean.setSalt(hashMap.get("userSalt").toString());
            Utils.getSpUtils().put(Constants.SpInfo.FIRSTUSER, mUserBean.getName());
            MyApplication.getDbController().insertOrReplace(mUserBean);
            hideProgress();
            ActivityUtils.next(getActivity(), MainActivity.class);
        } catch (Exception e) {
            e.printStackTrace();
            toast(getString(R.string.toast_error_mnemonic));
        }
    }

}
