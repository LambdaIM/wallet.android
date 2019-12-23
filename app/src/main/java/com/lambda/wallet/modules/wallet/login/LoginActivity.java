package com.lambda.wallet.modules.wallet.login;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.adapter.baseadapter.MultiItemTypeAdapter;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.UserBean;
import com.lambda.wallet.lambda.PasswordToKeyUtils;
import com.lambda.wallet.modules.main.MainActivity;
import com.lambda.wallet.modules.wallet.creatwallet.CreatWalletActivity;
import com.lambda.wallet.modules.wallet.importwallet.activity.ImportWalletActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.AndroidBug5497Workaround;
import com.lambda.wallet.util.KeyBoardUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;
import com.lambda.wallet.view.popupwindow.BasePopupWindow;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录创建页面
 */
public class LoginActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.img)
    RelativeLayout mImg;
    @BindView(R.id.wallet)
    TextView mWallet;
    @BindView(R.id.password)
    ClearEditText mPassword;
    @BindView(R.id.go)
    Button mGo;
    @BindView(R.id.creat_wallet)
    TextView mCreatWallet;
    @BindView(R.id.import_wallet)
    TextView mImportWallet;

    BasePopupWindow basePopupWindow;

    List<UserBean> mUserBeanList = new ArrayList<>();//钱包列表
    CommonAdapter mCommonAdapter;
    private OptionsPickerView pvCustomOptions;//选择资产

    private int select = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(false).statusBarColor(R.color.transparent).titleBar(mImg).statusBarDarkFont(false, 0.2f).init();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistActivity(activity);
    }

    @Override
    protected void initData() {
        try {
            mUserBeanList = MyApplication.getDbController().searchAll();
        } catch (Exception e) {
            e.printStackTrace();
            mUserBeanList = new ArrayList<>();
        }
    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.wallet, R.id.go, R.id.creat_wallet, R.id.import_wallet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wallet:
                if (KeyBoardUtil.isSoftInputShow(LoginActivity.this)) {
                    KeyBoardUtil.getInstance(LoginActivity.this).hide();
                }
                if (mUserBeanList.size() == 0) {
                    toast(getString(R.string.toast_no_wallet));
                    return;
                }
                if (basePopupWindow != null && basePopupWindow.isShowing()) {
                    basePopupWindow.dismiss();
                } else {
                    basePopupWindow = new BasePopupWindow.Builder(LoginActivity.this).
                            setView(LayoutInflater.from(LoginActivity.this).inflate(R.layout.popuwindow_wallet, null))
                            .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setOutsideTouchable(true)
                            .setAnimationStyle(R.style.AnimDown)
                            .create();
                    basePopupWindow.showAsDropDown(mWallet);
                    RecyclerView mWalletList = basePopupWindow.getContentView().findViewById(R.id.wallet_list);
                    LinearLayout mLL = basePopupWindow.getContentView().findViewById(R.id.ll);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    layoutManager.setSmoothScrollbarEnabled(true);
                    mWalletList.setLayoutManager(layoutManager);
                    mCommonAdapter = AdapterManger.getWalletAdapter(this, mUserBeanList);
                    mWalletList.setAdapter(mCommonAdapter);

                    mCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            mWallet.setText(StringUtils.lambdaAddress(mUserBeanList.get(position).getAddress()));
                            select = position;
                            basePopupWindow.dismiss();
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                    mLL.setOnClickListener(new NoDoubleClickListener() {
                        @Override
                        protected void onNoDoubleClick(View v) {
                            basePopupWindow.dismiss();
                        }
                    });

                }
                break;
            case R.id.go:
                // TODO: 2019/12/5  暂时忽略密码校验
                if (select == -1) {
                    toast(getString(R.string.toast_no_choose_wallet));
                    return;
                }
                if (TextUtils.isEmpty(mPassword.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_password));
                    return;
                }
                UserBean userBean = new UserBean();
                userBean = mUserBeanList.get(select);
                try {
                    if (!TextUtils.isEmpty(PasswordToKeyUtils.dncryptPwdForPrivate(mPassword.getText().toString(), userBean.getPrivateKey(), userBean.getSalt()))) {
                        MyApplication.getInstance().setUserBean(userBean);
                        Utils.getSpUtils().put(Constants.SpInfo.FIRSTUSER, userBean.getName());
                        ActivityUtils.next(this, MainActivity.class);
                    } else {
                        toast(getString(R.string.toast_rerror_password));
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    toast(getString(R.string.toast_rerror_password));
                }
                break;
            case R.id.creat_wallet:
                ActivityUtils.next(this, CreatWalletActivity.class);
                break;
            case R.id.import_wallet:
                ActivityUtils.next(this, ImportWalletActivity.class);
                break;
        }
    }
}
