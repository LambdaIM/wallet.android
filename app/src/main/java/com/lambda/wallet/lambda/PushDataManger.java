package com.lambda.wallet.lambda;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseUrl;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.lambda.bean.FeeBean;
import com.lambda.wallet.lambda.bean.SignaturesBean;
import com.lambda.wallet.lambda.bean.TransactionSuccessBean;
import com.lambda.wallet.lambda.bean.TransferSignBean;
import com.lambda.wallet.lambda.bean.TransferUnSignBean;
import com.lambda.wallet.net.HttpUtils;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.JsonUtil;
import com.lambda.wallet.util.ShowDialog;
import com.lambda.wallet.util.Utils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.utils.IOUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.internal.Util;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/5
 * Time: 11:05
 *
 * @param <T> the type parameter
 */
public class PushDataManger<T> {
    /**
     * The M callback.
     */
    Callback mCallback;
    /**
     * The M context.
     */
    Context mContext;
    /**
     * The Userpassword.
     */
    String userpassword;

    /**
     * The Transfer un sign bean.
     */
    TransferUnSignBean transferUnSignBean = new TransferUnSignBean();
    /**
     * The Transfer sign bean.
     */
//准备进行打包交易
    TransferSignBean transferSignBean = new TransferSignBean();

    /**
     * The Gas.
     */
    String gas, /**
     * The Token.
     */
    token, /**
     * The Memo.
     */
    memo, /**
     * The Private key.
     */
    privateKey = null;

    /**
     * The Fee bean.
     */
    FeeBean feeBean;
    /**
     * The Msgs bean list.
     */
    List<T> msgsBeanList;


    String chain_id, account_number, sequence;

    /**
     * Instantiates a new Push data manger.
     *
     * @param context      the context
     * @param userpassword the userpassword
     * @param callback     the callback
     */
    public PushDataManger(Context context, String userpassword, Callback callback, HashMap<String, String> hashMap) {
        mContext = context;
        this.userpassword = userpassword;
        mCallback = callback;
        this.chain_id = hashMap.get("chain_id");
        this.account_number = hashMap.get("account_number");
        this.sequence = hashMap.get("sequence");
    }


    /**
     * Push.
     *
     * @param gas          the gas
     * @param token        the token
     * @param memo         the memo
     * @param msgsBeanList the msgsBeanList
     */
    public void push(String gas, String token, String memo, List<T> msgsBeanList) {
        this.gas = gas;
        this.token = token;
        this.msgsBeanList = msgsBeanList;
        this.memo = memo;
        setUnsignData();
    }

    /**
     * Sets unsign data.
     */
    public void setUnsignData() {
        transferUnSignBean.setAccount_number(account_number);
        transferUnSignBean.setChain_id(chain_id);

        feeBean = new FeeBean();
        String gasSure = BigDecimalUtil.multiply(new BigDecimal(gas), new BigDecimal(1.5)).toString();
        feeBean.setGas(Math.round(Double.parseDouble(gasSure)) + "");
        List<FeeBean.AmountBean> amountBeans = new ArrayList<>();
        if (!Utils.getSpUtils().getString(Constants.SpInfo.LAMB).equals("0")) {
            String amount = BigDecimalUtil.multiply(new BigDecimal(feeBean.getGas()), new BigDecimal(Constants.GAS_PRICE)).toString();
            amountBeans.add(new FeeBean.AmountBean(Math.round(Double.parseDouble(amount)) + "", token));
        }
        feeBean.setAmount(amountBeans);

        transferUnSignBean.setFee(feeBean);
        transferUnSignBean.setMemo(memo);


        transferUnSignBean.setMsgs(msgsBeanList);
        transferUnSignBean.setSequence(sequence);
        goSign();
    }

    /**
     * Go sign.
     */
    public void goSign() {
        try {
            privateKey = PasswordToKeyUtils.dncryptPwdForPrivate(userpassword, MyApplication.getInstance().getUserBean().getPrivateKey(), MyApplication.getInstance().getUserBean().getSalt());
            if (TextUtils.isEmpty(privateKey)) {
                mCallback.onFail(mContext.getString(R.string.password_error));
                return;
            } else {
                String signatureTx = SignUtils.sign(privateKey, transferUnSignBean.getToSignByte());
                setSignData(signatureTx);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            mCallback.onFail(mContext.getString(R.string.password_error));
            return;
        }

    }

    /**
     * Sets sign data.
     *
     * @param signatureTx the signature tx
     */
    public void setSignData(String signatureTx) {

        TransferSignBean.TxBean txBean = new TransferSignBean.TxBean();
        txBean.setMsg(msgsBeanList);
        txBean.setFee(feeBean);


        List<SignaturesBean> signaturesBeanList = new ArrayList<>();
        SignaturesBean signaturesBean = new SignaturesBean();
        signaturesBean.setSignature(signatureTx);
        try {
            signaturesBean.setPub_key(new SignaturesBean.PubKeyBean(Constants.SignType.PUB_TYPE, WalletManger.getPubKeyValue(privateKey)));
        } catch (Exception e) {
            e.printStackTrace();
            mCallback.onFail(mContext.getString(R.string.password_error));
            return;
        }
        signaturesBeanList.add(signaturesBean);

        txBean.setSignatures(signaturesBeanList);

        txBean.setMemo(memo);

        transferSignBean.setTx(txBean);
        transferSignBean.setMode(Constants.SignType.TX_MODE);

        pushTransacction(transferSignBean);
    }

    /**
     * Push transacction.
     *
     * @param transferSignBean the transfer sign bean
     */
    public void pushTransacction(TransferSignBean transferSignBean) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        HttpUtils.postRequest(BaseUrl.HTTP_transfer + "txs", mContext, gson.toJson(transferSignBean), new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                if (response.body() != null) {
                    if (response.body().contains("error")) {
                        if (ShowDialog.dialog != null) {
                            ShowDialog.dissmiss();
                            mCallback.onFail(response.body());
                        }
                    } else {
                        TransactionSuccessBean transactionSuccessBean = (TransactionSuccessBean) JsonUtil.parseStringToBean(response.body().toString(), TransactionSuccessBean.class);
                        mCallback.onSuccess(transactionSuccessBean);
                        if (ShowDialog.dialog != null) {
                            ShowDialog.setMessage(mContext.getString(R.string.push_success));
                        }
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                if (ShowDialog.dialog != null) {
                    ShowDialog.dissmiss();
                }
                if (isPlaintext(response.getRawResponse().body().contentType())) {
                    byte[] bytes = new byte[0];
                    try {
                        bytes = IOUtils.toByteArray(response.getRawResponse().body().byteStream());
                        MediaType contentType = response.getRawResponse().body().contentType();
                        Charset charset = contentType != null ? contentType.charset(Util.UTF_8) : Util.UTF_8;
                        String body = new String(bytes, charset);
                        mCallback.onFail(body);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }


    /**
     * The interface Callback.
     */
    public interface Callback {
        /**
         * On success.
         *
         * @param transactionSuccessBean the transaction success bean
         */
        void onSuccess(TransactionSuccessBean transactionSuccessBean);

        /**
         * On fail.
         *
         * @param msg the msg
         */
        void onFail(String msg);
    }

}
