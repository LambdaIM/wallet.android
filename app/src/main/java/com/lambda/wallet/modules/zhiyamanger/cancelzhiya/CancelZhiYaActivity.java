package com.lambda.wallet.modules.zhiyamanger.cancelzhiya;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostZhiYaGasBean;
import com.lambda.wallet.lambda.bean.msg.ZhiYaMsgBean;
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

public class CancelZhiYaActivity extends BaseAcitvity<CancelZhiYaView, CancelZhiYaPresenter> implements CancelZhiYaView {


    @BindView(R.id.from_address)
    TextView mFromAddress;
    @BindView(R.id.to_address)
    TextView mToAddress;
    @BindView(R.id.amount)
    ClearEditText mAmount;
    @BindView(R.id.go)
    Button mGo;

    ProducersDetailsBean mProducersDetailsBean = new ProducersDetailsBean();
    @BindView(R.id.zhiya_amount)
    TextView mZhiyaAmount;
    HashMap<String ,String> hashMap = new HashMap<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_cancel_zhi_ya;
    }

    @Override
    public CancelZhiYaPresenter initPresenter() {
        return new CancelZhiYaPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.cancel_the_pledge));
        mFromAddress.setText(MyApplication.getInstance().getUserBean().getAddress());

    }

    @Override
    protected void initData() {
        mProducersDetailsBean = getIntent().getParcelableExtra("details");
        mToAddress.setText(mProducersDetailsBean.getOperator_address());
        mZhiyaAmount.setText(StringUtils.deletzero(getIntent().getStringExtra("zhiya"))+"TBB");
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
        if (!BigDecimalUtil.greaterThan(new BigDecimal(getIntent().getStringExtra("zhiya")), new BigDecimal(mAmount.getText().toString().trim()))) {
            toast(getString(R.string.balance_no_enough));
            return;
        }

        PostZhiYaGasBean postZhiYaGasBean = new PostZhiYaGasBean();
        PostZhiYaGasBean.BaseReqBean baseReqBean = new PostZhiYaGasBean.BaseReqBean();
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


                PostZhiYaGasBean.AmountBean amountBean = new PostZhiYaGasBean.AmountBean();
                amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                amountBean.setDenom("utbb");

                postZhiYaGasBean.setBase_req(baseReqBean);
                postZhiYaGasBean.setAmount(amountBean);
                postZhiYaGasBean.setDelegator_address(mFromAddress.getText().toString().trim());
                postZhiYaGasBean.setValidator_address(mToAddress.getText().toString().trim());
                postZhiYaGasBean.setValidator_type(1);

                showProgress();
                presenter.getGasData(MyApplication.getInstance().getUserBean().getAddress(), postZhiYaGasBean);

            }
        }).getInfo();


    }

    @Override
    public void getGasDataHttp(GasBean gasBean) {
        hideProgress();
        PasswordDialog mPasswordDialog = new PasswordDialog(CancelZhiYaActivity.this, new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                showProgress();

                ZhiYaMsgBean msgsBean = new ZhiYaMsgBean();
                ZhiYaMsgBean.ValueBean valueBean = new ZhiYaMsgBean.ValueBean();
                msgsBean.setType(Constants.SignType.CANCELZHIYA);

                valueBean.setDelegator_address(mFromAddress.getText().toString().trim());
                valueBean.setValidator_address(mToAddress.getText().toString().trim());
                valueBean.setValidator_type(1);

                ZhiYaMsgBean.ValueBean.AmountBean amountBean = new  ZhiYaMsgBean.ValueBean.AmountBean();
                amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                amountBean.setDenom("utbb");

                valueBean.setAmount(amountBean);

                msgsBean.setValue(valueBean);

                List<ZhiYaMsgBean> zhiYaMsgBeans = new ArrayList<>();
                zhiYaMsgBeans.add(msgsBean);

                new PushDataManger<ZhiYaMsgBean>(CancelZhiYaActivity.this, password, new PushDataManger.Callback() {
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
                },hashMap).push(gasBean.getGas_estimate(),Utils.getSpUtils().getString(Constants.SpInfo.TOKEN) , "", zhiYaMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        mPasswordDialog.setGas(gasBean.getGas_estimate());
        mPasswordDialog.show();
    }
}
