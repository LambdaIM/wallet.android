package com.lambda.wallet.modules.transaction.exchange.fragment.lamb2tbb;


import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.base.LazyLoadFragment;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostLamb2TbbGasBean;
import com.lambda.wallet.lambda.bean.msg.Lamb2TbbMsgBean;
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

/**
 * lamb兑换tbb
 */
public class LambExchangeFragment extends LazyLoadFragment<LambExchangeView, LambExchangePresenter> implements LambExchangeView {


    @BindView(R.id.token)
    ClearEditText mToken;
    @BindView(R.id.get_token)
    TextView mGetToken;
    @BindView(R.id.go)
    Button mGo;

    String tbb, lamb = null;
    HashMap<String ,String> hashMap = new HashMap<>();
    @Override
    protected int setContentView() {
        return R.layout.fragment_lamb_exchange;
    }

    @Override
    public LambExchangePresenter initPresenter() {
        return new LambExchangePresenter(getActivity());
    }

    @Override
    protected void onFragmentFirstVisible() {
        mToken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    lamb = mToken.getText().toString().trim().toString();
                    tbb = StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(lamb), new BigDecimal(3000)).toString());
                    mGetToken.setText(tbb);
                } catch (Exception e) {
                    e.printStackTrace();
                    mGetToken.setText("0");
                    lamb = null;
                    tbb = null;
                }
            }
        });
    }


    @OnClick(R.id.go)
    public void onClick() {
        if (TextUtils.isEmpty(lamb)) {
            toast(getString(R.string.toast_no_exchange_tbb));
            return;
        }
        if (lamb.equals("0")) {
            toast(getString(R.string.toast_error_tbb));
            return;
        }
        if (tbb.contains(".")) {
            toast(getString(R.string.toast_no_tbb_int));
            return;
        }
        PostLamb2TbbGasBean postLamb2TbbGasBean = new PostLamb2TbbGasBean();
        PostLamb2TbbGasBean.BaseReqBean baseReqBean = new PostLamb2TbbGasBean.BaseReqBean();
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

                postLamb2TbbGasBean.setBase_req(baseReqBean);
                postLamb2TbbGasBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());

                PostLamb2TbbGasBean.AssetBean assetBean = new PostLamb2TbbGasBean.AssetBean();
                assetBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(tbb),new BigDecimal(1000000)).toString()));
                assetBean.setDenom("utbb");

                postLamb2TbbGasBean.setAsset(assetBean);

                PostLamb2TbbGasBean.TokenBean tokenBean = new PostLamb2TbbGasBean.TokenBean();
                tokenBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(lamb),new BigDecimal(1000000)).toString()));
                tokenBean.setDenom(Utils.getSpUtils().getString("token"));

                postLamb2TbbGasBean.setToken(tokenBean);

                showProgress();
                presenter.getGasData(MyApplication.getInstance().getUserBean().getAddress(), postLamb2TbbGasBean);

            }
        }).getInfo();

    }

    @Override
    public void getGasDataHttp(GasBean gasBean) {
        hideProgress();
        PasswordDialog mPasswordDialog = new PasswordDialog(getActivity(), new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                // TODO: 2019/12/4 暂时忽略密码校验
                showProgress();

                Lamb2TbbMsgBean msgsBean = new Lamb2TbbMsgBean();
                Lamb2TbbMsgBean.ValueBean valueBean = new Lamb2TbbMsgBean.ValueBean();
                msgsBean.setType(Constants.SignType.LAMB2TBB);

                valueBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());

                Lamb2TbbMsgBean.ValueBean.AssetBean assetBean = new Lamb2TbbMsgBean.ValueBean.AssetBean();
                assetBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(tbb),new BigDecimal(1000000)).toString()));
                assetBean.setDenom("utbb");

                valueBean.setAsset(assetBean);

                Lamb2TbbMsgBean.ValueBean.TokenBean tokenBean = new Lamb2TbbMsgBean.ValueBean.TokenBean();
                tokenBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(lamb),new BigDecimal(1000000)).toString()));
                tokenBean.setDenom(Utils.getSpUtils().getString(Constants.SpInfo.TOKEN));

                valueBean.setToken(tokenBean);
                msgsBean.setValue(valueBean);
                List<Lamb2TbbMsgBean> lamb2TbbMsgBeans = new ArrayList<>();
                lamb2TbbMsgBeans.add(msgsBean);

                new PushDataManger<Lamb2TbbMsgBean>(getActivity(), password, new PushDataManger.Callback() {
                    @Override
                    public void onSuccess(TransactionSuccessBean transactionSuccessBean) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                hideProgress();
                                getActivity().finish();
                            }
                        }, 7000);//延时10S刷新数据
                    }

                    @Override
                    public void onFail(String msg) {
                        hideProgress();
                        toast(msg);
                    }
                },hashMap).push(gasBean.getGas_estimate(), Utils.getSpUtils().getString(Constants.SpInfo.TOKEN), "", lamb2TbbMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        mPasswordDialog.setGas(gasBean.getGas_estimate());
        mPasswordDialog.show();
    }
}
