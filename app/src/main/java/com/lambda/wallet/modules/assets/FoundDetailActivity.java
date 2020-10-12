package com.lambda.wallet.modules.assets;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.AppManager;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.FundInfoMineBean;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.HomeAssetBean;
import com.lambda.wallet.bean.PreMiningDetail;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostPreMiningGasbean;
import com.lambda.wallet.lambda.bean.gas.PostTransferGasBean;
import com.lambda.wallet.lambda.bean.msg.PreMiningMsgBean;
import com.lambda.wallet.lambda.bean.msg.ZhiYaMsgBean;
import com.lambda.wallet.modules.me.setting.SettingActivity;
import com.lambda.wallet.modules.me.setting.changechain.ChangeChainActivity;
import com.lambda.wallet.modules.producer.ProducerDetailsPresenter;
import com.lambda.wallet.modules.wallet.login.LoginActivity;
import com.lambda.wallet.modules.zhiyamanger.cancelzhiya.CancelZhiYaActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.DateUtils;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;
import com.lambda.wallet.view.dialog.confimdialog.Callback;
import com.lambda.wallet.view.dialog.confimdialog.ConfirmDialog;
import com.lambda.wallet.view.dialog.passworddialog.PasswordDialog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

/***
 * 资产详情
 */
public class FoundDetailActivity extends BaseAcitvity<FoundDetailView, FoundDetailPresenter> implements FoundDetailView {

    @BindView(R.id.asset_fullname)//资产全称
            TextView mAssetFullname;
    @BindView(R.id.additional_issue_type)//增发类型
            TextView mAssetAdditional;
    @BindView(R.id.asset_state)//状态
            TextView mAssetState;
    @BindView(R.id.asset_endtime)//结束时间
            TextView mAssetEndTime;
    @BindView(R.id.asset_initial_circulationc)//初始发行
            TextView mInitialCir;
    @BindView(R.id.asset_total_circulationc)//总发行
            TextView mtotalCir;

    @BindView(R.id.all_send)//总发行
            RelativeLayout mtotalRelay;

    @BindView(R.id.all_send_line)//总发行
            View mtotalRelayLine;
    @BindView(R.id.asset_total_participation)// 总参与额度
            TextView mtotalParticipation;
    @BindView(R.id.asset_total_completetion)// 完成度
            TextView mtotalCompletion;
    @BindView(R.id.asset_num_pre)// 预挖矿
            TextView assetPreNum;

    @BindView(R.id.title_expect_mining)// 预计挖矿
            TextView titlePreName;


    //参与预挖title 如果参与挖矿
    @BindView(R.id.title_name_kind_input)// 输入
            TextView titlePre;
    //参与预挖title
    @BindView(R.id.amount)// 输入
            ClearEditText amountEdit;
    //参与的按钮
    @BindView(R.id.go)// 输入
            Button goBtn;
    //预计挖矿
    @BindView(R.id.id_expect_mining)
    TextView expectMining;

    @BindView(R.id.top_linear)
    LinearLayout topLinear;

    //我的参与额度
    @BindView(R.id.id_my_participation)
    TextView myPartticipation;

    // 提示的内容
    @BindView(R.id.id_show_concern)
    TextView tipsContent;

    // 相对布局
    @BindView(R.id.relay_pre_miming)
    RelativeLayout relayPre;


    HashMap<String, String> hashMap = new HashMap<>();


    private HomeAssetBean homeAssetBean;//质押的资产

    private String shortStr;// 质押的单位

    private GasBean gasBean;//

    @Override
    protected int getLayoutId() {
        return R.layout.activity_asset_detail;
    }

    @Override
    public FoundDetailPresenter initPresenter() {
        return new FoundDetailPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(StringUtils.lambdaToken(getIntent().getStringExtra("address")) + getString(R.string.title_asset_title));
    }


    @Override
    protected void initData() {
        presenter.getAssetDetailsData(getIntent().getStringExtra("address"));
        presenter.getFundInfo(getIntent().getStringExtra("address"));

    }

    @Override
    public void initEvent() {

    }

    /***
     *  获取gas
     */
    private String mGetGas;

    @OnClick({R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go:
                //TODO 调用挖
                PostPreMiningGasbean postTransferGasBean = new PostPreMiningGasbean();
                PostPreMiningGasbean.BaseReqBean baseReqBean = new PostPreMiningGasbean.BaseReqBean();
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

                        PostPreMiningGasbean.AmountBean amountBean = new PostPreMiningGasbean.AmountBean();
                        amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(amountEdit.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                        amountBean.setDenom(shortStr);

                        postTransferGasBean.setBase_req(baseReqBean);
                        postTransferGasBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());
                        postTransferGasBean.setAsset(homeAssetBean.getAsset().getDenom());
                        postTransferGasBean.setToken(amountBean);

                        showProgress();
                        presenter.getPreMiningGas(postTransferGasBean);


                    }
                }).getInfo();

                break;
            default:
                break;
        }
    }


    @Override
    public void getAssetDetailsDataHttp(HomeAssetBean assetBean) {
        homeAssetBean = assetBean;
        //资产全称
        mAssetFullname.setText(assetBean.getName());
        //增发类型
        mAssetAdditional.setText(getAdditionalType(assetBean.getMint_type()) + "");
        //状态
        mAssetState.setText(getAssetState(assetBean.getStatus()) + "");

        mInitialCir.setText(StringUtils.addComma(StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(assetBean.getAsset().getAmount()), new BigDecimal(1000000), 1).toString())).toString() + StringUtils.lambdaToken(assetBean.getAsset().getDenom()));


        //总发行
        mtotalCir.setText(StringUtils.addComma(StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(assetBean.getTotal_supply().getAmount()), new BigDecimal(1000000), 1).toString())).toString() + StringUtils.lambdaToken(assetBean.getTotal_supply().getDenom()));

        // 完成度

        mtotalRelay.setVisibility(View.GONE);
        mtotalRelayLine.setVisibility(View.GONE);
        if ("0".equalsIgnoreCase(assetBean.getStatus())) {
            // 预挖矿
            titlePre.setVisibility(View.VISIBLE);
            amountEdit.setVisibility(View.VISIBLE);
            goBtn.setVisibility(View.VISIBLE);
            relayPre.setVisibility(View.GONE);
            tipsContent.setVisibility(View.VISIBLE);
            titlePreName.setVisibility(View.GONE);
            expectMining.setVisibility(View.GONE);
            tipsContent.setText(getResources().getString(R.string.pre_rule_pledge_attention));
        } else {
            //
            titlePre.setVisibility(View.GONE);
            amountEdit.setVisibility(View.GONE);
            goBtn.setVisibility(View.GONE);
            expectMining.setVisibility(View.VISIBLE);
            relayPre.setVisibility(View.VISIBLE);
            tipsContent.setVisibility(View.VISIBLE);
            titlePreName.setVisibility(View.VISIBLE);
            tipsContent.setText(getResources().getString(R.string.finish_rule_pledge_attention));
        }
    }

    @Override
    public void getFundDetailsDataHttp(PreMiningDetail assetBean) {

        if (assetBean != null) {
            //总发行
//            expectMining.setText(StringUtils.addComma(StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(assetBean.getAsset().getAmount()), new BigDecimal(1000000), 1).toString())).toString() + StringUtils.lambdaToken(assetBean.getAsset().getDenom()));

            topLinear.setVisibility(View.VISIBLE);
            titlePre.setText(getResources().getString(R.string.token) + "(" + StringUtils.lambdaToken(assetBean.getFund_asset().getDenom() + ")"));
            shortStr = assetBean.getFund_asset().getDenom();
            //初始发行
            assetPreNum.setText(StringUtils.addComma(StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(assetBean.getAsset().getAmount()), new BigDecimal(1000000), 1).toString())).toString() + StringUtils.lambdaToken(assetBean.getAsset().getDenom()));


            // 总参与额度
            mtotalParticipation.setText(StringUtils.addComma(StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(assetBean.getFund_asset().getAmount()), new BigDecimal(1000000), 1).toString())).toString() + StringUtils.lambdaToken(assetBean.getFund_asset().getDenom()));


            double mutiData = 100 * Double.parseDouble(assetBean.getAmount()) / Double.parseDouble(assetBean.getFund_asset().getAmount());


            mtotalCompletion.setText(Utils.limitPercent(3, mutiData, RoundingMode.DOWN));


            try {
                mAssetEndTime.setText(DateUtils.currentDate(assetBean.getEnd_time()));
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void getFundInfoMineDataHttp(FundInfoMineBean assetBean) {
        //预计挖矿
        if (assetBean != null) {
            topLinear.setVisibility(View.VISIBLE);
            relayPre.setVisibility(View.VISIBLE);
            expectMining.setVisibility(View.VISIBLE);
            myPartticipation.setVisibility(View.VISIBLE);
            titlePreName.setVisibility(View.VISIBLE);
            topLinear.setVisibility(View.VISIBLE);

            // 预计挖矿

            expectMining.setText(StringUtils.addComma(StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(assetBean.getStake().getAmount()), new BigDecimal(1000000), 6).toString())).toString() + StringUtils.lambdaToken(assetBean.getStake().getDenom()));


            myPartticipation.setText(StringUtils.addComma(StringUtils.deletzero(BigDecimalUtil.divide(new BigDecimal(assetBean.getInvest().getAmount()), new BigDecimal(1000000), 1).toString())).toString() + StringUtils.lambdaToken(assetBean.getInvest().getDenom()));


        }
    }

    @Override
    public void getMineGasDataHttp(GasBean gasBean) {
        this.gasBean = gasBean;

        hideProgress();

        PasswordDialog mPasswordDialog = new PasswordDialog(FoundDetailActivity.this, new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                showProgress();

                PreMiningMsgBean msgsBean = new PreMiningMsgBean();

                PreMiningMsgBean.ValueBean valueBean = new PreMiningMsgBean.ValueBean();
                msgsBean.setType(Constants.SignType.TX_MSG_ASSETINVEST);
                valueBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());
                valueBean.setAsset(homeAssetBean.getAsset().getDenom());

                PreMiningMsgBean.ValueBean.AmountBean amountBean = new PreMiningMsgBean.ValueBean.AmountBean();
                amountBean.setAmount(StringUtils.deletzero(BigDecimalUtil.multiply(new BigDecimal(amountEdit.getText().toString().trim()), new BigDecimal(1000000)).toString()));
                amountBean.setDenom(shortStr);

                valueBean.setToken(amountBean);


                msgsBean.setValue(valueBean);

                List<PreMiningMsgBean> preMiningMsgBeans = new ArrayList<>();
                preMiningMsgBeans.add(msgsBean);

                new PushDataManger<PreMiningMsgBean>(FoundDetailActivity.this, password, new PushDataManger.Callback() {
                    @Override
                    public void onSuccess(TransactionSuccessBean transactionSuccessBean) {

                        presenter.getAssetDetailsData(getIntent().getStringExtra("address"));
                        presenter.getFundInfo(getIntent().getStringExtra("address"));

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                hideProgress();
                            }
                        }, 7000);//延时10S刷新数据
                    }

                    @Override
                    public void onFail(String msg) {
                        hideProgress();
                        ConfirmDialog confirmDialog = new ConfirmDialog(FoundDetailActivity.this, new Callback() {
                            @Override
                            public void sure() {

                            }
                        });
                        confirmDialog.setContent(msg);
                        confirmDialog.show();
                    }
                }, hashMap).push(gasBean.getGas_estimate(), Utils.getSpUtils().getString(Constants.SpInfo.TOKEN), "", preMiningMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        String gas = BigDecimalUtil.multiply(new BigDecimal(gasBean.getGas_estimate()), new BigDecimal(1.5)).toString();
        String amount = BigDecimalUtil.multiply(new BigDecimal(Math.round(Double.parseDouble(gas)) + ""), new BigDecimal(Constants.GAS_PRICE)).toString();
        mPasswordDialog.setGas(Math.round(Double.parseDouble(amount)) + "");
        mPasswordDialog.show();
    }


    @Override
    public void getFundInfoMineDataHttpError() {
        expectMining.setVisibility(View.GONE);
        relayPre.setVisibility(View.GONE);
        titlePreName.setVisibility(View.GONE);
        topLinear.setVisibility(View.GONE);
    }


    /***
     * 获取增发类型
     * @return
     */
    private String getAdditionalType(String type) {
        String res = "";
        switch (type) {
            case "1":
                res = getResources().getString(R.string.str_no_additional_issuance);
                mAssetAdditional.setTextColor(getResources().getColor(R.color.text_red_line));

                break;
            case "2":
                res = getResources().getString(R.string.str_one_time_additional_issuance);
                mAssetAdditional.setTextColor(getResources().getColor(R.color.title_color));

                break;
            case "3":
                res = getResources().getString(R.string.strmining_additional_issuance);
                mAssetAdditional.setTextColor(getResources().getColor(R.color.dodgerblue));


                break;
        }
        return res;
    }

    /***
     * 获取增发类型
     * @return
     */
    private String getAssetState(String type) {
        presenter.getFundInfoMIME(getIntent().getStringExtra("address"));

        String res = "";
        switch (type) {
            case "0":
                res = getResources().getString(R.string.title_btn_pre_mining);
                presenter.getFundInfo(getIntent().getStringExtra("address"));
                mAssetState.setTextColor(getResources().getColor(R.color.dodgerblue));
                break;
            case "1":
                res = getResources().getString(R.string.str_pre_mining_completed);

                mAssetState.setTextColor(getResources().getColor(R.color.limegreen));

                break;
            case "2":
                res = getResources().getString(R.string.str_authorized_additional_issuance);
                mAssetState.setTextColor(getResources().getColor(R.color.gray_color));

                break;
            case "3":
                res = getResources().getString(R.string.str_additional_issuance_completed);

                mtotalRelay.setVisibility(View.VISIBLE);
                mtotalRelayLine.setVisibility(View.VISIBLE);
                mAssetState.setTextColor(getResources().getColor(R.color.forestgreen));

                break;
        }
        return res;
    }
}
