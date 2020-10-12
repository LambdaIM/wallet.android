package com.lambda.wallet.modules.home;


import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseFragment;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.HomeAssetBean;
import com.lambda.wallet.modules.scancode.ScanCodeActivity;
import com.lambda.wallet.modules.transaction.exchange.activity.ExchangeActivity;
import com.lambda.wallet.modules.transaction.makecollection.MakeCollectionActivity;
import com.lambda.wallet.modules.transaction.transfer.TransferActivity;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.AppBlackDefeatHeadView;
import com.lambda.wallet.view.MyScrollview;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;
import com.liaoinstan.springview.widget.SpringView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.ljp.permission.PermissionItem;

/**
 * 首页资产
 */
public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView {


    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.title)
    RelativeLayout mTitle;
    @BindView(R.id.all_money)
    TextView mAllMoney;
    @BindView(R.id.user_all_property)
    TextView mUserAllProperty;
    @BindView(R.id.award)
    TextView mAward;
    @BindView(R.id.ll_transfer)
    LinearLayout mLlTransfer;
    @BindView(R.id.ll_award)
    LinearLayout mLlAward;
    @BindView(R.id.ll_exchange)
    LinearLayout mLlExchange;
    @BindView(R.id.txt)
    TextView mTxt;
    @BindView(R.id.recycle_token)
    RecyclerView mRecycleToken;

    @BindView(R.id.id_txt_assets)
    TextView mAssetsTxt;
    @BindView(R.id.recycle_assets)
    RecyclerView mRecycleAssets;

    @BindView(R.id.scrollView)
    MyScrollview mScrollView;


    @BindView(R.id.spring)
    SpringView mSpring;
    @BindView(R.id.iv_scan)
    ImageView mIvScan;

    BigDecimal allMoney = new BigDecimal(0);
    // 之前的钱包信息
    CommonAdapter mCommonAdapter;

    //资产列表
    CommonAdapter massetAdapter;

    ArrayList<HomeAddressDetailsBean.ValueBean.CoinsBean> mCoinsBeans = new ArrayList<>();
    List<String> tokenList = new ArrayList<>();//用户资产下所拥有的token
    // 链上资产
    List<HomeAssetBean> mHomeAsset = new ArrayList<>();


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }


    @Override
    public HomePresenter initPresenter() {
        return new HomePresenter(getActivity());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(false).statusBarColor(R.color.transparent).titleBar(mTitle).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.fitsSystemWindows(false).statusBarColor(R.color.transparent).titleBar(mTitle).statusBarDarkFont(true, 0.2f).init();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        mAddress.setText(StringUtils.lambdaAddress(MyApplication.getInstance().getUserBean().getAddress()));
        //系统刷新
        mSpring.setHeader(new AppBlackDefeatHeadView(getContext()));
        mSpring.setGive(SpringView.Give.TOP);
        mSpring.setType(SpringView.Type.FOLLOW);
        mSpring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                presenter.getAddressDetailsData(MyApplication.getInstance().getUserBean().getAddress());
                presenter.getServerAssetsFundList();
            }

            @Override
            public void onLoadmore() {
                mSpring.onFinishFreshAndLoad();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        //链上资产
        presenter.getServerAssetsFundList();
    }

    @Override
    protected void initData() {
        //默认为0；
        Utils.getSpUtils().put(Constants.SpInfo.AWARD1, "0");
        Utils.getSpUtils().put(Constants.SpInfo.AWARD2, "0");
    }

    @Override
    public void initEvent() {
        mIvScan.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
                permissonItems.add(new PermissionItem(Manifest.permission.CAMERA, getString(R.string.camer), R.drawable.permission_ic_camera));
                if (Utils.getPermissions(permissonItems, getString(R.string.camer_title))) {
                    if (mCoinsBeans.size() != 0) {
                        Bundle bundle1 = new Bundle();
                        bundle1.putParcelableArrayList("token", mCoinsBeans);
                        ActivityUtils.next(getActivity(), ScanCodeActivity.class, bundle1);
                    } else {
                        toast(getString(R.string.chain_error));
                    }
                }
            }
        });
    }


    @OnClick({R.id.address, R.id.ll_transfer, R.id.ll_award, R.id.ll_exchange})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.address:
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(MyApplication.getInstance().getUserBean().getAddress());
                toast(getString(R.string.copy));
                break;
            case R.id.ll_transfer:
                if (mCoinsBeans.size() != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("token", mCoinsBeans);
                    bundle.putString("from", "home");
                    ActivityUtils.next(getActivity(), TransferActivity.class, bundle);
                } else {
                    toast(getString(R.string.chain_error));
                }
                break;
            case R.id.ll_award:
                if (mCoinsBeans.size() != 0) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelableArrayList("token", mCoinsBeans);
                    ActivityUtils.next(getActivity(), MakeCollectionActivity.class, bundle1);
                } else {
                    toast(getString(R.string.chain_error));
                }
                break;
            case R.id.ll_exchange:
                ActivityUtils.next(getActivity(), ExchangeActivity.class);
                break;
        }
    }

    @Override
    public void getAddressDetailsDataHttp(HomeAddressDetailsBean homeAddressDetailsBean) {
        mSpring.onFinishFreshAndLoad();
        mCoinsBeans = new ArrayList<>();
        tokenList = new ArrayList<>();
        allMoney = new BigDecimal(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecycleToken.setLayoutManager(layoutManager);
        mCommonAdapter = AdapterManger.getCoinAdapter(getActivity(), mCoinsBeans);
        mRecycleToken.setAdapter(mCommonAdapter);


        if (homeAddressDetailsBean.getValue().getCoins() == null) {
            List<HomeAddressDetailsBean.ValueBean.CoinsBean> coinsBeanList = new ArrayList<>();
            HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean = new HomeAddressDetailsBean.ValueBean.CoinsBean();
            coinsBean.setAmount("0");
            coinsBean.setDenom("ulamb");
            HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean1 = new HomeAddressDetailsBean.ValueBean.CoinsBean();
            coinsBean1.setAmount("0");
            coinsBean1.setDenom("utbb");
            coinsBeanList.add(coinsBean);
            coinsBeanList.add(coinsBean1);
            homeAddressDetailsBean.getValue().setCoins(coinsBeanList);

        }
        for (int i = 0; i < homeAddressDetailsBean.getValue().getCoins().size(); i++) {
            homeAddressDetailsBean.getValue().getCoins().get(i).setAmount(BigDecimalUtil.toLambdaBigDecimal(homeAddressDetailsBean.getValue().getCoins().get(i).getAmount()).toString());

            mCoinsBeans.add(homeAddressDetailsBean.getValue().getCoins().get(i));
            tokenList.add(homeAddressDetailsBean.getValue().getCoins().get(i).getDenom().toLowerCase());
        }
        if (!tokenList.contains("ulamb") && tokenList.contains("utbb")) {
            HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean1 = new HomeAddressDetailsBean.ValueBean.CoinsBean();
            coinsBean1.setAmount("0");
            coinsBean1.setDenom("ulamb");
            mCoinsBeans.add(0, coinsBean1);
        } else if (!tokenList.contains("ulamb") && !tokenList.contains("utbb")) {
            HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean1 = new HomeAddressDetailsBean.ValueBean.CoinsBean();
            coinsBean1.setAmount("0");
            coinsBean1.setDenom("ulamb");
            mCoinsBeans.add(0, coinsBean1);
            HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean = new HomeAddressDetailsBean.ValueBean.CoinsBean();
            coinsBean.setAmount("0");
            coinsBean.setDenom("utbb");
            mCoinsBeans.add(1, coinsBean);
        } else if (tokenList.contains("ulamb") && !tokenList.contains("utbb")) {
            HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean = new HomeAddressDetailsBean.ValueBean.CoinsBean();
            coinsBean.setAmount("0");
            coinsBean.setDenom("utbb");
            mCoinsBeans.add(1, coinsBean);
        }


        if (this.mHomeAsset.size() > 0) {
            for (int i = 0; i < this.mHomeAsset.size(); i++) {
                HomeAssetBean.AssetBean assetBean = this.mHomeAsset.get(i).getAsset();
                HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean = new HomeAddressDetailsBean.ValueBean.CoinsBean();
                coinsBean.setAmount("0");
                coinsBean.setDenom(assetBean.getDenom());
                coinsBean.setType(this.mHomeAsset.get(i).getStatus());
                if (!isHasCoin(coinsBean)){
                    mCoinsBeans.add(coinsBean);
                }
            }
        }


        for (int i = 0; i < mCoinsBeans.size(); i++) {
            if (mCoinsBeans.get(i).getDenom().toLowerCase().equals("ulamb")) {
                Utils.getSpUtils().put(Constants.SpInfo.LAMB, mCoinsBeans.get(i).getAmount());
                Utils.getSpUtils().put(Constants.SpInfo.TOKEN, mCoinsBeans.get(i).getDenom());
                allMoney = BigDecimalUtil.add(allMoney, new BigDecimal(mCoinsBeans.get(i).getAmount()));
            }
            if (mCoinsBeans.get(i).getDenom().toLowerCase().equals("utbb")) {
                Utils.getSpUtils().put(Constants.SpInfo.TBB, mCoinsBeans.get(i).getAmount());
            }
        }


        mUserAllProperty.setText(StringUtils.addComma(allMoney.toString()));
        mCommonAdapter.notifyDataSetChanged();

    }

    /****
     * 检测是不是存在该币
     * @param coinsBean
     */
    private boolean isHasCoin(HomeAddressDetailsBean.ValueBean.CoinsBean coinsBean) {

        boolean flag = false;
        for (int a = 0; a < mCoinsBeans.size(); a++) {
            HomeAddressDetailsBean.ValueBean.CoinsBean tempCoin = mCoinsBeans.get(a);
            if (coinsBean.getDenom().equalsIgnoreCase(tempCoin.getDenom())) {
                flag = true;
                mCoinsBeans.get(a).setType(coinsBean.getType());
            }
        }
        return flag;
    }

    @Override
    public void getServerAssetsFundList(List<HomeAssetBean> homeAssetBean) {
        mSpring.onFinishFreshAndLoad();
        mCoinsBeans = new ArrayList<>();
        tokenList = new ArrayList<>();
        allMoney = new BigDecimal(0);


        // 余额
        presenter.getAddressDetailsData(MyApplication.getInstance().getUserBean().getAddress());

        this.mHomeAsset = homeAssetBean;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        mRecycleAssets.setLayoutManager(layoutManager);
        massetAdapter = AdapterManger.getAssetsAdapter(getActivity(), homeAssetBean);
        mRecycleAssets.setAdapter(massetAdapter);

        massetAdapter.notifyDataSetChanged();

    }


}
