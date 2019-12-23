package com.lambda.wallet.lambda;

import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.bean.ChainBean;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.net.callbck.JsonCallback;
import com.lambda.wallet.util.Utils;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/18
 * Time: 12:28
 */
public class ChainInfoUtils {

    String chain_id, account_number, sequence;
   Callback mCallback;

    public ChainInfoUtils(Callback callback) {
        mCallback = callback;
    }

    public void getInfo() {
        HashMap<String, String> hashMap = new HashMap();
        HttpUtils.getRequets(BaseUrl.HTTP_Get_chain_details, Utils.getContext(), new HashMap<>(), new JsonCallback<ChainBean>() {
            @Override
            public void onSuccess(Response<ChainBean> response) {
                try {
                    if (response.body() != null) {
                        chain_id = response.body().getNetwork().toString();
                        hashMap.put("chain_id", chain_id);
                        HttpUtils.getRequets(BaseUrl.HTTP_Get_account + "/" + MyApplication.getInstance().getUserBean().getAddress(), Utils.getContext(), new HashMap<>(), new JsonCallback<HomeAddressDetailsBean>() {
                            @Override
                            public void onSuccess(Response<HomeAddressDetailsBean> response) {
                                try {
                                    if (response.body() != null) {
                                        account_number = response.body().getValue().getAccount_number();
                                        sequence = response.body().getValue().getSequence();
                                        hashMap.put("account_number", account_number);
                                        hashMap.put("sequence", sequence);
                                        mCallback.onSuccess(hashMap);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface Callback {
        void onSuccess(HashMap<String ,String> hashMap);

    }

}
