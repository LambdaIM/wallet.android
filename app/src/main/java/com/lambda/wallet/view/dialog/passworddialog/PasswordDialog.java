package com.lambda.wallet.view.dialog.passworddialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.util.BigDecimalUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.ToastUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.ClearEditText;
import com.lzy.okgo.utils.OkLogger;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 验证钱包密码框
 */

public class PasswordDialog extends Dialog implements View.OnClickListener {

    PasswordCallback callback;
    private ClearEditText mClearEditText;
    private TextView sureBtn;
    private TextView cancleBtn;
    private TextView mGas;
    private Context context;

    public PasswordDialog(Context context, PasswordCallback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        this.context = context;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_password, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_confirm_sure);
        cancleBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        mGas = (TextView) mView.findViewById(R.id.gas);
        mClearEditText = (ClearEditText) mView.findViewById(R.id.dialog_password);
        sureBtn.setOnClickListener(this);
        cancleBtn.setOnClickListener(this);
        super.setContentView(mView);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.78); //设置宽度
        this.getWindow().setAttributes(lp);
    }


    public PasswordDialog setGas(String gas) {
        OkLogger.i("==========>"+Utils.getSpUtils().getString(Constants.SpInfo.LAMB));
        if (Utils.getSpUtils().getString(Constants.SpInfo.LAMB).equals("0")){
            mGas.setText("0");
        }else {
            mGas.setText(StringUtils.deletzero(BigDecimalUtil.toLambdaBigDecimal(gas).toString()));
        }

        return this;
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
                if (mClearEditText.getText().toString().trim().length() != 0) {
                    callback.sure(mClearEditText.getText().toString().trim());
                    this.cancel();
                } else {
                    ToastUtils.showShortToast("请输入您的钱包密码");
                }
                break;
        }
    }
}
