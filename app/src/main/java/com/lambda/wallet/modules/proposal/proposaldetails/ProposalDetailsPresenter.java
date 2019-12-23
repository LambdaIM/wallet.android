package com.lambda.wallet.modules.proposal.proposaldetails;

import android.content.Context;

import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.MyYajinBean;
import com.lambda.wallet.bean.proposal.MyTouPiaoBean;
import com.lambda.wallet.bean.proposal.ProposalListBean;
import com.lambda.wallet.bean.proposal.ProposalParamBean;
import com.lambda.wallet.bean.proposal.TouPiaoBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;


public class ProposalDetailsPresenter extends BasePresent<ProposalDetailsView> {
    private Context mContext;

    public ProposalDetailsPresenter(Context context) {
        this.mContext = context;
    }

    public void getProposalData(String proposalId) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_proposal_details + proposalId, mContext, new HashMap<>(), new JsonCallback<ProposalListBean>() {
            @Override
            public void onSuccess(Response<ProposalListBean> response) {
                try {
                    if (response.body() != null) {
                        view.getProposalDetailsDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getProposalYajinData() {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_proposal_parameters, mContext, new HashMap<>(), new JsonCallback<ProposalParamBean>() {
            @Override
            public void onSuccess(Response<ProposalParamBean> response) {
                try {
                    if (response.body() != null) {
                        view.getProposalParamDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getMyYajinData(String id) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_proposal_my_yajin + id + "/deposits/" + MyApplication.getInstance().getUserBean().getAddress(), mContext, new HashMap<>(), new JsonCallback<MyYajinBean>() {
            @Override
            public void onSuccess(Response<MyYajinBean> response) {
                try {
                    if (response.body() != null) {
                        view.getMyYajinDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getToupiaoData(String id) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_proposal_toupiao + id + "/tally", mContext, new HashMap<>(), new JsonCallback<TouPiaoBean>() {
            @Override
            public void onSuccess(Response<TouPiaoBean> response) {
                try {
                    if (response.body() != null) {
                        view.getTouPiaoDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getMyToupiaoData(String id) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_proposal_toupiao + id + "/votes/"+MyApplication.getInstance().getUserBean().getAddress(), mContext, new HashMap<>(), new JsonCallback<MyTouPiaoBean>() {
            @Override
            public void onSuccess(Response<MyTouPiaoBean> response) {
                try {
                    if (response.body() != null) {
                        view.getMyTouPiaoDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}

