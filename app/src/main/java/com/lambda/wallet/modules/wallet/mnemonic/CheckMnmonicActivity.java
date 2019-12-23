package com.lambda.wallet.modules.wallet.mnemonic;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.lambda.wallet.R;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.adapter.baseadapter.base.ViewHolder;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.MyApplication;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.base.Constants;
import com.lambda.wallet.bean.UserBean;
import com.lambda.wallet.lambda.WalletManger;
import com.lambda.wallet.modules.main.MainActivity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2019/11/27 校验助记词是否正确
public class CheckMnmonicActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.sure_mnemonic_list)
    RecyclerView mSureMnemonicList;
    @BindView(R.id.mnemonic_list)
    RecyclerView mMnemonicList;
    @BindView(R.id.go)
    Button mGo;

    private ArrayList<String> mSureWords = new ArrayList<>();
    private ArrayList<String> mCheckWords = new ArrayList<>();
    private CommonAdapter mSureCommonAdapter;
    private CommonAdapter mCheckCommonAdapter;
    private UserBean mUserBean = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_mnmonic;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getString(R.string.mnemonic));
        GridLayoutManager layoutManager = new GridLayoutManager(CheckMnmonicActivity.this, 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        mMnemonicList.setLayoutManager(layoutManager);
        GridLayoutManager layoutManager1 = new GridLayoutManager(CheckMnmonicActivity.this, 3);
        layoutManager1.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager1.setSmoothScrollbarEnabled(true);
        mSureMnemonicList.setLayoutManager(layoutManager1);
    }

    @Override
    protected void initData() {
        mCheckWords = getIntent().getStringArrayListExtra("words");
        mUserBean = getIntent().getParcelableExtra("userBean");
        mSureCommonAdapter = new CommonAdapter<String>(this, R.layout.item_save_mnemonic_list, mSureWords) {
            @Override
            protected void convert(final ViewHolder holder, String item, int position) {
                holder.setText(R.id.word, item);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCheckWords.add(item);
                        mCheckCommonAdapter.notifyDataSetChanged();
                        mSureWords.remove(item);
                        notifyDataSetChanged();
                    }
                });
            }
        };
        mCheckCommonAdapter = new CommonAdapter<String>(this, R.layout.item_save_mnemonic_list, mCheckWords) {
            @Override
            protected void convert(final ViewHolder holder, String item, int position) {
                holder.setText(R.id.word, item);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSureWords.add(item);
                        mSureCommonAdapter.notifyDataSetChanged();
                        mCheckWords.remove(item);
                        notifyDataSetChanged();
                    }
                });
            }
        };
        mSureMnemonicList.setAdapter(mSureCommonAdapter);
        mMnemonicList.setAdapter(mCheckCommonAdapter);
    }

    @Override
    public void initEvent() {

    }

    @OnClick(R.id.go)
    public void onClick() {
        if (mSureWords.size() == 0 || mSureWords.size() != 24) {
            toast(getString(R.string.no_mnmonic_error));
            return;
        }
        if (!WalletManger.isMnemonicWords(mSureWords)) {
            toast(getString(R.string.mnmonic_error));
            return;
        }
        String address0 = WalletManger.getAddressFromPrivateKey(WalletManger.getPrivateKeyFromMnemonicCode(mSureWords));
        if (address0.equals(mUserBean.getAddress()) && WalletManger.getPubFromPrivateKey(WalletManger.getPrivateKeyFromMnemonicCode(mSureWords)).equals(mUserBean.getPublicKey())) {
            toast(getString(R.string.check_success));
            Utils.getSpUtils().put(Constants.SpInfo.FIRSTUSER, mUserBean.getName());
            MyApplication.getDbController().insertOrReplace(mUserBean);
            ActivityUtils.next(this, MainActivity.class);
        } else {
            toast(getString(R.string.mnmonic_error));
        }
    }
}
