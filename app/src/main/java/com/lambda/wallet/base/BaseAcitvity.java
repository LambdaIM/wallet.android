package com.lambda.wallet.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.lambda.wallet.R;
import com.lambda.wallet.app.ActivityUtils;
import com.lambda.wallet.app.AppManager;
import com.lambda.wallet.eventbus.Event;
import com.lambda.wallet.eventbus.EventBusUtil;
import com.lambda.wallet.util.KeyBoardUtil;
import com.lambda.wallet.util.ShowDialog;
import com.lambda.wallet.util.ToastUtils;
import com.lzy.okgo.OkGo;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


public abstract class BaseAcitvity<V, P extends BasePresent<V>> extends AutoLayoutActivity implements BaseView {

    protected P presenter;
    protected Activity activity;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activity = this;
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(activity);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            presenter = initPresenter();
            initBind();
            initViews(savedInstanceState);
            initData();
            initEvent();
        }

        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }

    }

    protected abstract int getLayoutId();

    public abstract P initPresenter();

    protected void initBind() {
        ButterKnife.bind(activity);
    }

    protected abstract void initViews(Bundle savedInstanceState);

    protected abstract void initData();

    public abstract void initEvent();

    /**
     * 是否可以使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.white);
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.keyboardEnable(true); //解决软键盘与底部输入框冲突问题;
        mImmersionBar.init();
    }

    protected <T extends View> T getId(int id) {
        return (T) findViewById(id);
    }
    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭堆栈中的Activity
        AppManager.getAppManager().finishActivity(activity);
        //在onDestroy()生命周期中释放P中引用的V。
        presenter.detach();
        //在onDestroy()生命周期中取消所有子线程里面的网络连接。

        OkGo.getInstance().cancelTag(activity);
        if (mImmersionBar != null) {
            mImmersionBar.destroy();  //在BaseActivity里销毁
        }
        if (ShowDialog.dialog!=null) {
            ShowDialog.dissmiss();
        }
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    @Override
    public void showProgress() {
        ShowDialog.showDialog(this, "", false, null);
    }

    @Override
    public void hideProgress() {
        if (ShowDialog.dialog!=null) {
            ShowDialog.dissmiss();
        }
    }

    @Override
    public void toast(CharSequence s) {
        ToastUtils.showShortToast(s);
    }

    @Override
    public void showNullLayout() {

    }

    @Override
    public void hideNullLayout() {

    }

    @Override
    public void showErrorLayout() {
      /*  getId(R.id.error_text).setVisibility(View.VISIBLE);
        getId(R.id.error_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });*/
    }
    /* *
     * 设置title
     *
     * @param title 文本*/

    @Override
    public void hideErrorLayout() {
        // getId(R.id.error_text).setVisibility(View.GONE);
    }


    /**
     * 设置左侧返回按钮
     */
    protected void setLeftImg() {
//        getId(R.id.iv_back).setVisibility(View.GONE);
    }

    protected void setCenterTitle(String title) {
        goBack();
        if (title == null) {
            return;
        }
        TextView tvTitle = getId(R.id.tv_title);
        ImageView tvTitle1 = getId(R.id.iv_back);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
    }
    /* *
     * 设置返回按钮事件*/

    protected void goBack() {
        getId(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.getInstance(activity).hide();
                ActivityUtils.goBack(activity);
            }
        });
    }

    /**
     * 设置右侧文本
     *
     * @param title        文字
     * @param isVisibility 是否显示
     */

    protected void setRightTitle(String title, Boolean isVisibility) {
        if (title == null) {
            return;
        }
        TextView tvTitle = getId(R.id.tv_right_text);
        tvTitle.setText(title);
        if (isVisibility == true) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // 在Activity中初始化P，并且连接V
            presenter.attach((V) activity);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.fontScale != 1) { //fontScale不为1，需要强制设置为1
            getResources();
        }
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) { //fontScale不为1，需要强制设置为1//f防止过大模式字体变大
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置成默认值，即fontScale为1
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }
}

