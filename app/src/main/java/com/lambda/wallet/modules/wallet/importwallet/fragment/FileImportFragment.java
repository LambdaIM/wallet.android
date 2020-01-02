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
import com.lambda.wallet.modules.main.MainActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.JsonUtil;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 文件导入
 */
public class FileImportFragment extends LazyLoadFragment<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.file_info)
    ClearEditText mFileInfo;
    @BindView(R.id.wallet_name)
    ClearEditText mWalletName;
    @BindView(R.id.password)
    ClearEditText mPassword;
    @BindView(R.id.go)
    Button mGo;

    UserBean mUserBean = new UserBean();
    String privateKey = null;
    @Override
    protected int setContentView() {
        return R.layout.fragment_file_import;
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
        if (TextUtils.isEmpty(mFileInfo.getText().toString().trim())) {
            toast(getString(R.string.toast_file_info));
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

        try {
            mUserBean = (UserBean) JsonUtil.parseStringToBean(mFileInfo.getText().toString().trim(),UserBean.class);
            if(mUserBean==null){
                toast(getString(R.string.toast_error_file_info));
            }else{
                privateKey = PasswordToKeyUtils.dncryptPwdForPrivate(mPassword.getText().toString().trim(),mUserBean.getPrivateKey(),mUserBean.getSalt());
                if (TextUtils.isEmpty(privateKey)){
                    toast(getString(R.string.toast_rerror_password));
                }else{
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
                    mUserBean.setName(mWalletName.getText().toString());

                    Utils.getSpUtils().put(Constants.SpInfo.FIRSTUSER, mUserBean.getName());
                    MyApplication.getDbController().insertOrReplace(mUserBean);
                    hideProgress();
                    ActivityUtils.next(getActivity(), MainActivity.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            toast(getString(R.string.toast_error_file_info));
        }

    }



}
