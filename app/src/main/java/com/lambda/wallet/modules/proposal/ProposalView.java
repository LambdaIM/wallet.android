package com.lambda.wallet.modules.proposal;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.proposal.ProposalListBean;

import java.util.List;


public interface ProposalView extends BaseView {

    void getProposalListDataHttp(List<ProposalListBean> proposalListBeans);

}
