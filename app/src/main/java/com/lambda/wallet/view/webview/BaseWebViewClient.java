package com.lambda.wallet.view.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lambda.wallet.util.ShowDialog;



public class BaseWebViewClient extends WebViewClient {

    Context mContext;


    public BaseWebViewClient(Context context) {
        mContext = context;

    }

    /**
     * 是否在 WebView 内加载页面
     *
     * @param view
     * @param url
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if(url == null) return false;
        try {
            if(url.startsWith("weixin://") || url.startsWith("alipays://") ||
                    url.startsWith("mailto://") || url.startsWith("tel://")||url.startsWith("https://openapi.alipay.com")
                //其他自定义的scheme
                    ) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mContext.startActivity(intent);
                return true;
            }
        } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            return false;
        }
        //处理http和https开头的url
        view.loadUrl(url);
        return true;


    }

    /**
     * WebView 开始加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        ShowDialog.showDialog(mContext, "", false, null).show();
    }

    /**
     * WebView 完成加载页面时回调，一次Frame加载对应一次回调
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (ShowDialog.dialog!=null){
            ShowDialog.dissmiss();
        }
    }

    /**
     * WebView 加载页面资源时会回调，每一个资源产生的一次网络加载，除非本地有当前 url 对应有缓存，否则就会加载。
     *
     * @param view WebView
     * @param url  url
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * WebView 可以拦截某一次的 request 来返回我们自己加载的数据
     *
     * @param view    WebView
     * @param request 当前产生 request 请求
     * @return WebResourceResponse
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        super.shouldInterceptRequest(view, request);
     /*   OkLogger.i("==============>"+request.getMethod());
        OkLogger.i("==============>"+request.getUrl());
        OkLogger.i("==============>"+new Gson().toJson(request.getRequestHeaders()));*/
     /*   WebResourceResponse webResourceResponse = new WebResourceResponse(null,null,null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            OkLogger.i("==============>"+request.getMethod());
            OkLogger.i("==============>"+request.getUrl());
            OkLogger.i("==============>"+new Gson().toJson(request.getRequestHeaders()));
            OkLogger.i("==============>"+request.toString());
        }
        if (request.getUrl().getPath().contains("scatterjs-core.min.js")){
            try {
                webResourceResponse.setResponseHeaders(request.getRequestHeaders());
                webResourceResponse.setEncoding(request.getRequestHeaders().get("content-type"));
                webResourceResponse.setData(mContext.getAssets().open("scatterjs-core.min.js"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return webResourceResponse;
        } else if (request.getUrl().getPath().contains("scatter.min.js")){
            try {
                webResourceResponse.setResponseHeaders(request.getRequestHeaders());
                webResourceResponse.setEncoding(request.getRequestHeaders().get("content-type"));
                webResourceResponse.setData(mContext.getAssets().open("scatterjs.min.js"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return webResourceResponse;
        }*/
        return super.shouldInterceptRequest(view, request);
    }

    /**
     * WebView 访问 url 出错
     *
     * @param view
     * @param request
     * @param error
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
//        ToastUtils.showLongToast("页面加载错误~");
    }

    /**
     * WebView ssl 访问证书出错，handler.cancel()取消加载，handler.proceed()对然错误也继续加载
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        if (error.getPrimaryError() == SslError.SSL_INVALID) {
            handler.proceed();
        } else {
            handler.cancel();
        }
    }

    /**
     * 当WebView得页面Scale值发生改变时回调
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }
}

