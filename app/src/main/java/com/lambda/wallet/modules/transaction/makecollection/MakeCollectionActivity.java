package com.lambda.wallet.modules.transaction.makecollection;

import android.Manifest;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.lib.WheelView;
import com.google.gson.Gson;
import com.google.zxing.client.result.ParsedResultType;
import com.lambda.wallet.R;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.bean.HomeAddressDetailsBean;
import com.lambda.wallet.bean.QrCodeTokenMakeCollectionBean;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.AndroidBug5497Workaround;
import com.lambda.wallet.util.FilesUtils;
import com.lambda.wallet.util.KeyBoardUtil;
import com.lambda.wallet.util.StringUtils;
import com.lambda.wallet.util.Utils;
import com.mylhyl.zxing.scanner.encode.QREncode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.ljp.permission.PermissionItem;

/**
 * 收款
 */
public class MakeCollectionActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.token)
    TextView mToken;
    @BindView(R.id.choose_token)
    LinearLayout mChooseToken;
    @BindView(R.id.qr_code)
    ImageView mQrCode;
    @BindView(R.id.save_qr_code)
    TextView mSaveQrCode;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.copy)
    TextView mCopy;


    private OptionsPickerView pvCustomOptions;//选择资产
    private List<String> mTokenList = new ArrayList<>();
    ArrayList<HomeAddressDetailsBean.ValueBean.CoinsBean> mCoinsBeans = new ArrayList<>();//资产信息

    private int select = 0;
    Bitmap bitmap;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_make_collection;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistActivity(activity);
        setCenterTitle(getString(R.string.make_collection));
    }

    @Override
    protected void initData() {
        mCoinsBeans = getIntent().getParcelableArrayListExtra("token");
        for (int i = 0; i < mCoinsBeans.size(); i++) {
            mTokenList.add(StringUtils.lambdaToken(mCoinsBeans.get(i).getDenom()));
        }
        mToken.setText(StringUtils.lambdaToken(mCoinsBeans.get(0).getDenom()));
        select = 0;

        QrCodeTokenMakeCollectionBean qrCodeQrCodeTokenMakeCollectionBean = new QrCodeTokenMakeCollectionBean();
        qrCodeQrCodeTokenMakeCollectionBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());
        qrCodeQrCodeTokenMakeCollectionBean.setToken(mCoinsBeans.get(select).getDenom());
        qrCodeQrCodeTokenMakeCollectionBean.setType("make_collections_QRCode");
        qrCodeQrCodeTokenMakeCollectionBean.setChainUrl(Utils.getSpUtils().getString("url"));
        Resources res = MakeCollectionActivity.this.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round);
        //生成的账号二维码包含的信息
        bitmap = new QREncode.Builder(MakeCollectionActivity.this)
                .setColor(getResources().getColor(R.color.title_color))//二维码颜色
                .setParsedResultType(ParsedResultType.TEXT)//默认是TEXT类型
                .setContents(new Gson().toJson(qrCodeQrCodeTokenMakeCollectionBean))//二维码内容
                .setSize(1000)
                .setLogoBitmap(bmp)
                .setMargin(1)
                .build().encodeAsBitmap();
        if (bitmap != null) {
            mQrCode.setImageBitmap(bitmap);
        }

        mAddress.setText(MyApplication.getInstance().getUserBean().getAddress());
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.choose_token, R.id.save_qr_code, R.id.copy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_token:
                if (KeyBoardUtil.isSoftInputShow(MakeCollectionActivity.this)) {
                    KeyBoardUtil.getInstance(MakeCollectionActivity.this).hide();
                }

                if (pvCustomOptions == null) {
                    pvCustomOptions = new OptionsPickerView.Builder(MakeCollectionActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                        @Override
                        public void onOptionsSelect(int options1, int option2, int options3, View v) {
                            String tx = mTokenList.get(options1);
                            if (!tx.equals(mToken.getText().toString().trim())) {
                                mToken.setText(tx);
                                select = options1;
                                QrCodeTokenMakeCollectionBean qrCodeQrCodeTokenMakeCollectionBean = new QrCodeTokenMakeCollectionBean();
                                qrCodeQrCodeTokenMakeCollectionBean.setAddress(MyApplication.getInstance().getUserBean().getAddress());
                                qrCodeQrCodeTokenMakeCollectionBean.setToken(mCoinsBeans.get(select).getDenom());
                                qrCodeQrCodeTokenMakeCollectionBean.setType("make_collections_QRCode");
                                qrCodeQrCodeTokenMakeCollectionBean.setChainUrl(Utils.getSpUtils().getString("url"));
                                Resources res = MakeCollectionActivity.this.getResources();
                                Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher_round);
                                //生成的账号二维码包含的信息
                                bitmap = new QREncode.Builder(MakeCollectionActivity.this)
                                        .setColor(getResources().getColor(R.color.title_color))//二维码颜色
                                        .setParsedResultType(ParsedResultType.TEXT)//默认是TEXT类型
                                        .setContents(new Gson().toJson(qrCodeQrCodeTokenMakeCollectionBean))//二维码内容
                                        .setSize(1000)
                                        .setMargin(1)
                                        .setLogoBitmap(bmp)
                                        .build().encodeAsBitmap();
                                if (bitmap != null) {
                                    mQrCode.setImageBitmap(bitmap);
                                }

                            }
                        }
                    })
                            .setTitleBgColor(getResources().getColor(R.color.white))
                            .setSubmitText(getString(R.string.sure))//确定按钮文字
                            .setCancelText(getString(R.string.cancel))//取消按钮文字
                            .setSubCalSize(14)//确定和取消文字大小
                            .setContentTextSize(15)//滚轮文字大小
                            .setSubmitColor(getResources().getColor(R.color.theme_color))//确定按钮文字颜色
                            .setCancelColor(getResources().getColor(R.color.gray_color))//取消按钮文字颜色
                            .setLineSpacingMultiplier(2.0f)
                            .setDividerType(WheelView.DividerType.FILL)
                            .build();
                    pvCustomOptions.setPicker(mTokenList);//添加数据
                    pvCustomOptions.show();
                } else {
                    pvCustomOptions.show();
                }
                break;
            case R.id.save_qr_code:
                if (bitmap != null) {
                    List<PermissionItem> permissonItems = new ArrayList<PermissionItem>();
                    permissonItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.WRITE_EXTERNAL_STORAGE), R.drawable.permission_card1));
                    permissonItems.add(new PermissionItem(Manifest.permission.READ_EXTERNAL_STORAGE, getString(R.string.READ_EXTERNAL_STORAGE), R.drawable.permission_card1));
                    if (Utils.getPermissions(permissonItems, getString(R.string.toast_storage))) {
                        FilesUtils.saveImageToGallery(this, new BitmapDrawable(bitmap));
                        toast(getString(R.string.save_img_success));
                    }
                }

                break;
            case R.id.copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(MyApplication.getInstance().getUserBean().getAddress());
                toast(getResources().getString(R.string.copy));
                break;
        }
    }
}
