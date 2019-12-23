package com.lambda.wallet.modules.proposal;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.proposal.ProposalListBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class ProposalPresenter extends BasePresent<ProposalView> {
    private Context mContext;

    public ProposalPresenter(Context context) {
        this.mContext = context;
    }

    public void getProposalData() {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_proposal_list, mContext, new HashMap<>(), new JsonCallback<List<ProposalListBean>>() {
            @Override
            public void onSuccess(Response<List<ProposalListBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getProposalListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}

