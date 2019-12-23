package com.lambda.wallet.lambda.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/12/4
 * Time: 14:37
 */
public class TransferUnSignBean<T> {

    /**
     * account_number : 1
     * chain_id : lambda-chain-test2.5
     * fee : {"amount":[{"amount":"101745","denom":"ulamb"}],"gas":"40698"}
     * memo :
     * msgs : [{"type":"cosmos-sdk/MsgSend","value":{"amount":[{"amount":"1000000","denom":"ulamb"}],"from_address":"lambda1prrcl9674j4aqrgrzmys5e28lkcxmntx2gm2zt","to_address":"lambda1hynqrp2f80jqs86gu8nd5wwcnek2wwd3esszg0"}}]
     * sequence : 125
     */

    private String account_number;
    private String chain_id;
    private FeeBean fee;
    private String memo;
    private String sequence;
    private List<T> msgs;

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getChain_id() {
        return chain_id;
    }

    public void setChain_id(String chain_id) {
        this.chain_id = chain_id;
    }

    public FeeBean getFee() {
        return fee;
    }

    public void setFee(FeeBean fee) {
        this.fee = fee;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public List<T> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<T> msgs) {
        this.msgs = msgs;
    }


    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    public byte[] getToSignByte() {
        Gson Presenter = new GsonBuilder().disableHtmlEscaping().create();
        return Presenter.toJson(this).getBytes(Charset.forName("UTF-8"));
    }
}
