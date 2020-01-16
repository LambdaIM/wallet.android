package com.lambda.wallet.app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.lambda.wallet.bean.UserBean;
import com.lambda.wallet.gen.DbController;
import com.lambda.wallet.util.LocalManageUtil;
import com.lambda.wallet.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class MyApplication extends MultiDexApplication {
    private static MyApplication mInstance;
    private static DbController mDbController;
    private UserBean mUserBean = new UserBean();

    public static MyApplication getInstance() {
        return mInstance;
    }

    public static DbController getDbController() {
        return mDbController;
    }

    public UserBean getUserBean() {
        return mUserBean;
    }

    public void setUserBean(UserBean userBean) {
        mUserBean = userBean;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        Utils.init(getApplicationContext());


        AutoLayoutConifg.getInstance().useDeviceSize();

        MultiDex.install(this);

        try {
            initOkGo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //配置数据库
        mDbController = DbController.getInstance(this);


        LocalManageUtil.setApplicationLanguage(getApplicationContext());


    }

    @Override
    protected void attachBaseContext(Context base) {
        //保存系统选择语言
        LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(LocalManageUtil.setLocal(base));
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mInstance != null) {
            mInstance = null;
        }
    }


    public void initOkGo() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkHttp");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志
        //超时时间设置
        builder.readTimeout(30000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(30000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(30000, TimeUnit.MILLISECONDS);   //全局的连接超时时间
        OkGo.getInstance().init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //必须设置OkHttpClient
                .setCacheMode(CacheMode.NO_CACHE)               //不使用缓存
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3)        //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
                .addCommonHeaders(headers);//添加通用头；
    }


}

