package com.lambda.wallet.modules.transaction.award.fragment.produceraward;


import android.widget.Button;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.base.LazyLoadFragment;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.WalletManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostProducerAwardGasBean;
import com.lambda.wallet.lambda.bean.msg.ProducerAwardMsgBean;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.dialog.passworddialog.PasswordDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 节点收益
 */
public class ProducerAwardFragment extends LazyLoadFragment<ProducerAwardView, ProducerAwardPresenter> implements ProducerAwardView {


    @BindView(R.id.amount)
    TextView mAmount;
    @BindView(R.id.go)
    Button mGo;

    HashMap<String, String> hashMap = new HashMap<>();

    @Override
    protected int setContentView() {
        return R.layout.fragment_producer_award;
    }

    @Override
    public ProducerAwardPresenter initPresenter() {
        return new ProducerAwardPresenter(getActivity());
    }

    @Override
    protected void onFragmentFirstVisible() {
        mAmount.setText(Utils.getSpUtils().getString(Constants.SpInfo.AWARD2));
    }


    @OnClick(R.id.go)
    public void onClick() {
        if (mAmount.getText().toString().equals("0")) {
            toast("暂无收益可提取");
            return;
        }
        PostProducerAwardGasBean postProducerAwardGasBean = new PostProducerAwardGasBean();
        PostProducerAwardGasBean.BaseReqBean baseReqBean = new PostProducerAwardGasBean.BaseReqBean();
        new ChainInfoUtils(new ChainInfoUtils.Callback() {
            @Override
            public void onSuccess(HashMap<String, String> hashMap1) {
                hashMap = hashMap1;
                baseReqBean.setSequence(hashMap.get("sequence"));
                baseReqBean.setAccount_number(hashMap.get("account_number"));
                baseReqBean.setFrom(MyApplication.getInstance().getUserBean().getAddress());
                baseReqBean.setChain_id(hashMap.get("chain_id"));
                baseReqBean.setSimulate(true);
                baseReqBean.setMemo("");


                postProducerAwardGasBean.setBase_req(baseReqBean);
                postProducerAwardGasBean.setValidator_address(WalletManger.getDevAddress(MyApplication.getInstance().getUserBean().getAddress()));


                showProgress();
                presenter.getGasData(MyApplication.getInstance().getUserBean().getAddress(), postProducerAwardGasBean);

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
                ProducerAwardMsgBean msgsBean = new ProducerAwardMsgBean();
                ProducerAwardMsgBean.ValueBean valueBean = new ProducerAwardMsgBean.ValueBean();

                msgsBean.setType(Constants.SignType.ProducerAward);

                valueBean.setValidator_address(WalletManger.getDevAddress(MyApplication.getInstance().getUserBean().getAddress()));
                msgsBean.setValue(valueBean);

                List<ProducerAwardMsgBean> producerAwardMsgBeans = new ArrayList<>();
                producerAwardMsgBeans.add(msgsBean);

                new PushDataManger<ProducerAwardMsgBean>(getActivity(), password, new PushDataManger.Callback() {
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
                }, hashMap).push(gasBean.getGas_estimate(), Utils.getSpUtils().getString(Constants.SpInfo.TOKEN), "", producerAwardMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        mPasswordDialog.setGas(gasBean.getGas_estimate());
        mPasswordDialog.show();
    }
}
