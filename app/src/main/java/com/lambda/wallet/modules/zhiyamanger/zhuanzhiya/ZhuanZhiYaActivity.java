package com.lambda.wallet.modules.zhiyamanger.zhuanzhiya;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostZhuanZhiYaGasBean;
import com.lambda.wallet.lambda.bean.msg.ZhuanZhiYaMsgBean;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.KeyBoardUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;
import com.lambda.wallet.view.dialog.confimdialog.Callback;
import com.lambda.wallet.view.dialog.confimdialog.ConfirmDialog;
import com.lambda.wallet.view.dialog.passworddialog.PasswordDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2019/12/8 转质押
public class ZhuanZhiYaActivity extends BaseAcitvity<ZhuanZhiYaView, ZhuanZhiYaPresenter> implements ZhuanZhiYaView {


    @BindView(R.id.from_address)
    TextView mFromAddress;
    @BindView(R.id.old_address)
    TextView mOldAddress;
    @BindView(R.id.new_address)
    TextView mNewAddress;
    @BindView(R.id.amount)
    ClearEditText mAmount;
    @BindView(R.id.go)
    Button mGo;

    ProducersDetailsBean mProducersDetailsBean = new ProducersDetailsBean();

    private OptionsPickerView pvCustomOptions;//选择节点
    List<ProducersDetailsBean> mProducersListBeanDetails = new ArrayList<>();//节点列表
    List<String> producerList = new ArrayList<>();

    HashMap<String ,String> hashMap = new HashMap<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhuan_zhi_ya;
    }

    @Override
    public ZhuanZhiYaPresenter initPresenter() {
        return new ZhuanZhiYaPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.turn_the_pledge));
        mFromAddress.setText(MyApplication.getInstance().getUserBean().getAddress());

    }

    @Override
    protected void initData() {
        mProducersDetailsBean = getIntent().getParcelableExtra("details");
        mOldAddress.setText(mProducersDetailsBean.getOperator_address());
        showProgress();
        presenter.getProducersBondedData();
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.new_address, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_address:
                if (KeyBoardUtil.isSoftInputShow(ZhuanZhiYaActivity.this)) {
                    KeyBoardUtil.getInstance(ZhuanZhiYaActivity.this).hide();
                }
                if (pvCustomOptions == null) {
                    pvCustomOptions = new OptionsPickerView.Builder(ZhuanZhiYaActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            String tx = producerList.get(options1);
                            if (!tx.equals(mNewAddress.getText().toString().trim())) {
                                mNewAddress.setText(mProducersListBeanDetails.get(options1).getOperator_address());
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
                    pvCustomOptions.setPicker(producerList);//添加数据
                    pvCustomOptions.show();
                } else {
                    pvCustomOptions.show();
                }

                break;
            case R.id.go:
                if (TextUtils.isEmpty(mAmount.getText().toString().trim())) {
                    toast(getString(R.string.toast_no_amount));
                    return;
                }
                if (TextUtils.isEmpty(mNewAddress.getText().toString().trim())) {
                    toast(getString(R.string.toast_choose_producer));
                    return;
                }

                PostZhuanZhiYaGasBean postZhuanZhiYaGasBean = new PostZhuanZhiYaGasBean();
                PostZhuanZhiYaGasBean.BaseReqBean baseReqBean = new PostZhuanZhiYaGasBean.BaseReqBean();
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


                        PostZhuanZhiYaGasBean.AmountBean amountBean = new PostZhuanZhiYaGasBean.AmountBean();
                        amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                        amountBean.setDenom("utbb");

                        postZhuanZhiYaGasBean.setBase_req(baseReqBean);
                        postZhuanZhiYaGasBean.setAmount(amountBean);
                        postZhuanZhiYaGasBean.setDelegator_address(mFromAddress.getText().toString().trim());
                        postZhuanZhiYaGasBean.setValidator_src_address(mOldAddress.getText().toString().trim());
                        postZhuanZhiYaGasBean.setValidator_dst_address(mNewAddress.getText().toString().trim());
                        postZhuanZhiYaGasBean.setValidator_type(1);

                        showProgress();
                        presenter.getGasData(MyApplication.getInstance().getUserBean().getAddress(), postZhuanZhiYaGasBean);


                    }
                }).getInfo();

                break;
        }
    }

    @Override
    public void getGasDataHttp(GasBean gasBean) {
        hideProgress();
        PasswordDialog mPasswordDialog = new PasswordDialog(ZhuanZhiYaActivity.this, new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                // TODO: 2019/12/4 暂时忽略密码校验
                showProgress();

                ZhuanZhiYaMsgBean msgsBean = new ZhuanZhiYaMsgBean();
                ZhuanZhiYaMsgBean.ValueBean valueBean = new ZhuanZhiYaMsgBean.ValueBean();
                msgsBean.setType(Constants.SignType.ZHUANZHIYAZHIYA);

                valueBean.setDelegator_address(mFromAddress.getText().toString().trim());
                valueBean.setValidator_dst_address(mNewAddress.getText().toString().trim());
                valueBean.setValidator_src_address(mOldAddress.getText().toString().trim());
                valueBean.setValidator_type(1);

                ZhuanZhiYaMsgBean.ValueBean.AmountBean amountBean = new  ZhuanZhiYaMsgBean.ValueBean.AmountBean();
                amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(mAmount.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                amountBean.setDenom("utbb");

                valueBean.setAmount(amountBean);

                msgsBean.setValue(valueBean);

                List<ZhuanZhiYaMsgBean> zhuanZhiYaMsgBeans = new ArrayList<>();
                zhuanZhiYaMsgBeans.add(msgsBean);

                new PushDataManger<ZhuanZhiYaMsgBean>(ZhuanZhiYaActivity.this, password, new PushDataManger.Callback() {
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
                        ConfirmDialog confirmDialog = new ConfirmDialog(ZhuanZhiYaActivity.this, new Callback() {
                            @Override
                            public void sure() {

                            }
                        });
                        confirmDialog.setContent(msg);
                        confirmDialog.show();
                    }
                },hashMap).push(gasBean.getGas_estimate(),Utils.getSpUtils().getString(Constants.SpInfo.TOKEN) , "", zhuanZhiYaMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        String gas = BigDecimalUtil.multiply(new BigDecimal(gasBean.getGas_estimate()), new BigDecimal(1.5)).toString();
        String amount = BigDecimalUtil.multiply(new BigDecimal(Math.round(Double.parseDouble(gas))+""), new BigDecimal(Constants.GAS_PRICE)).toString();
        mPasswordDialog.setGas(Math.round(Double.parseDouble(amount)) + "");
        mPasswordDialog.show();


    }

    @Override
    public void getBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean) {
        for (int i = 0; i < producersDetailsBean.size(); i++) {
            mProducersListBeanDetails.add(producersDetailsBean.get(i));
        }
        presenter.getProducersunBondedData();
    }

    @Override
    public void getUnBondedProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean) {
        for (int i = 0; i < producersDetailsBean.size(); i++) {
            mProducersListBeanDetails.add(producersDetailsBean.get(i));
        }
        presenter.getProducersBondedingData();
    }

    @Override
    public void getUnBondingProducerListDataHttp(List<ProducersDetailsBean> producersDetailsBean) {
        hideProgress();
        for (int i = 0; i < producersDetailsBean.size(); i++) {
            mProducersListBeanDetails.add(producersDetailsBean.get(i));
        }

        for (int i = 0; i < mProducersListBeanDetails.size(); i++) {
            producerList.add(StringUtils.lambdaAddress(mProducersListBeanDetails.get(i).getOperator_address()) + "   " + mProducersListBeanDetails.get(i).getDescription().getMoniker());
        }
    }

}
