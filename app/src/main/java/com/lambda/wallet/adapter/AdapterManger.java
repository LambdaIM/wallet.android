package com.lambda.wallet.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambda.wallet.R;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.adapter.baseadapter.base.ViewHolder;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.MarketSellListBean;
import com.lambda.wallet.bean.MyCancelZhiYaBean;
import com.lambda.wallet.bean.MyMiningListBean;
import com.lambda.wallet.bean.MyStoreOrderBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.TransactionDetailBean;
import com.lambda.wallet.bean.TransferHistoryBean;
import com.lambda.wallet.bean.UserBean;
import com.lambda.wallet.bean.proposal.BurnCoinsProposalBean;
import com.lambda.wallet.bean.proposal.CommunityPoolSpendProposalBean;
import com.lambda.wallet.bean.proposal.ParameterChangeProposalBean;
import com.lambda.wallet.bean.proposal.ProposalListBean;
import com.lambda.wallet.bean.proposal.SoftwareUpgradeProposalBean;
import com.lambda.wallet.bean.proposal.TextProposalBean;
import com.lambda.wallet.modules.mining.orderdetails.OrderDetailsActivity;
import com.lambda.wallet.modules.mining.storemarket.buystore.BuyStoreActivity;
import com.lambda.wallet.modules.producer.ProducerDetailsActivity;
import com.lambda.wallet.modules.proposal.proposaldetails.ProposalDetailsActivity;
import com.lambda.wallet.modules.transaction.transactiondetail.TransactionDetailActivity;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.DateUtils;
import com.lambda.wallet.util.JsonUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.ToastUtils;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;

import java.math.BigDecimal;
import java.util.List;


public class AdapterManger<T> {

    static CommonAdapter mCommonAdapter;
    static Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    // TODO: 2019/11/27 保存助计词 
    public static CommonAdapter mnemonicAdapter(final Context context, List<String> mData) {

        mCommonAdapter = new CommonAdapter<String>(context, R.layout.item_save_mnemonic_list, mData) {
            @Override
            protected void convert(final ViewHolder holder, String item, int position) {
                holder.setText(R.id.word, item);
            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getCoinAdapter(final Context context, List<HomeAddressDetailsBean.ValueBean.CoinsBean> mAccountWithCoinBeen) {
        mCommonAdapter = new CommonAdapter<HomeAddressDetailsBean.ValueBean.CoinsBean>(context, R.layout.item_account_with_coin, mAccountWithCoinBeen) {
            @Override
            protected void convert(ViewHolder holder, HomeAddressDetailsBean.ValueBean.CoinsBean item, int position) {
                ImageView imageView = holder.getView(R.id.token_img);
                if (item.getDenom().toLowerCase().equals("ulamb")) {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_lamb));
                }
                if (item.getDenom().toLowerCase().equals("utbb")) {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_tbb));
                }
                holder.setText(R.id.token, StringUtils.lambdaToken(item.getDenom()));
                holder.setText(R.id.amount, StringUtils.addComma(item.getAmount()));
            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getWalletAdapter(final Context context, List<UserBean> mAccountWithCoinBeen) {
        mCommonAdapter = new CommonAdapter<UserBean>(context, R.layout.item_wallet, mAccountWithCoinBeen) {
            @Override
            protected void convert(ViewHolder holder, UserBean item, int position) {
                holder.setText(R.id.name, item.getName());
                holder.setText(R.id.address, StringUtils.lambdaAddress(item.getAddress()));
            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getProducerListAdapter(final Context context, List<ProducersDetailsBean> producersDetailsBeans, String bonded_tokens) {
        mCommonAdapter = new CommonAdapter<ProducersDetailsBean>(context, R.layout.item_producers, producersDetailsBeans) {
            @Override
            protected void convert(ViewHolder holder, ProducersDetailsBean item, int position) {
                holder.setText(R.id.name, item.getDescription().getMoniker());

                TextView textView = holder.getView(R.id.status);
                if (item.getStatus() == 2) {
                    textView.setText(R.string.normal);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_green_status));
                } else {
                    textView.setText(R.string.in_prison);
                    textView.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red_status));
                }

                if (!TextUtils.isEmpty(bonded_tokens)) {
                    holder.setText(R.id.toupiao, mContext.getString(R.string.voting_weight) + StringUtils.deletzero(BigDecimalUtil.multiply(BigDecimalUtil.divide(new BigDecimal(item.getTokens()), new BigDecimal(bonded_tokens)), new BigDecimal(100),2).toString()) + "%");
                } else {
                    holder.setText(R.id.toupiao, mContext.getString(R.string.voting_weight) +"----");
                }
                holder.getConvertView().setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("address", item.getOperator_address());
                        ActivityUtils.next((Activity) mContext, ProducerDetailsActivity.class, bundle);
                    }
                });
            }
        };
        return mCommonAdapter;
    }


    public static CommonAdapter getMarketSellListAdapter(final Context context, List<MarketSellListBean> marketSellListBeans) {
        mCommonAdapter = new CommonAdapter<MarketSellListBean>(context, R.layout.item_market_sell, marketSellListBeans) {
            @Override
            protected void convert(ViewHolder holder, MarketSellListBean item, int position) {
                holder.setText(R.id.address, StringUtils.lambdaAddress(item.getAddress()));
                holder.setText(R.id.price, mContext.getString(R.string.price_lamb_gb_month) + StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(item.getPrice()).toString()));
                ImageView imageView = holder.getView(R.id.status);
                TextView go = holder.getView(R.id.go);
                if (item.getStatus().equals("0")) {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_huoyue));
                    go.setBackground(mContext.getResources().getDrawable(R.drawable.bg_sure_btn));
                    go.setTextColor(mContext.getResources().getColor(R.color.white));
                } else {
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_maiguangle));
                    go.setBackground(mContext.getResources().getDrawable(R.drawable.gray_line_coner));
                    go.setTextColor(mContext.getResources().getColor(R.color.gray_color));
                }

                holder.getView(R.id.go).setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        if (item.getStatus().equals("0")) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("type", 0);//0一键购买1自选交易
                            bundle.putParcelable("sellInfo", item);
                            ActivityUtils.next((Activity) mContext, BuyStoreActivity.class, bundle);
                        } else {
                            ToastUtils.showShortToast(R.string.order_selled);
                        }
                    }
                });
            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getTransferHistoryAdapter(final Context context, List<TransferHistoryBean> historyBeanList) {
        mCommonAdapter = new CommonAdapter<TransferHistoryBean>(context, R.layout.item_historyl, historyBeanList) {
            @Override
            protected void convert(ViewHolder holder, TransferHistoryBean item, int position) {
                TextView amount = holder.getView(R.id.amount);
                TextView status = holder.getView(R.id.status);
                int a = 0;
                for (int i = 0; i < item.getTx().getValue().getMsg().size(); i++) {
                    if (item.getTx().getValue().getMsg().get(i).getValue().getFrom_address().equals(MyApplication.getInstance().getUserBean().getAddress())) {
                        a = i;
                        holder.setText(R.id.address, StringUtils.lambdaAddress(item.getTx().getValue().getMsg().get(i).getValue().getTo_address()));
                        holder.setText(R.id.history_type, mContext.getString(R.string.sendd_to));
                        amount.setTextColor(mContext.getResources().getColor(R.color.red_color));
                        try {
                            if (item.getTx().getValue().getMsg().get(i).getValue().getAmount().size()>1) {
                                amount.setText("-" + StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getAmount()).toString()) + StringUtils.lambdaToken(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getDenom())+mContext.getString(R.string.etc));
                            }else {
                                amount.setText("-" + StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getAmount()).toString()) + StringUtils.lambdaToken(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getDenom()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (item.getTx().getValue().getMsg().get(i).getValue().getTo_address().equals(MyApplication.getInstance().getUserBean().getAddress())) {
                        a = i;
                        holder.setText(R.id.address, StringUtils.lambdaAddress(item.getTx().getValue().getMsg().get(i).getValue().getFrom_address()));
                        holder.setText(R.id.history_type, mContext.getString(R.string.get_from));
                        amount.setTextColor(mContext.getResources().getColor(R.color.order_green_color));
                        try {
                            if (item.getTx().getValue().getMsg().get(i).getValue().getAmount().size()>1) {
                                amount.setText("+" + StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getAmount()).toString()) + StringUtils.lambdaToken(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getDenom())+mContext.getString(R.string.etc));
                            }else {
                                amount.setText("+" + StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getAmount()).toString()) + StringUtils.lambdaToken(item.getTx().getValue().getMsg().get(i).getValue().getAmount().get(0).getDenom()));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    status.setText(item.getLogs().get(a).isSuccess() ?  mContext.getString(R.string.success) : mContext.getString(R.string.fail));
                } catch (Exception e) {
                    e.printStackTrace();
                    status.setText(R.string.success);
                }

                holder.setText(R.id.time, DateUtils.GTMToLocal(item.getTimestamp()));

                holder.getConvertView().setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("hash", item.getTxhash());
                        ActivityUtils.next((Activity) mContext, TransactionDetailActivity.class, bundle);
                    }
                });
            }
        };
        return mCommonAdapter;
    }
    public static CommonAdapter getTransferHistoryDetailsAdapter(final Context context, List<TransactionDetailBean.TxBean.ValueBeanX.MsgBean> historyBeanList) {
        mCommonAdapter = new CommonAdapter<TransactionDetailBean.TxBean.ValueBeanX.MsgBean>(context, R.layout.item_detail_historyl, historyBeanList) {
            @Override
            protected void convert(ViewHolder holder, TransactionDetailBean.TxBean.ValueBeanX.MsgBean item, int position) {
                TextView amount = holder.getView(R.id.amount);
                holder.setText(R.id.from, StringUtils.lambdaAddress(item.getValue().getFrom_address()));
                holder.setText(R.id.to, StringUtils.lambdaAddress(item.getValue().getTo_address()));
                try {
                    if (item.getValue().getAmount().size()>1) {
                        amount.setText( StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getValue().getAmount().get(0).getAmount()).toString()) + StringUtils.lambdaToken(item.getValue().getAmount().get(0).getDenom())+mContext.getString(R.string.etc));
                    }else {
                        amount.setText( StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getValue().getAmount().get(0).getAmount()).toString()) + StringUtils.lambdaToken(item.getValue().getAmount().get(0).getDenom()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getMyZhiyaListAdapter(final Context context, List<MyMiningListBean> zhiyaProducerBeanList) {
        mCommonAdapter = new CommonAdapter<MyMiningListBean>(context, R.layout.item_my_zhiya, zhiyaProducerBeanList) {
            @Override
            protected void convert(ViewHolder holder, MyMiningListBean item, int position) {
                try {
                    holder.setText(R.id.address, item.getProducersDetailsBean().getDescription().getMoniker());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String tbb = null;
                /*shares 和tbb转换【个人质押量计算】
                个人质押后获取到质押分份额后，显示如果是显示质押的tbb数量需要进行转换计算

                shares/delegator_shares*tokens

                用个人的shares 除以节点总的shares 再乘以节点总的tokens*/
                if (item.getProducersDetailsBean() != null) {
                    tbb = StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(BigDecimalUtil.multiply(BigDecimalUtil.divide(new BigDecimal(item.getProducersDetailsBean().getTokens()), new BigDecimal(item.getProducersDetailsBean().getDelegator_shares())), new BigDecimal(item.getShares())).toString()).toString());
                    holder.setText(R.id.zhiya_token, tbb + "TBB");
                    try {
                        holder.setText(R.id.award, StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getAwardBean().getAmount()).toString()) + StringUtils.lambdaToken(item.getAwardBean().getDenom()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        holder.setText(R.id.award, "0LAMB");
                    }
                }
                holder.getConvertView().setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("address", item.getValidator_address());
                        ActivityUtils.next((Activity) mContext, ProducerDetailsActivity.class, bundle);
                    }
                });
            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getMyCancelZhiyaListAdapter(final Context context, List<MyCancelZhiYaBean> zhiyaProducerBeanList) {
        mCommonAdapter = new CommonAdapter<MyCancelZhiYaBean>(context, R.layout.item_my_cancel_zhiya, zhiyaProducerBeanList) {
            @Override
            protected void convert(ViewHolder holder, MyCancelZhiYaBean item, int position) {
                holder.setText(R.id.address, StringUtils.lambdaAddress(item.getDelegator_address()));
                String tbb = null;
                tbb = StringUtils.addComma(BigDecimalUtil.toLambdaBigDecimal(item.getEntriesBean().getBalance()).toString());
                holder.setText(R.id.amount, tbb + "TBB");
                holder.setText(R.id.time, mContext.getString(R.string.complete) + DateUtils.GTMToLocal(item.getEntriesBean().getCompletion_time()));


            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getStoreOrderListAdapter(final Context context, List<MyStoreOrderBean> myStoreOrderBeanList) {
        mCommonAdapter = new CommonAdapter<MyStoreOrderBean>(context, R.layout.item_store_order, myStoreOrderBeanList) {
            @Override
            protected void convert(ViewHolder holder, MyStoreOrderBean item, int position) {
                TextView order_status = holder.getView(R.id.order_status);
                TextView order_type = holder.getView(R.id.order_type);
                if (item.getMatchOrder().getStatus().equals("0")) {
                    order_status.setText(mContext.getString(R.string.active));
                } else {
                    order_status.setText(mContext.getString(R.string.done));
                }

                if (item.getMatchOrder().getAskAddress().equals(MyApplication.getInstance().getUserBean().getAddress())) {//卖方地址和自己地址一致则为卖单
                    order_type.setText(R.string.seller);
                    order_type.setBackgroundColor(mContext.getResources().getColor(R.color.red_color));
                    holder.setText(R.id.one, mContext.getString(R.string.seller_space_gb));
                    holder.setText(R.id.two, mContext.getString(R.string.get_amount_lamb));
                } else {
                    order_type.setText(R.string.buy1);
                    order_type.setBackgroundColor(mContext.getResources().getColor(R.color.order_green_color));
                    holder.setText(R.id.one, mContext.getString(R.string.buy_space_gb));
                    holder.setText(R.id.two, mContext.getString(R.string.par_amount_lamb));
                }

                holder.setText(R.id.order_id, item.getMatchOrder().getOrderId());
                holder.setText(R.id.time, DateUtils.GTMToLocal(item.getMatchOrder().getCreateTime()));

                holder.setText(R.id.space, item.getMatchOrder().getSize());
                holder.setText(R.id.amount, StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(item.getMatchOrder().getUserPay().getAmount()).toString()));

                holder.getConvertView().setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("order", item);
                        ActivityUtils.next((Activity) mContext, OrderDetailsActivity.class, bundle);
                    }
                });
            }
        };
        return mCommonAdapter;
    }


    public static CommonAdapter getProposalListAdapter(final Context context, List<ProposalListBean> proposalListBeans) {
        mCommonAdapter = new CommonAdapter<ProposalListBean>(context, R.layout.item_proposal, proposalListBeans) {
            @Override
            protected void convert(ViewHolder holder, ProposalListBean item, int position) {
                TextView status = holder.getView(R.id.status);
                if (item.getProposal_status().equals("Rejected")) {
                    status.setText(R.string.rejected);
                    status.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red_status));
                } else if (item.getProposal_status().equals("Passed")) {
                    status.setText(R.string.passed);
                    status.setBackground(mContext.getResources().getDrawable(R.drawable.bg_green_status));
                } else if (item.getProposal_status().equals("DepositPeriod")) {
                    status.setText(R.string.depositPeriod);
                    status.setBackground(mContext.getResources().getDrawable(R.drawable.bg_blue_status));
                } else if (item.getProposal_status().equals("VotingPeriod")) {
                    status.setText(R.string.voting_period);
                    status.setBackground(mContext.getResources().getDrawable(R.drawable.bg_yellow_status));
                }
                String temp = gson.toJson(item.getContent().getValue());
                if (item.getContent().getType().contains("TextProposal")) {//文本
                    TextProposalBean value = (TextProposalBean) JsonUtil.parseStringToBean(temp, TextProposalBean.class);
                    holder.setText(R.id.title, value.getTitle());
                    holder.setText(R.id.desc, value.getDescription());
                } else if (item.getContent().getType().contains("BurnCoinsProposal")) {//销毁币
                    BurnCoinsProposalBean value = (BurnCoinsProposalBean) JsonUtil.parseStringToBean(temp, BurnCoinsProposalBean.class);
                    holder.setText(R.id.title, value.getTitle());
                    holder.setText(R.id.desc, value.getDescription());

                } else if (item.getContent().getType().contains("CommunityPoolSpendProposal")) {//社区基金
                    CommunityPoolSpendProposalBean value = (CommunityPoolSpendProposalBean) JsonUtil.parseStringToBean(temp, CommunityPoolSpendProposalBean.class);
                    holder.setText(R.id.title, value.getTitle());
                    holder.setText(R.id.desc, value.getDescription());
                } else if (item.getContent().getType().contains("ParameterChangeProposal")) {//参数变更
                    ParameterChangeProposalBean value = (ParameterChangeProposalBean) JsonUtil.parseStringToBean(temp, ParameterChangeProposalBean.class);
                    holder.setText(R.id.title, value.getTitle());
                    holder.setText(R.id.desc, value.getDescription());
                } else if (item.getContent().getType().contains("SoftwareUpgradeProposal")) {//软件升级
                    SoftwareUpgradeProposalBean value = (SoftwareUpgradeProposalBean) JsonUtil.parseStringToBean(temp, SoftwareUpgradeProposalBean.class);
                    holder.setText(R.id.title, value.getTitle());
                    holder.setText(R.id.desc, value.getDescription());
                }


                holder.getConvertView().setOnClickListener(new NoDoubleClickListener() {
                    @Override
                    protected void onNoDoubleClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("proposalId", item.getId());
                        ActivityUtils.next((Activity) mContext, ProposalDetailsActivity.class, bundle);

                    }
                });
            }
        };
        return mCommonAdapter;
    }

    public static CommonAdapter getParammChangeAdapter(final Context context, List<ParameterChangeProposalBean.ChangesBean> changesBeans) {
        mCommonAdapter = new CommonAdapter<ParameterChangeProposalBean.ChangesBean>(context, R.layout.item_para_change, changesBeans) {
            @Override
            protected void convert(ViewHolder holder, ParameterChangeProposalBean.ChangesBean item, int position) {
                holder.setText(R.id.subspace, item.getSubspace());
                holder.setText(R.id.key, item.getKey());
                holder.setText(R.id.value, item.getValue());

            }
        };
        return mCommonAdapter;
    }
}
