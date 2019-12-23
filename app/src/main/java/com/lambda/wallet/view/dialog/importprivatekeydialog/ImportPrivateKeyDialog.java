package com.lambda.wallet.view.dialog.importprivatekeydialog;

import android.app.Dialog;
import android.content.Context;
import android.text.ClipboardManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.util.ToastUtils;

/**
 * 导出私钥弹窗
 */

public class ImportPrivateKeyDialog extends Dialog implements View.OnClickListener {

    private TextView info;
    private TextView cancel;
    private TextView copy;
    private Context context;

    public ImportPrivateKeyDialog(Context context) {
        super(context, R.style.CustomDialog);

        this.context = context;
        setCustomDialog();
    }

    private void setCustomDialog() {

        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_import_private_key, null);
        info = (TextView) mView.findViewById(R.id.info);
        cancel = (TextView) mView.findViewById(R.id.cancel);
        copy = (TextView) mView.findViewById(R.id.copy);

        cancel.setOnClickListener(this);
        copy.setOnClickListener(this);
        super.setContentView(mView);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.78); //设置宽度
        this.getWindow().setAttributes(lp);
    }


    public ImportPrivateKeyDialog setContent(String infoJson) {
        info.setText(infoJson);
        return this;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancel:
                this.cancel();
                break;
            case R.id.copy:
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(info.getText());
                ToastUtils.showShortToast(R.string.copy_success);
                this.cancel();
                break;
        }
    }

}
