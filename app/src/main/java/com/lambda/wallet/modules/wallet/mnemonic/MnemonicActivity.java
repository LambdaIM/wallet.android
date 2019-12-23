package com.lambda.wallet.modules.wallet.mnemonic;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.Button;

import com.lambda.wallet.R;
import com.lambda.wallet.adapter.AdapterManger;
import com.lambda.wallet.adapter.baseadapter.CommonAdapter;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

// TODO: 2019/11/27 生成助记词
public class MnemonicActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {


    @BindView(R.id.mnemonic_list)
    RecyclerView mMnemonicList;
    @BindView(R.id.go)
    Button mGo;

    private CommonAdapter mCommonAdapter;
    private ArrayList<String> mWords = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_mnemonic;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//当前页面防截屏录屏

        setCenterTitle(getString(R.string.mnemonic));
        GridLayoutManager layoutManager = new GridLayoutManager(MnemonicActivity.this, 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.setSmoothScrollbarEnabled(true);
        mMnemonicList.setLayoutManager(layoutManager);
    }

    @Override
    protected void initData() {
        mWords = getIntent().getStringArrayListExtra("words");
        mCommonAdapter = AdapterManger.mnemonicAdapter(this, mWords);
        mMnemonicList.setAdapter(mCommonAdapter);
    }


    @Override
    public void initEvent() {

    }

    @OnClick(R.id.go)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("userBean", getIntent().getParcelableExtra("userBean"));//方便测试把正确的传递到下个页面，然后正确的是需要根据助记词生成地址去校验
        Collections.shuffle(mWords);//随机打乱顺序
        bundle.putStringArrayList("words", mWords);
        ActivityUtils.next(this, CheckMnmonicActivity.class, bundle);
    }
}
