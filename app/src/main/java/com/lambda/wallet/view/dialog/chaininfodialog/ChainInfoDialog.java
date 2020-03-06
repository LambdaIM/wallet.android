package com.lambda.wallet.view.dialog.chaininfodialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lambda.wallet.R;
import com.lambda.wallet.bean.ChainInfoBean;


/**
 *节点信息弹窗
 */

public class ChainInfoDialog extends Dialog implements View.OnClickListener {

    Callback callback;
    private TextView channels;
    private TextView id;
    private TextView listen_addr;
    private TextView moniker;
    private TextView network;
    private TextView other;
    private TextView protocol_version;
    private TextView version;
    private TextView sureBtn;
    private TextView info;
    private Context context;
    private LinearLayout success;
    private LinearLayout fail;

    public ChainInfoDialog(Context context, Callback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        this.context = context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_chain_info, null);
        sureBtn = (TextView) mView.findViewById(R.id.sure);
        id = (TextView) mView.findViewById(R.id.id);
        listen_addr = (TextView) mView.findViewById(R.id.listen_addr);
        channels = (TextView) mView.findViewById(R.id.channels);
        moniker = (TextView) mView.findViewById(R.id.moniker);
        network = (TextView) mView.findViewById(R.id.network);
        other = (TextView) mView.findViewById(R.id.other);
        protocol_version = (TextView) mView.findViewById(R.id.protocol_version);
        version = (TextView) mView.findViewById(R.id.version);
        info = (TextView) mView.findViewById(R.id.info);
        success = (LinearLayout) mView.findViewById(R.id.success);
        fail = (LinearLayout) mView.findViewById(R.id.fail);
        sureBtn.setOnClickListener(this);
        super.setContentView(mView);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.78); //设置宽度
        this.getWindow().setAttributes(lp);
    }


    public ChainInfoDialog setContent(ChainInfoBean chainInfoBean) {
        success.setVisibility(View.VISIBLE);
        fail.setVisibility(View.GONE);
        channels.setText(chainInfoBean.getChannels());
        id.setText(chainInfoBean.getId());
        listen_addr.setText(chainInfoBean.getListen_addr());
        moniker.setText(chainInfoBean.getMoniker());
        network.setText(chainInfoBean.getNetwork());
        other.setText(new Gson().toJson(chainInfoBean.getOther()));
        protocol_version.setText(new Gson().toJson(chainInfoBean.getProtocol_version()));
        version.setText(chainInfoBean.getVersion());
        return this;
    }
    public ChainInfoDialog setError(String  error) {
        success.setVisibility(View.GONE);
        fail.setVisibility(View.VISIBLE);
        info.setText(error);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sure:
                callback.sure();
                this.cancel();
                break;
        }
    }

}
