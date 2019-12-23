package com.lambda.wallet.modules.mining.storemarket.buystore;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.GasBean;
import com.lambda.wallet.bean.MarketParamsBean;
import com.lambda.wallet.bean.MarketSellListBean;
import com.lambda.wallet.bean.MarketsBean;
import com.lambda.wallet.lambda.ChainInfoUtils;
import com.lambda.wallet.lambda.PushDataManger;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.gas.PostBuyStoreGasBean;
import com.lambda.wallet.lambda.bean.msg.BuyStoreMsgBean;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.DateUtils;
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

// TODO: 2019/12/2 购买空间
public class BuyStoreActivity extends BaseAcitvity<BuyStoreView, BuyStorePresenter> implements BuyStoreView {


    @BindView(R.id.space)
    ClearEditText mSpace;
    @BindView(R.id.time)
    ClearEditText mTime;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.minBuySize)
    TextView mMinBuySize;
    @BindView(R.id.minDuration)
    TextView mMinDuration;
    @BindView(R.id.maxDuration)
    TextView mMaxDuration;
    @BindView(R.id.rate)
    TextView mRate;
    @BindView(R.id.sellSize)
    TextView mSellSize;
    @BindView(R.id.unUseSize)
    TextView mUnUseSize;
    @BindView(R.id.price)
    TextView mPrice;
    @BindView(R.id.pay_token)
    TextView mPayToken;
    @BindView(R.id.sure_pay_token)
    TextView mSurePayToken;
    @BindView(R.id.go)
    TextView mGo;

    MarketSellListBean mMarketSellListBean = new MarketSellListBean();

    BigDecimal price = new BigDecimal(0);
    BigDecimal payToken = new BigDecimal(0);

    int type = 0;//0一键购买1自选交易

    HashMap<String ,String> hashMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_store;
    }

    @Override
    public BuyStorePresenter initPresenter() {
        return new BuyStorePresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.buy_space));
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            mMarketSellListBean = getIntent().getParcelableExtra("sellInfo");
            mAddress.setText(StringUtils.lambdaAddress(mMarketSellListBean.getAddress()));
            mMinBuySize.setText(mMarketSellListBean.getMinBuySize() + "GB");
            mMinDuration.setText(DateUtils.mstimeStamp2Month(mMarketSellListBean.getMinDuration()) + getString(R.string.month));
            mMaxDuration.setText(DateUtils.mstimeStamp2Month(mMarketSellListBean.getMaxDuration()) + getString(R.string.month));
            mRate.setText(StringUtils.deletzero(mMarketSellListBean.getRate()));
            mSellSize.setText(mMarketSellListBean.getSellSize() + "GB");
            mUnUseSize.setText(mMarketSellListBean.getUnUseSize() + "GB");
            mPrice.setText(BigDecimalUtil.toLambdaBigDecimal(mMarketSellListBean.getPrice()).toString() + " LAMB/GB/month");
            price = new BigDecimal(mMarketSellListBean.getPrice());
        } else {
            showProgress();
            mSpace.setText(getIntent().getStringExtra("space"));
            mTime.setText(getIntent().getStringExtra("time"));
            presenter.getMarketsData();
        }
    }

    @Override
    public void initEvent() {
        mSpace.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!TextUtils.isEmpty(mTime.getText().toString().trim())) {
                        payToken = BigDecimalUtil.multiply(BigDecimalUtil.multiply(price, new BigDecimal(mSpace.getText().toString().trim())), new BigDecimal(mTime.getText().toString().trim()));
                        mPayToken.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(payToken.toString()).toString()) + "LAMB");
                        mSurePayToken.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(payToken.toString()).toString()) + "LAMB");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    payToken = new BigDecimal(0);
                    mPayToken.setText("0LAMB");
                    mSurePayToken.setText("0LAMB");
                }
            }
        });
        mTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!TextUtils.isEmpty(mSpace.getText().toString().trim())) {
                        payToken = BigDecimalUtil.multiply(BigDecimalUtil.multiply(price, new BigDecimal(mTime.getText().toString().trim())), new BigDecimal(mSpace.getText().toString().trim()));
                        mPayToken.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(payToken.toString()).toString()) + "LAMB");
                        mSurePayToken.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(payToken.toString()).toString()) + "LAMB");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    payToken = new BigDecimal(0);
                    mPayToken.setText("0");
                    mSurePayToken.setText("0");
                }
            }
        });
    }


    @OnClick(R.id.go)
    public void onClick() {
        if (TextUtils.isEmpty(mSpace.getText().toString().trim())) {
            toast(getString(R.string.toast_no_space));
            return;
        }
        if (TextUtils.isEmpty(mTime.getText().toString().trim())) {
            toast(getString(R.string.toast_no_time));
            return;
        }
        PostBuyStoreGasBean postBuyStoreGasBean = new PostBuyStoreGasBean();
        PostBuyStoreGasBean.BaseReqBean baseReqBean = new PostBuyStoreGasBean.BaseReqBean();
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

                postBuyStoreGasBean.setBase_req(baseReqBean);
                postBuyStoreGasBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());
                postBuyStoreGasBean.setDuration(DateUtils.month2MstimeStamp(mTime.getText().toString().trim()));
                postBuyStoreGasBean.setMarketName(Constants.MARKETNAME);
                postBuyStoreGasBean.setSellOrderId(type == 1 ? mMarketSellListBean.getOrderId() : "[do-not-input-value]");
                postBuyStoreGasBean.setSize(mSpace.getText().toString());

                showProgress();
                presenter.getGasData(postBuyStoreGasBean);
            }
        }).getInfo();



    }

    @Override
    public void getGasDataHttp(GasBean gasBean) {
        hideProgress();
        PasswordDialog mPasswordDialog = new PasswordDialog(BuyStoreActivity.this, new com.lambda.wallet.view.dialog.passworddialog.PasswordCallback() {
            @Override
            public void sure(String password) {
                showProgress();

                BuyStoreMsgBean msgsBean = new BuyStoreMsgBean();
                BuyStoreMsgBean.ValueBean valueBean = new BuyStoreMsgBean.ValueBean();
                msgsBean.setType(Constants.SignType.BUYSTORE);

                valueBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());
                valueBean.setDuration(DateUtils.month2MstimeStamp(mTime.getText().toString().trim()));
                valueBean.setMarketName(Constants.MARKETNAME);
                valueBean.setSellOrderId(type == 1 ? mMarketSellListBean.getOrderId() : "[do-not-input-value]");
                valueBean.setSize(mSpace.getText().toString());

                msgsBean.setValue(valueBean);

                List<BuyStoreMsgBean> buyStoreMsgBeans = new ArrayList<>();
                buyStoreMsgBeans.add(msgsBean);

                new PushDataManger<BuyStoreMsgBean>(BuyStoreActivity.this, password, new PushDataManger.Callback() {
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
                },hashMap).push(gasBean.getGas_estimate(), Utils.getSpUtils().getString(Constants.SpInfo.TOKEN), "", buyStoreMsgBeans);
            }
        });
        mPasswordDialog.setCancelable(false);
        mPasswordDialog.setGas(gasBean.getGas_estimate());
        mPasswordDialog.show();
    }

    @Override
    public void getMarketsDataHttp(List<MarketsBean> marketsBeans) {
        for (int i = 0; i < marketsBeans.size(); i++) {
            if (marketsBeans.get(i).getName().equals("LambdaMarket")) {
                mAddress.setText(StringUtils.lambdaAddress(marketsBeans.get(i).getMarketAddress()));
                presenter.getMarketsParamsData();
            }
        }
    }

    @Override
    public void getMarketsParamsDataHttp(MarketParamsBean marketParamsBean) {
        hideProgress();

        mMinBuySize.setText(marketParamsBean.getOrder_min_buy_size() + "GB");
        mMinDuration.setText(DateUtils.mstimeStamp2Month(marketParamsBean.getOrder_min_buy_duration()) + getString(R.string.month));
        mMaxDuration.setText(DateUtils.mstimeStamp2Month(marketParamsBean.getOrder_max_buy_duration()) + getString(R.string.month));
        mRate.setText(StringUtils.deletzero(marketParamsBean.getOrder_premium_rate()));
        mSellSize.setText("----");
        mUnUseSize.setText("----");
        mPrice.setText(BigDecimalUtil.toLambdaBigDecimal(marketParamsBean.getOrder_normal_price()).toString() + " LAMB/GB/month");
        price = new BigDecimal(marketParamsBean.getOrder_normal_price());

        if (!TextUtils.isEmpty(mSpace.getText().toString().trim()) && !TextUtils.isEmpty(mTime.getText().toString().trim())) {
            payToken = BigDecimalUtil.multiply(BigDecimalUtil.multiply(price, new BigDecimal(mTime.getText().toString().trim())), new BigDecimal(mSpace.getText().toString().trim()));
            mPayToken.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(payToken.toString()).toString()) + "LAMB");
            mSurePayToken.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(payToken.toString()).toString()) + "LAMB");

        }

    }
}
