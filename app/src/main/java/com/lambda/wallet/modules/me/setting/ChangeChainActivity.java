package com.lambda.wallet.modules.me.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.AppManager;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.RegexUtil;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.dialog.addchaindialog.AddChainCallback;
import com.lambda.wallet.view.dialog.addchaindialog.AddChainDialog;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangeChainActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


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
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle("切换验证节点");
        setRightTitle("新增", true);

        if (Utils.getSpUtils().getString("url").equals("http://39.107.247.86:13659")) {
            mChooseOne.setChecked(true);
            mChooseTwo.setChecked(false);
            mChooseThree.setChecked(false);
        } else if (Utils.getSpUtils().getString("url").equals("http://47.93.196.236:13659")){
            mChooseOne.setChecked(false);
            mChooseTwo.setChecked(true);
            mChooseThree.setChecked(false);
        }else if (Utils.getSpUtils().getString("url").equals(Utils.getSpUtils().getString("addUrl"))){
            mChooseOne.setChecked(false);
            mChooseTwo.setChecked(false);
            mChooseThree.setChecked(true);
        }

        if (TextUtils.isEmpty(Utils.getSpUtils().getString("addUrl"))){
            mRelAddChain.setVisibility(View.GONE);
        }else {
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
                        if (addUrl.equals("http://39.107.247.86:13659")|| addUrl.equals("http://47.93.196.236:13659")||
                                addUrl.equals(Utils.getSpUtils().getString("addUrl"))) {
                            toast("该节点已存在");
                        }else {
                            mAddChain.setText(addUrl);
                            mChooseOne.setChecked(false);
                            mChooseTwo.setChecked(false);
                            mChooseThree.setChecked(true);
                            Utils.getSpUtils().put("addUrl",addUrl);
                            Utils.getSpUtils().put("url",addUrl);
                            AppManager.getAppManager().restartApp(ChangeChainActivity.this);
                        }
                    }
                });
                addChainDialog.setCanceledOnTouchOutside(true);
                addChainDialog.show();

            }
        });
    }

    @OnClick({R.id.rel_main_chain, R.id.rel_test_chain, R.id.rel_add_chain})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_main_chain:
                mChooseOne.setChecked(true);
                mChooseTwo.setChecked(false);
                mChooseThree.setChecked(false);
                if (!Utils.getSpUtils().getString("url").equals("http://39.107.247.86:13659")) {
                    Utils.getSpUtils().put("url", "http://39.107.247.86:13659");
                    AppManager.getAppManager().restartApp(this);
                }
                break;
            case R.id.rel_test_chain:
                mChooseOne.setChecked(false);
                mChooseTwo.setChecked(true);
                mChooseThree.setChecked(false);
                if (!Utils.getSpUtils().getString("url").equals("http://47.93.196.236:13659")) {
                    Utils.getSpUtils().put("url", "http://47.93.196.236:13659");
                    AppManager.getAppManager().restartApp(this);
                }
                break;
            case R.id.rel_add_chain:
                mChooseOne.setChecked(false);
                mChooseTwo.setChecked(false);
                mChooseThree.setChecked(true);
                if (!Utils.getSpUtils().getString("url").equals(Utils.getSpUtils().getString("addUrl"))) {
                    Utils.getSpUtils().put("url", mAddChain.getText().toString());
                    AppManager.getAppManager().restartApp(this);
                }
                break;
        }
    }


}
