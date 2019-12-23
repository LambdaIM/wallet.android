package com.lambda.wallet.modules.mining;

import android.content.Context;

import com.lambda.wallet.base.BasePresent;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.AllZhiyaTokenBean;
import com.lambda.wallet.bean.AwardBean;
import com.lambda.wallet.bean.ProducerAwardBean;
import com.lambda.wallet.bean.ProducersDetailsBean;
import com.lambda.wallet.bean.ZhiyaProducerBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lambda.wallet.util.JsonUtil;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.List;


public class MiningPresenter extends BasePresent<MiningView> {
    private Context mContext;

    public MiningPresenter(Context context) {
        this.mContext = context;
    }

    public void getProducersBondedData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_producers + "bonded", mContext, new HashMap<>(), new JsonCallback<List<ProducersDetailsBean>>() {
            @Override
            public void onSuccess(Response<List<ProducersDetailsBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getBondedProducerListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getProducersunBondedData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_producers + "unbonded", mContext, new HashMap<>(), new JsonCallback<List<ProducersDetailsBean>>() {
            @Override
            public void onSuccess(Response<List<ProducersDetailsBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getUnBondedProducerListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getProducersBondedingData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_producers + "unbonding", mContext, new HashMap<>(), new JsonCallback<List<ProducersDetailsBean>>() {
            @Override
            public void onSuccess(Response<List<ProducersDetailsBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getUnBondingProducerListDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAllZhiYaTokenData() {
        HttpUtils.getRequets(BaseUrl.HTTP_Get_chain_all_zhiya_token, mContext, new HashMap<>(), new JsonCallback<AllZhiyaTokenBean>() {
            @Override
            public void onSuccess(Response<AllZhiyaTokenBean> response) {
                try {
                    if (response.body() != null) {
                        view.getAllZhiyaTokenDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public void getAwardData(String address) {

        HttpUtils.getRequets(BaseUrl.getHTTP_get_award + address +"/rewards", mContext, new HashMap<>(), new JsonCallback<List<AwardBean>>() {
            @Override
            public void onSuccess(Response<List<AwardBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getAwardDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
    public void getProducerAwardData(String address) {

        HttpUtils.getRequets(BaseUrl.getHTTP_get_producer_award + address , mContext, new HashMap<>(), new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    if (response.code()==200) {
                        ProducerAwardBean producerAwardBean = (ProducerAwardBean) JsonUtil.parseStringToBean(response.body(),ProducerAwardBean.class);
                        view.getProducerAwardDataHttp(producerAwardBean);
                    }else {
                        view.getFailDataHttp();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });

    }


    public void getProducerData(String address) {
        HttpUtils.getRequets(BaseUrl.getHTTP_get_zhiya_producer + address + "/delegations", mContext, new HashMap<>(), new JsonCallback<List<ZhiyaProducerBean>>() {
            @Override
            public void onSuccess(Response<List<ZhiyaProducerBean>> response) {
                try {
                    if (response.body() != null) {
                        view.getZhiyaDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getProducerDetailsData(String address) {
        HttpUtils.getRequets(BaseUrl.getHTTP_Get_producers_details + address, mContext, new HashMap<>(), new JsonCallback<ProducersDetailsBean>() {
            @Override
            public void onSuccess(Response<ProducersDetailsBean> response) {
                try {
                    if (response.body() != null) {
                        view.getProducerDetailsDataHttp(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}

