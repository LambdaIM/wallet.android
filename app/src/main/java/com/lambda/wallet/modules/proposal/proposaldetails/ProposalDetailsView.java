package com.lambda.wallet.modules.proposal.proposaldetails;


import com.lambda.wallet.base.BaseView;
import com.lambda.wallet.bean.MyYajinBean;
import com.lambda.wallet.bean.proposal.MyTouPiaoBean;
import com.lambda.wallet.bean.proposal.ProposalListBean;
import com.lambda.wallet.bean.proposal.ProposalParamBean;
import com.lambda.wallet.bean.proposal.TouPiaoBean;


public interface ProposalDetailsView extends BaseView {

    void getProposalDetailsDataHttp(ProposalListBean proposalListBean);

    void getProposalParamDataHttp(ProposalParamBean proposalParamBean);

    void getMyYajinDataHttp(MyYajinBean myYajinBean);

    void getTouPiaoDataHttp(TouPiaoBean finalTallyResultBean);

    void getMyTouPiaoDataHttp(MyTouPiaoBean myTouPiaoBean);

}
