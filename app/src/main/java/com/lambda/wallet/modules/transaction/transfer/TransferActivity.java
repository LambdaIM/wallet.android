package com.lambda.wallet.modules.transaction.transfer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.QrCodeTokenMakeCollectionBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostTransferGasBean;
import com.lambda.wallet.lambda.bean.msg.TransferMsgBean;
import com.lambda.wallet.modules.transaction.history.HistoryTransferActivity;
import com.lambda.wallet.util.AndroidBug5497Workaround;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.KeyBoardUtil;
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

// TODO: 2019/11/29 转账
public class TransferActivity extends BaseAcitvity<TransferView, TransferPresenter> implements TransferView {


    @BindView(R.id.to_address)
    ClearEditText mToAddress;
    @BindView(R.id.token)
    TextView mToken;
    @BindView(R.id.can_use_amount)
    TextView mCanUseAmount;
    @BindView(R.id.amount)
    ClearEditText mAmount;
    @BindView(R.id.memo)
    ClearEditText mMemo;
    @BindView(R.id.go)
    Button mGo;

    private OptionsPickerView pvCustomOptions;//选择资产
    private List<String> mTokenList = new ArrayList<>();
    ArrayList<HomeAddressDetailsBean.ValueBean.CoinsBean> mCoinsBeans = new ArrayList<>();//资产信息
    QrCodeTokenMakeCollectionBean qrCodeMakeCollectionBean = new QrCodeTokenMakeCollectionBean();

    private int select = 0;//默认选中的资产
    HashMap<String ,String> hashMap = new HashMap<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    public TransferPresenter initPresenter() {
        return new TransferPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistActivity(activity);
        setCenterTitle(getString(R.string.transfer));
        setRightTitle(getString(R.string.transfer_history), true);

    }

    @Override
    protected void initData() {
        mCoinsBeans = getIntent().getParcelableArrayListExtra("token");
        if (getIntent().getStringExtra("from").equals("qrcode")) {//从二维码进入
            qrCodeMakeCollectionBean = getIntent().getParcelableExtra("info");
            mToAddress.setText(qrCodeMakeCollectionBean.getAddress());
            mToken.setText(StringUtils.lambdaToken(qrCodeMakeCollectionBean.getToken()));
            for (int i = 0; i < mCoinsBeans.size(); i++) {
                mTokenList.add(StringUtils.lambdaToken(mCoinsBeans.get(i).getDenom()));
                if (mCoinsBeans.get(i).getDenom().equals(qrCodeMakeCollectionBean.getToken())) {
                    mCanUseAmount.setText(StringUtils.addComma(mCoinsBeans.get(i).getAmount()) );
                    select = i;
                }
            }
            mAmount.setFocusable(true);
            mAmount.setFocusableInTouchMode(true);
            mAmount.requestFocus();
        } else {//从首页进入
            for (int i = 0; i < mCoinsBeans.size(); i++) {
                mTokenList.add(StringUtils.lambdaToken(mCoinsBeans.get(i).getDenom()));
            }
            mCanUseAmount.setText(StringUtils.addComma(mCoinsBeans.get(0).getAmount()) );
            mToken.setText(StringUtils.lambdaToken(mCoinsBeans.get(0).getDenom()));
            select = 0;
        }
    }

    @Override
    public void initEvent() {
        getId(R.id.tv_right_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                ActivityUtils.next(TransferActivity.this, HistoryTransferActivity.class, bundle);
            }
        });

    }


    @OnClick({R.id.token, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.token:
                if (KeyBoardUtil.isSoftInputShow(TransferActivity.this)) {
                    KeyBoardUtil.getInstance(TransferActivity.this).hide();
                }
                if (pvCustomOptions == null) {
                    pvCustomOptions = new OptionsPickerView.Builder(TransferActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            String tx = mTokenList.get(options1);
                            if (!tx.equals(mToken.getText().toString().trim())) {
                                mToken.setText(tx);
                                select = options1;
                                mCanUseAmount.setText(StringUtils.deletzero(mCoinsBeans.get(options1).getAmount()));
                            }
                        }
                    })
                            .setTitleBgColor(getResources().getColor(R.color.white))
                            .setSubmitText(getString(R.string.sure))//确定按钮文字
                            .setCancelText(getString(R.string.cancel))//取消按钮文字
                            .setSubCalSize(14)//确定和取消文字大小
                            .setContentTextSize(15)//滚轮文字大小
                            .setSubmitColor(getResources().getColor(R.color.theme_color))//确定按钮文字颜色
                            .setCancelColor(getResources().getColor(R.color.gray_color))//取消按钮文字颜色
                            .setLineSpacingMultiplier(2.0f)
                            .setDividerType(WheelView.DividerType.FILL)
                            .build();
                    pvCustomOptions.setPicker(mTokenList);//添加数据
                    pvCustomOptions.show();
                } else {
                    pvCustomOptions.show();
                }

                break;
            case R.id.go:
                if (TextUtils.isEmpty(mToAddress.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_receiver));
                    return;
                }
                if (TextUtils.isEmpty(mAmount.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_amount));
                    return;
                }
                if (!BigDecimalUtil.greaterThan(new BigDecimal(mCoinsBeans.get(select).getAmount()), new BigDecimal(mAmount.getText().toString().trim()))) {
                    toast(getString(R.string.balance_no_enough));
                    return;
                }
                if (MyApplication.getInstance().getUserBean().getAddress().equals(mToAddress.getText().toString().trim())) {
                    toast(getString(R.string.toast_error_to_yourself));
                    return;
                }
                if (mToAddress.getText().toString().trim().length()!=45){
                    toast(getString(R.string.toast_error_lambda));
                    return;
                }
                PostTransferGasBean postTransferGasBean = new PostTransferGasBean();
                PostTransferGasBean.BaseReqBean baseReqBean = new PostTransferGasBean.BaseReqBean();
               new ChainInfoUtils(new ChainInfoUtils.Callback() {
                    @Override
                    public void onSuccess(HashMap<String, String> hashMap1) {
                        hashMap =hashMap1;

                        baseReqBean.setSequence(hashMap.get("sequence"));
                        baseReqBean.setAccount_number(hashMap.get("account_number"));
                        baseReqBean.setFrom(MyApplication.getInstance().getUserBean().getAddress());
                        baseReqBean.setChain_id(hashMap.get("chain_id"));
                        baseReqBean.setSimulate(true);
                        baseReqBean.setMemo(mMemo.getText().toString());

                        List<PostTransferGasBean.AmountBean> amountBeans = new ArrayList<>();
                        PostTransferGasBean.AmountBean amountBean = new PostTransferGasBean.AmountBean();
                        amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                        amountBean.setDenom(mCoinsBeans.get(select).getDenom());
                        amountBeans.add(amountBean);

                        postTransferGasBean.setBase_req(baseReqBean);
                        postTransferGasBean.setFrom_address(MyApplication.getInstance().getUserBean().getAddress());
                        postTransferGasBean.setTo_address(mToAddress.getText().toString().trim());
                        postTransferGasBean.setAmount(amountBeans);

                        showProgress();
                        presenter.getGasData(MyApplication.getInstance().getUserBean().getAddress(), postTransferGasBean);


                    }
                }).getInfo();

                break;
        }
    }

    @Override
    public void getGasDataHttp(GasBean gasBean) {
        hideProgress();
        PasswordDialog mPasswordDialog = new PasswordDialog(TransferActivity.this, new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                showProgress();

                TransferMsgBean msgsBean = new TransferMsgBean();
                TransferMsgBean.ValueBean valueBean = new TransferMsgBean.ValueBean();
                List<TransferMsgBean.ValueBean.AmountBean> amountBeanXES = new ArrayList<>();
                msgsBean.setType(Constants.SignType.TRANSFER);

                valueBean.setFrom_address(MyApplication.getInstance().getUserBean().getAddress());
                valueBean.setTo_address(mToAddress.getText().toString().trim());
                amountBeanXES.add(new TransferMsgBean.ValueBean.AmountBean(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()), mCoinsBeans.get(select).getDenom()));
                valueBean.setAmount(amountBeanXES);
                msgsBean.setValue(valueBean);
                List<TransferMsgBean> transferMsgBeans = new ArrayList<>();
                transferMsgBeans.add(msgsBean);

                new PushDataManger<TransferMsgBean>(TransferActivity.this, password, new PushDataManger.Callback() {
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
                },hashMap).push(gasBean.getGas_estimate(), Utils.getSpUtils().getString(Constants.SpInfo.TOKEN), mMemo.getText().toString(), transferMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        mPasswordDialog.setGas(gasBean.getGas_estimate());
        mPasswordDialog.show();
    }

}
