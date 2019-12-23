package com.lambda.wallet.modules.me;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseFragment;
import com.lambda.wallet.modules.me.setting.SettingActivity;
import com.lambda.wallet.modules.proposal.ProposalActivity;
import com.lambda.wallet.modules.transaction.history.HistoryTransferActivity;
import com.lambda.wallet.modules.wallet.walletmanger.WalletMangerActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */
public class MeFragment extends BaseFragment<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.rel)
    RelativeLayout mRel;
    @BindView(R.id.wallet_img)
    ImageView mWalletImg;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.go_my_wallet)
    TextView mGoMyWallet;
    @BindView(R.id.go_transfer_history)
    TextView mGoTransferHistory;
    @BindView(R.id.go_proposal)
    TextView mGoProposal;
    @BindView(R.id.go_settings)
    TextView mGoSettings;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(false).statusBarColor(R.color.transparent).titleBar(mRel).statusBarDarkFont(false, 0f).init();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null) {
            mImmersionBar.fitsSystemWindows(false).statusBarColor(R.color.transparent).titleBar(mRel).statusBarDarkFont(false, 0f).init();
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mAddress.setText(MyApplication.getInstance().getUserBean().getName());
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.go_my_wallet, R.id.go_transfer_history, R.id.go_proposal, R.id.go_settings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_my_wallet:
                ActivityUtils.next(getActivity(), WalletMangerActivity.class);
                break;
            case R.id.go_transfer_history:
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                ActivityUtils.next(getActivity(), HistoryTransferActivity.class, bundle);
                break;
            case R.id.go_proposal:
                ActivityUtils.next(getActivity(), ProposalActivity.class);
                break;
            case R.id.go_settings:
                ActivityUtils.next(getActivity(), SettingActivity.class);
                break;
        }
    }
}
