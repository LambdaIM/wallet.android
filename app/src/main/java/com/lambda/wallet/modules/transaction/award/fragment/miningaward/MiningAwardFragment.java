package com.lambda.wallet.modules.transaction.award.fragment.miningaward;


import android.widget.Button;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.base.LazyLoadFragment;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostMiningAwardGasBean;
import com.lambda.wallet.lambda.bean.msg.MiningAwardMsgBean;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.Utils;
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

/**
 * 挖矿收益
 */
public class MiningAwardFragment extends LazyLoadFragment<MiningAwardView, MiningAwardPresenter> implements MiningAwardView {


    @BindView(R.id.amount)
    TextView mAmount;
    @BindView(R.id.go)
    Button mGo;

    List<MiningAwardMsgBean> miningAwardMsgBeans = new ArrayList<>();
    HashMap<String ,String> hashMap = new HashMap<>();
    @Override
    protected int setContentView() {
        return R.layout.fragment_mining_award;
    }

    @Override
    public MiningAwardPresenter initPresenter() {
        return new MiningAwardPresenter(getActivity());
    }

    @Override
    protected void onFragmentFirstVisible() {
        mAmount.setText(Utils.getSpUtils().getString(Constants.SpInfo.AWARD1));
        presenter.getProducerData(MyApplication.getInstance().getUserBean().getAddress());
    }


    @OnClick(R.id.go)
    public void onClick() {
        if (mAmount.getText().toString().equals("0")) {
            toast(getString(R.string.toast_no_award));
            return;
        }

        PostMiningAwardGasBean postMiningAwardGasBean = new PostMiningAwardGasBean();
        PostMiningAwardGasBean.BaseReqBean baseReqBean = new PostMiningAwardGasBean.BaseReqBean();
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


                postMiningAwardGasBean.setBase_req(baseReqBean);
                postMiningAwardGasBean.setDelegator_address(MyApplication.getInstance().getUserBean().getAddress());


                showProgress();
                presenter.getGasData(MyApplication.getInstance().getUserBean().getAddress(), postMiningAwardGasBean);
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


                new PushDataManger<MiningAwardMsgBean>(getActivity(), password, new PushDataManger.Callback() {
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
                        ConfirmDialog confirmDialog = new ConfirmDialog(getActivity(), new Callback() {
                            @Override
                            public void sure() {

                            }
                        });
                        confirmDialog.setContent(msg);
                        confirmDialog.show();
                    }
                },hashMap).push(gasBean.getGas_estimate(), Utils.getSpUtils().getString(Constants.SpInfo.TOKEN), "", miningAwardMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        String gas = BigDecimalUtil.multiply(new BigDecimal(gasBean.getGas_estimate()), new BigDecimal(1.5)).toString();
        String amount = BigDecimalUtil.multiply(new BigDecimal(Math.round(Double.parseDouble(gas))+""), new BigDecimal(Constants.GAS_PRICE)).toString();
        mPasswordDialog.setGas(Math.round(Double.parseDouble(amount)) + "");
        mPasswordDialog.show();
    }

    @Override
    public void getZhiyaDataHttp(List<ZhiyaProducerBean> zhiyaProducerBeans) {
        int j=0;
        if (zhiyaProducerBeans.size()<=5) {
            j=zhiyaProducerBeans.size();
        }else {
            j=5;
        }
        for (int i = 0; i < j; i++) {
            MiningAwardMsgBean msgsBean = new MiningAwardMsgBean();
            MiningAwardMsgBean.ValueBean valueBean = new MiningAwardMsgBean.ValueBean();

            msgsBean.setType(Constants.SignType.MiningAward);

            valueBean.setDelegator_address(MyApplication.getInstance().getUserBean().getAddress());
            valueBean.setValidator_address(zhiyaProducerBeans.get(i).getValidator_address());
            msgsBean.setValue(valueBean);

            miningAwardMsgBeans.add(msgsBean);
        }
    }


}
