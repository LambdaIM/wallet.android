package com.lambda.wallet.view.dialog.addchaindialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.util.ToastUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 新增节点
 */

public class AddChainDialog extends Dialog implements View.OnClickListener {

    AddChainCallback callback;
    private ClearEditText mClearEditText;
    private ClearEditText mPort;
    private TextView sureBtn;
    private TextView cancleBtn;
    private Context context;

    public AddChainDialog(Context context, AddChainCallback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        this.context = context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_chain, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_confirm_sure);
        cancleBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        mClearEditText = (ClearEditText) mView.findViewById(R.id.dialog_url);
        mPort = (ClearEditText) mView.findViewById(R.id.dialog_port);
        sureBtn.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        super.setContentView(mView);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.78); //设置宽度
        try {
            mClearEditText.setText(Utils.getSpUtils().getString("addUrl").split(":")[1].replace("//",""));
            mPort.setText(Utils.getSpUtils().getString("addUrl").split(":")[2]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.getWindow().setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (mClearEditText != null) {
                    mClearEditText.setFocusable(true);
                    mClearEditText.setFocusableInTouchMode(true);
                    mClearEditText.requestFocus();
                    InputMethodManager inputManager = (InputMethodManager) mClearEditText
                            .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(mClearEditText, 0);
                }
            }
        }, 200);
    }

    @Override
    public void cancel() {
        super.cancel();
        InputMethodManager inputManager = (InputMethodManager) mClearEditText
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mClearEditText.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_confirm_cancle:
                this.cancel();
                break;

            case R.id.dialog_confirm_sure:
                if (mClearEditText.getText().toString().trim().length() != 0 && mPort.getText().toString().trim().length() != 0) {
                    callback.sure(mClearEditText.getText().toString().trim() + ":" + mPort.getText().toString().trim());
                    this.cancel();
                } else {
                    ToastUtils.showShortToast(R.string.toast_ip);
                }
                break;
        }
    }
}
