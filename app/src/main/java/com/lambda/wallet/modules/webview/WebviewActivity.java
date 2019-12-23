package com.lambda.wallet.modules.webview;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lambda.wallet.R;
import com.lambda.wallet.base.BaseAcitvity;
import com.lambda.wallet.normalvp.NormalPresenter;
import com.lambda.wallet.normalvp.NormalView;
import com.lambda.wallet.view.nodoubleclick.NoDoubleClickListener;
import com.lambda.wallet.view.webview.BaseWebChromeClient;
import com.lambda.wallet.view.webview.BaseWebSetting;
import com.lambda.wallet.view.webview.BaseWebView;
import com.lambda.wallet.view.webview.BaseWebViewClient;
import com.lzy.okgo.utils.OkLogger;

import butterknife.BindView;

public class WebviewActivity extends BaseAcitvity<NormalView, NormalPresenter> implements NormalView {

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.webview)
    BaseWebView mWebDetails;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_close)
    TextView mClose;

    String url = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public NormalPresenter initPresenter() {
        return new NormalPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setCenterTitle(getIntent().getStringExtra("title"));
        url = getIntent().getStringExtra("url");
        OkLogger.i("======>" + url);
        // 开启辅助功能崩溃
        mWebDetails.disableAccessibility(this);
        new BaseWebSetting(mWebDetails, WebviewActivity.this, false);//设置webseeting
        mWebDetails.setWebViewClient(new BaseWebViewClient(WebviewActivity.this));
        mWebDetails.setWebChromeClient(new BaseWebChromeClient(WebviewActivity.this, mProgressBar, mTvTitle));
        mWebDetails.loadUrl(url);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initEvent() {
        getId(R.id.iv_back).setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                if (mWebDetails.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                    mWebDetails.goBack();
                } else {//当webview处于第一页面时,直接退出程序
                    finish();
                }
            }
        });

        mClose.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                finish();
            }
        });
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebDetails.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                mWebDetails.goBack();
                return true;
            } else {//当webview处于第一页面时,直接退出程序
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
