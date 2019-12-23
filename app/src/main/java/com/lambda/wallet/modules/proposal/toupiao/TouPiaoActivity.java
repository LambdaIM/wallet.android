package com.lambda.wallet.modules.proposal.toupiao;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostTouPiaoGasBean;
import com.lambda.wallet.lambda.bean.msg.TouPiaoMsgBean;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.dialog.passworddialog.PasswordDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2019/12/10 投票 
public class TouPiaoActivity extends BaseAcitvity<TouPiaoView, TouPiaoPresenter> implements TouPiaoView {


    @BindView(R.id.from_address)
    TextView mFromAddress;
    @BindView(R.id.ti_an_title)
    TextView mTiAnTitle;
    @BindView(R.id.choose_one)
    CheckBox mChooseOne;
    @BindView(R.id.rel_yes)
    RelativeLayout mRelYes;
    @BindView(R.id.choose_two)
    CheckBox mChooseTwo;
    @BindView(R.id.rel_no)
    RelativeLayout mRelNo;
    @BindView(R.id.choose_three)
    CheckBox mChooseThree;
    @BindView(R.id.rel_no_with_veto)
    RelativeLayout mRelNoWithVeto;
    @BindView(R.id.choose_four)
    CheckBox mChooseFour;
    @BindView(R.id.rel_abstain)
    RelativeLayout mRelAbstain;
    @BindView(R.id.go)
    Button mGo;

    String option = "Yes";

    HashMap<String ,String> hashMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tou_piao;
    }

    @Override
    public TouPiaoPresenter initPresenter() {
        return new TouPiaoPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle("投票");
        mFromAddress.setText(MyApplication.getInstance().getUserBean().getAddress());
        mTiAnTitle.setText(getIntent().getStringExtra("title"));
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.rel_yes, R.id.rel_no, R.id.rel_no_with_veto, R.id.rel_abstain, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_yes:
                mChooseOne.setChecked(true);
                mChooseTwo.setChecked(false);
                mChooseThree.setChecked(false);
                mChooseFour.setChecked(false);
                option = "Yes";
                break;
            case R.id.rel_no:
                mChooseOne.setChecked(false);
                mChooseTwo.setChecked(true);
                mChooseThree.setChecked(false);
                mChooseFour.setChecked(false);
                option = "No";
                break;
            case R.id.rel_no_with_veto:
                mChooseOne.setChecked(false);
                mChooseTwo.setChecked(false);
                mChooseThree.setChecked(true);
                mChooseFour.setChecked(false);
                option = "NoWithVeto";
                break;
            case R.id.rel_abstain:
                mChooseOne.setChecked(false);
                mChooseTwo.setChecked(false);
                mChooseThree.setChecked(false);
                mChooseFour.setChecked(true);
                option = "Abstain";
                break;
            case R.id.go:
                PostTouPiaoGasBean postTouPiaoGasBean = new PostTouPiaoGasBean();
                PostTouPiaoGasBean.BaseReqBean baseReqBean = new PostTouPiaoGasBean.BaseReqBean();
                new ChainInfoUtils(new ChainInfoUtils.Callback() {
                    @Override
                    public void onSuccess(HashMap<String, String> hashMap1) {
                        hashMap =hashMap1;
                        baseReqBean.setSequence(hashMap.get("sequence"));
                        baseReqBean.setAccount_number(hashMap.get("account_number"));
                        baseReqBean.setFrom(MyApplication.getInstance().getUserBean().getAddress());
                        baseReqBean.setChain_id(hashMap.get("chain_id"));
                        baseReqBean.setSimulate(true);
                        baseReqBean.setMemo("");


                        postTouPiaoGasBean.setBase_req(baseReqBean);
                        postTouPiaoGasBean.setOption(option);
                        postTouPiaoGasBean.setVoter(mFromAddress.getText().toString().trim());
                        postTouPiaoGasBean.setProposal_id(getIntent().getStringExtra("id"));

                        showProgress();
                        presenter.getGasData(getIntent().getStringExtra("id"), postTouPiaoGasBean);


                    }
                }).getInfo();

                break;
        }
    }

    @Override
    public void getGasDataHttp(GasBean gasBean) {
        hideProgress();
        PasswordDialog mPasswordDialog = new PasswordDialog(TouPiaoActivity.this, new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                // TODO: 2019/12/4 暂时忽略密码校验
                showProgress();

                TouPiaoMsgBean msgsBean = new TouPiaoMsgBean();
                TouPiaoMsgBean.ValueBean valueBean = new TouPiaoMsgBean.ValueBean();
                msgsBean.setType(Constants.SignType.MSGVOTE);

                valueBean.setVoter(mFromAddress.getText().toString().trim());
                valueBean.setProposal_id(getIntent().getStringExtra("id"));
                valueBean.setOption(option);

                msgsBean.setValue(valueBean);

                List<TouPiaoMsgBean> cashPledgeMsgBeans = new ArrayList<>();
                cashPledgeMsgBeans.add(msgsBean);

                new PushDataManger<TouPiaoMsgBean>(TouPiaoActivity.this, password, new PushDataManger.Callback() {
                    @Override
                    public void onSuccess(TransactionSuccessBean transactionSuccessBean) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                hideProgress();
                                finish();
                            }
                        }, 7000);//延时10S刷新数据
                    }

                    @Override
                    public void onFail(String msg) {
                        hideProgress();
                        toast(msg);
                    }
                },hashMap).push(gasBean.getGas_estimate(),Utils.getSpUtils().getString(Constants.SpInfo.TOKEN) , "", cashPledgeMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        mPasswordDialog.setGas(gasBean.getGas_estimate());
        mPasswordDialog.show();
    }



}
