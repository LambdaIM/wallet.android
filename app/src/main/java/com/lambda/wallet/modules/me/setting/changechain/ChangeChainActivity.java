package com.lambda.wallet.modules.me.setting.changechain;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.AppManager;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.ChainInfoBean;
import com.lambda.wallet.util.RegexUtil;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.dialog.addchaindialog.AddChainCallback;
import com.lambda.wallet.view.dialog.addchaindialog.AddChainDialog;
import com.lambda.wallet.view.dialog.chaininfodialog.Callback;
import com.lambda.wallet.view.dialog.chaininfodialog.ChainInfoDialog;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeChainActivity extends BaseAcitvity<ChangeChainView, ChangeChainPresenter> implements ChangeChainView {


    @BindView(R.id.choose_one)
    CheckBox mChooseOne;
    @BindView(R.id.rel_main_chain)
    RelativeLayout mRelMainChain;
    @BindView(R.id.choose_two)
    CheckBox mChooseTwo;
    @BindView(R.id.rel_test_chain)
    RelativeLayout mRelTestChain;
    @BindView(R.id.choose_three)
    CheckBox mChooseThree;
    @BindView(R.id.rel_add_chain)
    RelativeLayout mRelAddChain;
    @BindView(R.id.add_chain)
    TextView mAddChain;

    String addUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_chain;
    }

    @Override
    public ChangeChainPresenter initPresenter() {
        return new ChangeChainPresenter(ChangeChainActivity.this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.change_node));
        setRightTitle(getString(R.string.add), true);

        if (Utils.getSpUtils().getString("url").equals("http://39.107.247.86:13659")) {
            mChooseOne.setChecked(true);
            mChooseTwo.setChecked(false);
            mChooseThree.setChecked(false);
        } else if (Utils.getSpUtils().getString("url").equals("http://47.93.196.236:13659")) {
            mChooseOne.setChecked(false);
            mChooseTwo.setChecked(true);
            mChooseThree.setChecked(false);
        } else if (Utils.getSpUtils().getString("url").equals(Utils.getSpUtils().getString("addUrl"))) {
            mChooseOne.setChecked(false);
            mChooseTwo.setChecked(false);
            mChooseThree.setChecked(true);
        }

        if (TextUtils.isEmpty(Utils.getSpUtils().getString("addUrl"))) {
            mRelAddChain.setVisibility(View.GONE);
        } else {
            mRelAddChain.setVisibility(View.VISIBLE);
            mAddChain.setText(Utils.getSpUtils().getString("addUrl"));
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        getId(R.id.tv_right_text).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                AddChainDialog addChainDialog = new AddChainDialog(ChangeChainActivity.this, new AddChainCallback() {
                    @Override
                    public void sure(String url) {

                        if (RegexUtil.checkURL(url)) {
                            addUrl = url;
                        } else {
                            addUrl = "http://" + url;
                        }
                        if (addUrl.equals("http://39.107.247.86:13659") || addUrl.equals("http://47.93.196.236:13659") ||
                                addUrl.equals(Utils.getSpUtils().getString("addUrl"))) {
                            toast(getString(R.string.node_exited));
                        } else {
                            mRelAddChain.setVisibility(View.VISIBLE);
                            mAddChain.setText(addUrl);
//                            mChooseOne.setChecked(false);
//                            mChooseTwo.setChecked(false);
//                            mChooseThree.setChecked(true);
                            Utils.getSpUtils().put("addUrl", addUrl);
                            presenter.getChainInfoData(Utils.getSpUtils().getString("addUrl"));
                        }
                    }
                });
                addChainDialog.setCanceledOnTouchOutside(false);
                addChainDialog.show();

            }
        });
    }

    @OnClick({R.id.rel_main_chain, R.id.rel_test_chain, R.id.rel_add_chain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_main_chain:
                presenter.getChainInfoData("http://39.107.247.86:13659");
                break;
            case R.id.rel_test_chain:
                presenter.getChainInfoData("http://47.93.196.236:13659");
                break;
            case R.id.rel_add_chain:
                presenter.getChainInfoData(Utils.getSpUtils().getString("addUrl"));
                break;
        }
    }
    @Override
    public void getChainInfoDataHttp(ChainInfoBean chainInfoBean,String url) {
        if (chainInfoBean!=null){
            ChainInfoDialog chainInfoDialog = new ChainInfoDialog(ChangeChainActivity.this, new Callback() {
                @Override
                public void sure() {
                    if (url.equals("http://39.107.247.86:13659")) {
                        mChooseOne.setChecked(true);
                        mChooseTwo.setChecked(false);
                        mChooseThree.setChecked(false);
                        Utils.getSpUtils().put("url", "http://39.107.247.86:13659");
                        AppManager.getAppManager().restartApp(ChangeChainActivity.this);
                    }
                    if (url.equals("http://47.93.196.236:13659")) {
                        mChooseOne.setChecked(false);
                        mChooseTwo.setChecked(true);
                        mChooseThree.setChecked(false);
                        Utils.getSpUtils().put("url", "http://47.93.196.236:13659");
                        AppManager.getAppManager().restartApp(ChangeChainActivity.this);
                    }
                    if (url.equals(Utils.getSpUtils().getString("addUrl"))) {
                        mChooseOne.setChecked(false);
                        mChooseTwo.setChecked(false);
                        mChooseThree.setChecked(true);
                        Utils.getSpUtils().put("url", mAddChain.getText().toString());
                        AppManager.getAppManager().restartApp(ChangeChainActivity.this);
                    }
                }
            });
            chainInfoDialog.setCanceledOnTouchOutside(false);
            chainInfoDialog.setContent(chainInfoBean);
            chainInfoDialog.show();
        }
    }

    @Override
    public void getFailDataHttp(String url) {
        ChainInfoDialog chainInfoDialog = new ChainInfoDialog(ChangeChainActivity.this, new Callback() {
            @Override
            public void sure() {
            }
        });
        chainInfoDialog.setCanceledOnTouchOutside(false);
        chainInfoDialog.setError(url);
        chainInfoDialog.show();
    }

}
