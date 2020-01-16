package com.lambda.wallet.modules.proposal.cashpledge;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostCashPledgeGasBean;
import com.lambda.wallet.lambda.bean.msg.CashPledgeMsgBean;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;
import com.lambda.wallet.view.dialog.passworddialog.PasswordDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2019/12/10 存入押金
public class CashPledgeActivity extends BaseAcitvity<CashPledgeView, CashPledgePresenter> implements CashPledgeView {

    @BindView(R.id.from_address)
    TextView mFromAddress;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.can_use_amount)
    TextView mCanUseAmount;
    @BindView(R.id.amount)
    ClearEditText mAmount;
    @BindView(R.id.go)
    Button mGo;

    HashMap<String ,String> hashMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cash_pledge;
    }

    @Override
    public CashPledgePresenter initPresenter() {
        return new CashPledgePresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.save_deposit));
        mFromAddress.setText(MyApplication.getInstance().getUserBean().getAddress());
        mTitle.setText(getIntent().getStringExtra("title"));
        mCanUseAmount.setText(StringUtils.deletzero(Utils.getSpUtils().getString(Constants.SpInfo.LAMB)) + "LAMB");

    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @OnClick(R.id.go)
    public void onClick() {
        if (TextUtils.isEmpty(mAmount.getText().toString().trim())) {
            toast(getString(R.string.toast_no_amount));
            return;
        }
        if (!BigDecimalUtil.greaterThan(new BigDecimal(Utils.getSpUtils().getString(Constants.SpInfo.LAMB)), new BigDecimal(mAmount.getText().toString().trim()))) {
            toast(getString(R.string.balance_no_enough));
            return;
        }

        PostCashPledgeGasBean postCashPledgeGasBean = new PostCashPledgeGasBean();
        PostCashPledgeGasBean.BaseReqBean baseReqBean = new PostCashPledgeGasBean.BaseReqBean();
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


                PostCashPledgeGasBean.AmountBean amountBean = new PostCashPledgeGasBean.AmountBean();
                amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                amountBean.setDenom(Utils.getSpUtils().getString(Constants.SpInfo.TOKEN));
                List<PostCashPledgeGasBean.AmountBean> amountBeans = new ArrayList<>();
                amountBeans.add(amountBean);

                postCashPledgeGasBean.setBase_req(baseReqBean);
                postCashPledgeGasBean.setAmount(amountBeans);
                postCashPledgeGasBean.setDepositor(mFromAddress.getText().toString().trim());
                postCashPledgeGasBean.setProposal_id(getIntent().getStringExtra("id"));

                showProgress();
                presenter.getGasData(getIntent().getStringExtra("id"), postCashPledgeGasBean);

            }
        }).getInfo();


    }

    @Override
    public void getGasDataHttp(GasBean gasBean) {
        hideProgress();
        PasswordDialog mPasswordDialog = new PasswordDialog(CashPledgeActivity.this, new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                // TODO: 2019/12/4 暂时忽略密码校验
                showProgress();

                CashPledgeMsgBean msgsBean = new CashPledgeMsgBean();
                CashPledgeMsgBean.ValueBean valueBean = new CashPledgeMsgBean.ValueBean();
                msgsBean.setType(Constants.SignType.MSGDEPOSIT);

                valueBean.setDepositor(mFromAddress.getText().toString().trim());
                valueBean.setProposal_id(getIntent().getStringExtra("id"));


                CashPledgeMsgBean.ValueBean.AmountBean amountBean = new CashPledgeMsgBean.ValueBean.AmountBean();
                amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                amountBean.setDenom(Utils.getSpUtils().getString(Constants.SpInfo.TOKEN));
                List<CashPledgeMsgBean.ValueBean.AmountBean> amountBeans = new ArrayList<>();
                amountBeans.add(amountBean);

                valueBean.setAmount(amountBeans);

                msgsBean.setValue(valueBean);

                List<CashPledgeMsgBean> cashPledgeMsgBeans = new ArrayList<>();
                cashPledgeMsgBeans.add(msgsBean);

                new PushDataManger<CashPledgeMsgBean>(CashPledgeActivity.this, password, new PushDataManger.Callback() {
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
