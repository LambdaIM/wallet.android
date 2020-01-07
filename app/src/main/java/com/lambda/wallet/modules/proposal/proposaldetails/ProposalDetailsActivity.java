package com.lambda.wallet.modules.proposal.proposaldetails;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.MyYajinBean;
import com.lambda.wallet.bean.proposal.BurnCoinsProposalBean;
import com.lambda.wallet.bean.proposal.CommunityPoolSpendProposalBean;
import com.lambda.wallet.bean.proposal.MyTouPiaoBean;
import com.lambda.wallet.bean.proposal.ParameterChangeProposalBean;
import com.lambda.wallet.bean.proposal.ProposalListBean;
import com.lambda.wallet.bean.proposal.ProposalParamBean;
import com.lambda.wallet.bean.proposal.SoftwareUpgradeProposalBean;
import com.lambda.wallet.bean.proposal.TextProposalBean;
import com.lambda.wallet.bean.proposal.TouPiaoBean;
import com.lambda.wallet.modules.proposal.cashpledge.CashPledgeActivity;
import com.lambda.wallet.modules.proposal.toupiao.TouPiaoActivity;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.DateUtils;
import com.lambda.wallet.util.JsonUtil;
import com.lambda.wallet.util.StringUtils;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;

public class ProposalDetailsActivity extends BaseAcitvity<ProposalDetailsView, ProposalDetailsPresenter> implements ProposalDetailsView {


    @BindView(R.id.proposal_title)
    TextView mProposalTitle;
    @BindView(R.id.status)
    TextView mStatus;
    @BindView(R.id.type)
    TextView mType;
    @BindView(R.id.id)
    TextView mId;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.yajin_max_time)
    TextView mYajinMaxTime;
    @BindView(R.id.need_yajin)
    TextView mNeedYajin;
    @BindView(R.id.have_yajin)
    TextView mHaveYajin;
    @BindView(R.id.my_yajin)
    TextView mMyYajin;
    @BindView(R.id.desc)
    TextView mDesc;
    @BindView(R.id.go)
    TextView mGo;
    @BindView(R.id.rel)
    RelativeLayout mRel;
    @BindView(R.id.ll_burn)
    LinearLayout mLLBurn;
    @BindView(R.id.burn_amount)
    TextView mBurn_amount;
    @BindView(R.id.ll_pool_spend)
    LinearLayout mLl_pool_spend;
    @BindView(R.id.pool_spend_amount)
    TextView mPool_spend_amount;
    @BindView(R.id.ll_soft_update)
    LinearLayout mLl_soft_update;
    @BindView(R.id.version)
    TextView mVersion;
    @BindView(R.id.switch_height)
    TextView mSwitch_height;
    @BindView(R.id.software)
    TextView mSoftware;
    @BindView(R.id.ll_param_change)
    LinearLayout mLl_param_change;
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.my_toupiao)
    TextView mMyToupiao;
    @BindView(R.id.ll_my_toupiao)
    LinearLayout mLlMyToupiao;
    @BindView(R.id.yes)
    TextView mYes;
    @BindView(R.id.no)
    TextView mNo;
    @BindView(R.id.abstain)
    TextView mAbstain;
    @BindView(R.id.no_with_veto)
    TextView mNoWithVeto;
    @BindView(R.id.ll_toupiao)
    LinearLayout mLlToupiao;
    @BindView(R.id.one)
    TextView mOne;
    @BindView(R.id.two)
    TextView mTwo;
    @BindView(R.id.total)
    TextView mTotal;

    ProposalListBean mProposalListBean = new ProposalListBean();
    Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_proposal_details;
    }

    @Override
    public ProposalDetailsPresenter initPresenter() {
        return new ProposalDetailsPresenter(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle("提案详情");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgress();
        presenter.getProposalYajinData();
    }

    @Override
    public void getProposalDetailsDataHttp(ProposalListBean proposalListBean) {

        mProposalListBean = proposalListBean;

        if (mProposalListBean.getProposal_status().equals("Rejected")) {
            mStatus.setText("未通过");
            mRel.setVisibility(View.GONE);
            mOne.setText("投票开始时间：");
            mTwo.setText("投票结束时间：");
            mTime.setText(DateUtils.GTMToLocal(mProposalListBean.getVoting_start_time()));
            mYajinMaxTime.setText(DateUtils.GTMToLocal(mProposalListBean.getVoting_end_time()));

            mLlMyToupiao.setVisibility(View.VISIBLE);
            mMyToupiao.setText("----");
            mLlToupiao.setVisibility(View.VISIBLE);

        } else if (mProposalListBean.getProposal_status().equals("Passed")) {
            mStatus.setText("通过");
            mRel.setVisibility(View.GONE);
            mOne.setText("投票开始时间：");
            mTwo.setText("投票结束时间：");
            mTime.setText(DateUtils.GTMToLocal(mProposalListBean.getVoting_start_time()));
            mYajinMaxTime.setText(DateUtils.GTMToLocal(mProposalListBean.getVoting_end_time()));

            mLlMyToupiao.setVisibility(View.VISIBLE);
            mMyToupiao.setText("----");
            mLlToupiao.setVisibility(View.VISIBLE);

        } else if (mProposalListBean.getProposal_status().equals("DepositPeriod")) {
            mStatus.setText("押金阶段");
            mRel.setVisibility(View.VISIBLE);
            mGo.setText("存入押金");
            mMyYajin.setText("----");
            mTime.setText(DateUtils.GTMToLocal(mProposalListBean.getSubmit_time()));
            mYajinMaxTime.setText(DateUtils.GTMToLocal(mProposalListBean.getDeposit_end_time()));
        } else if (mProposalListBean.getProposal_status().equals("VotingPeriod")) {
            mStatus.setText("投票阶段");
            mRel.setVisibility(View.VISIBLE);
            mGo.setText("投票");
            mMyYajin.setText("----");
            mLlMyToupiao.setVisibility(View.VISIBLE);
            mMyToupiao.setText("----");
            mLlToupiao.setVisibility(View.VISIBLE);
            mOne.setText("投票开始时间：");
            mTwo.setText("投票结束时间：");
            mTime.setText(DateUtils.GTMToLocal(mProposalListBean.getVoting_start_time()));
            mYajinMaxTime.setText(DateUtils.GTMToLocal(mProposalListBean.getVoting_end_time()));
        }

        String temp = gson.toJson(mProposalListBean.getContent().getValue());
        if (mProposalListBean.getContent().getType().contains("TextProposal")) {//文本
            TextProposalBean value = (TextProposalBean) JsonUtil.parseStringToBean(temp, TextProposalBean.class);
            mProposalTitle.setText(value.getTitle());
            mDesc.setText(value.getDescription());
            mType.setText("文本");
        } else if (mProposalListBean.getContent().getType().contains("BurnCoinsProposal")) {//销毁币
            BurnCoinsProposalBean value = (BurnCoinsProposalBean) JsonUtil.parseStringToBean(temp, BurnCoinsProposalBean.class);
            mProposalTitle.setText(value.getTitle());
            mDesc.setText(value.getDescription());
            mType.setText("销毁币");
            mLLBurn.setVisibility(View.VISIBLE);
            mBurn_amount.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(value.getBurn_amount().get(0).getAmount()).toString()) + "LAMB");
        } else if (mProposalListBean.getContent().getType().contains("CommunityPoolSpendProposal")) {//社区基金
            CommunityPoolSpendProposalBean value = (CommunityPoolSpendProposalBean) JsonUtil.parseStringToBean(temp, CommunityPoolSpendProposalBean.class);
            mProposalTitle.setText(value.getTitle());
            mDesc.setText(value.getDescription());
            mType.setText("社区基金");
            mLl_pool_spend.setVisibility(View.VISIBLE);
            mPool_spend_amount.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(value.getAmount().get(0).getAmount()).toString()) + "LAMB");

        } else if (mProposalListBean.getContent().getType().contains("ParameterChangeProposal")) {//参数变更
            ParameterChangeProposalBean value = (ParameterChangeProposalBean) JsonUtil.parseStringToBean(temp, ParameterChangeProposalBean.class);
            mProposalTitle.setText(value.getTitle());
            mDesc.setText(value.getDescription());
            mType.setText("参数变更");
            mLl_param_change.setVisibility(View.VISIBLE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            layoutManager.setSmoothScrollbarEnabled(true);
            mList.setLayoutManager(layoutManager);
            CommonAdapter mCommonAdapter = AdapterManger.getParammChangeAdapter(this, value.getChanges());
            mList.setAdapter(mCommonAdapter);

        } else if (mProposalListBean.getContent().getType().contains("SoftwareUpgradeProposal")) {//软件升级
            SoftwareUpgradeProposalBean value = (SoftwareUpgradeProposalBean) JsonUtil.parseStringToBean(temp, SoftwareUpgradeProposalBean.class);
            mProposalTitle.setText(value.getTitle());
            mDesc.setText(value.getDescription());
            mType.setText("软件升级");
            mLl_soft_update.setVisibility(View.VISIBLE);
            mVersion.setText(value.getVersion());
            mSwitch_height.setText(value.getSwitch_height());
            mSoftware.setText(value.getSoftware());
        }

        mId.setText(mProposalListBean.getId());


        if (mProposalListBean.getTotal_deposit() == null) {
            mHaveYajin.setText("----");
        } else {
            mHaveYajin.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(mProposalListBean.getTotal_deposit().get(0).getAmount()).toString()) + "LAMB");
        }

        presenter.getMyYajinData(getIntent().getStringExtra("proposalId"));
        presenter.getToupiaoData(getIntent().getStringExtra("proposalId"));
        presenter.getMyToupiaoData(getIntent().getStringExtra("proposalId"));
    }

    @Override
    public void getProposalParamDataHttp(ProposalParamBean proposalParamBean) {
        mNeedYajin.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(proposalParamBean.getMin_deposit().get(0).getAmount()).toString()) + "LAMB");
        presenter.getProposalData(getIntent().getStringExtra("proposalId"));
    }

    @Override
    public void getMyYajinDataHttp(MyYajinBean myYajinBean) {
        hideProgress();
        mMyYajin.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(myYajinBean.getAmount().get(0).getAmount()).toString()) + "LAMB");
    }

    @Override
    public void getTouPiaoDataHttp(TouPiaoBean finalTallyResultBean) {
        BigDecimal yes = new BigDecimal(finalTallyResultBean.getYes());
        BigDecimal no = new BigDecimal(finalTallyResultBean.getNo());
        BigDecimal abstain = new BigDecimal(finalTallyResultBean.getAbstain());
        BigDecimal no_with_vote = new BigDecimal(finalTallyResultBean.getNo_with_veto());
        BigDecimal total = new BigDecimal(0);
        total =BigDecimalUtil.add(BigDecimalUtil.add(yes,no),BigDecimalUtil.add(abstain,no_with_vote));
        mTotal.setText(total.toString());
        mYes.setText(finalTallyResultBean.getYes() + "(" + BigDecimalUtil.multiply(BigDecimalUtil.divide(yes, total), new BigDecimal(100), 2) + "%)");
        mNo.setText(finalTallyResultBean.getNo() + "(" + BigDecimalUtil.multiply(BigDecimalUtil.divide(no,total), new BigDecimal(100), 2) + "%)");
        mAbstain.setText(finalTallyResultBean.getAbstain() + "(" + BigDecimalUtil.multiply(BigDecimalUtil.divide(abstain,total), new BigDecimal(100), 2) + "%)");
        mNoWithVeto.setText(finalTallyResultBean.getNo_with_veto() + "(" + BigDecimalUtil.multiply(BigDecimalUtil.divide(no_with_vote,total), new BigDecimal(100), 2) + "%)");

    }

    @Override
    public void getMyTouPiaoDataHttp(MyTouPiaoBean myTouPiaoBean) {
        if (myTouPiaoBean.getOption().equals("Yes")) {
            mMyToupiao.setText("同意");
        } else if (myTouPiaoBean.getOption().equals("No")) {
            mMyToupiao.setText("反对");
        } else if (myTouPiaoBean.getOption().equals("Abstain")) {
            mMyToupiao.setText("弃权");
        } else if (myTouPiaoBean.getOption().equals("NoWithVeto")) {
            mMyToupiao.setText("强烈反对");
        }

    }

    @OnClick(R.id.go)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putString("title", mProposalTitle.getText().toString().trim());
        bundle.putString("id", mProposalListBean.getId());
        if (mProposalListBean.getProposal_status().equals("DepositPeriod")) {
            ActivityUtils.next(this, CashPledgeActivity.class, bundle);
        } else {
            ActivityUtils.next(this, TouPiaoActivity.class, bundle);
        }
    }

}
