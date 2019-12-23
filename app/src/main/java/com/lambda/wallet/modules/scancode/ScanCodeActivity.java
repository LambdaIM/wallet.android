package com.lambda.wallet.modules.scancode;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.AppManager;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.QrCodeTokenMakeCollectionBean;
import com.lambda.wallet.modules.transaction.transfer.TransferActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.FilesUtils;
import com.lambda.wallet.util.JsonUtil;
import com.lambda.wallet.util.ShowDialog;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.lambda.wallet.view.dialog.confimdialog.Callback;
import com.lambda.wallet.view.dialog.confimdialog.ConfirmDialog;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.decode.QRDecode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.ljp.permission.PermissionItem;

public class ScanCodeActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.scanner_view)
    ScannerView mScannerView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ll)
    RelativeLayout mLl;
    @BindView(R.id.tv_right)
    TextView mTvRight;

    private static final int CHOOSE_PICTURE = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initViews(Bundle savedInstanceState) {
        ScannerOptions.Builder builder = new ScannerOptions.Builder();
        builder.setLaserLineColor(getResources().getColor(R.color.theme_color))//扫描线颜色rgb值
                .setFrameCornerColor(getResources().getColor(R.color.theme_color))//扫描框4角颜色rgb值
                .setMediaResId(R.raw.beep)
                .setTipText(getString(R.string.send_qr_code_toast))
                .setTipTextSize(13)
                .setTipTextColor(getResources().getColor(R.color.white))
        ;

        mScannerView.setScannerOptions(builder.build());
        mScannerView.setTouchscreenBlocksFocus(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {
                pareCode(parsedResult.toString());
            }
        });
        mTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
                permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.WRITE_EXTERNAL_STORAGE), R.drawable.permission_card1));
                permissonItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, getString(R.string.READ_EXTERNAL_STORAGE), R.drawable.permission_card1));
                if (Utils.getPermissions(permissonItems, getString(R.string.toast_storage))) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, CHOOSE_PICTURE);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }

    public void pareCode(String data) {
        Bundle bundle = new Bundle();
        if (data.toString().contains("make_collections_QRCode")) {//转账
            Boolean isHaveToken = false;
            QrCodeTokenMakeCollectionBean qrCodeMakeCollectionBean = (QrCodeTokenMakeCollectionBean) JsonUtil.parseStringToBean(data.toString(), QrCodeTokenMakeCollectionBean.class);
            ArrayList<HomeAddressDetailsBean.ValueBean.CoinsBean> mCoinsBeans = new ArrayList<>();//资产信息
            if (Utils.getSpUtils().getString("url").equals(qrCodeMakeCollectionBean.getChainUrl())) {
                mCoinsBeans = getIntent().getParcelableArrayListExtra("token");
                for (int i = 0; i < mCoinsBeans.size(); i++) {
                    if (mCoinsBeans.get(i).getDenom().equals(qrCodeMakeCollectionBean.getToken())) {
                        isHaveToken = true;
                    }
                }
                if (isHaveToken) {
                    bundle.putParcelable("info", qrCodeMakeCollectionBean);
                    bundle.putString("from", "qrcode");
                    bundle.putParcelableArrayList("token", getIntent().getParcelableArrayListExtra("token"));
                    ActivityUtils.next(ScanCodeActivity.this, TransferActivity.class, bundle, true);
                } else {
                    ConfirmDialog confirmDialog = new ConfirmDialog(this, new Callback() {
                        @Override
                        public void sure() {
                            finish();
                        }
                    });
                    confirmDialog.setCancelable(false);
                    confirmDialog.setContent("您暂未拥有" + StringUtils.lambdaToken(qrCodeMakeCollectionBean.getToken()) + ",无法进行扫码转账");
                    confirmDialog.show();
                }
            } else {//需要切换主网环境 重新扫描
                ConfirmDialog confirmDialog = new ConfirmDialog(this, new Callback() {
                    @Override
                    public void sure() {
                        if (TextUtils.isEmpty(Utils.getSpUtils().getString("addUrl"))) {
                            Utils.getSpUtils().put("addUrl", qrCodeMakeCollectionBean.getChainUrl());
                        }
                        Utils.getSpUtils().put("url", qrCodeMakeCollectionBean.getChainUrl());
                        AppManager.getAppManager().restartApp(ScanCodeActivity.this);
                    }
                });
                confirmDialog.setCancelable(false);
                confirmDialog.setContent("您当前链接的节点地址和" + qrCodeMakeCollectionBean.getChainUrl() + "不同，是否切换以完成转账操作?");
                confirmDialog.show();


            }
        } else {
            toast("只能扫描LAMB Wallet合作二维码");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ScanCodeActivity.RESULT_OK && requestCode == CHOOSE_PICTURE) {
            //获取uri拿bitmap要做压缩处理，防止oom
            ShowDialog.showDialog(this, "识别中...", true, null);
            Uri originalUri = null;
            File file = null;
            if (null != data) {
                originalUri = data.getData();
                file = FilesUtils.getFileFromMediaUri(ScanCodeActivity.this, originalUri);
            }
            Bitmap photoBmp = null;
            try {
                photoBmp = FilesUtils.getBitmapFormUri(ScanCodeActivity.this, Uri.fromFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int degree = FilesUtils.getBitmapDegree(file.getAbsolutePath());
            //把图片旋转为正的方向
            Bitmap newbitmap = FilesUtils.rotateBitmapByDegree(photoBmp, degree);
            QRDecode.decodeQR(newbitmap, new OnScannerCompletionListener() {
                @Override
                public void onScannerCompletion(Result result, ParsedResult parsedResult, Bitmap bitmap) {
                    ShowDialog.dissmiss();
                    if (parsedResult != null) {
                        pareCode(parsedResult.toString());
                    } else {
                        toast("暂无法识别，建议您扫描读取");
                    }
                }
            });
        }
    }
}
