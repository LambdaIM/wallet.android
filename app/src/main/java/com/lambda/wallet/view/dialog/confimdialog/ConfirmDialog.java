package com.lambda.wallet.view.dialog.confimdialog;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lambda.wallet.R;


/**
 *确认弹窗
 */

public class ConfirmDialog extends Dialog implements View.OnClickListener {

    Callback callback;
    private TextView content;
    private TextView sureBtn;
    private TextView cancleBtn;
    private Context context;

    public ConfirmDialog(Context context, Callback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        this.context = context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_confirm_sure);
        cancleBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        content = (TextView) mView.findViewById(R.id.dialog_confirm_title);
        sureBtn.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        super.setContentView(mView);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.78); //设置宽度
        this.getWindow().setAttributes(lp);
    }


    public ConfirmDialog setContent(String cont) {
        content.setText(cont);
        return this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm_cancle:
                callback.sure();
                this.cancel();
                break;

            case R.id.dialog_confirm_sure:
                this.cancel();
                break;
        }
    }

}
