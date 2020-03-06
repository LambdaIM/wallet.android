package com.lambda.wallet.view.dialog.chaininfodialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lambda.wallet.R;


/**
 *节点信息弹窗
 */

public class ChainInfoDialog extends Dialog implements View.OnClickListener {

    Callback callback;
    private TextView content;
    private TextView sureBtn;
    private Context context;

    public ChainInfoDialog(Context context, Callback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        this.context = context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_chain_info, null);
        sureBtn = (TextView) mView.findViewById(R.id.sure);
        content = (TextView) mView.findViewById(R.id.info);
        sureBtn.setOnClickListener(this);
        super.setContentView(mView);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.78); //设置宽度
        this.getWindow().setAttributes(lp);
    }


    public ChainInfoDialog setContent(String cont) {
        content.setText(cont);
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
