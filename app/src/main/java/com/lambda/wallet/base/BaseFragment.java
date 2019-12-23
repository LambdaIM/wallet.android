package com.lambda.wallet.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.lambda.wallet.eventbus.Event;
import com.lambda.wallet.eventbus.EventBusUtil;
import com.lambda.wallet.util.ShowDialog;
import com.lambda.wallet.util.ToastUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;


public abstract class BaseFragment<V, P extends BasePresent<V>> extends Fragment implements BaseView {

    protected P presenter;
    protected ImmersionBar mImmersionBar;
    private View mView;  //缓存Fragment view

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getContentViewLayoutID(), null);
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //创建presenter
        presenter = initPresenter();
        initViews(savedInstanceState);
        initData();
        initEvent();
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }

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
    public void onResume() {
        super.onResume();
        // 在Activity中初始化P，并且连接V
        presenter.attach((V) this);

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在onDestroy()生命周期中释放P中引用的V。
        presenter.detach();
        //在onDestroy()生命周期中取消所有子线程里面的网络连接。
        OkGo.getInstance().cancelTag(getActivity());
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        if (ShowDialog.dialog != null) {
            ShowDialog.dissmiss();
        }
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }
    protected abstract int getContentViewLayoutID();
    
    public abstract P initPresenter();

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
        mImmersionBar = ImmersionBar.with(getActivity());
        mImmersionBar.keyboardEnable(true).statusBarDarkFont(true, 0.2f).init();
    }



    protected <T extends View> T getId(int id) {
        return (T) mView.findViewById(id);
    }

    @Override
    public void showProgress() {
        ShowDialog.showDialog(getActivity(), "", false, null);

    }

    @Override
    public void hideProgress() {
        if (ShowDialog.dialog != null) {
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

    @Override
    public void hideErrorLayout() {
//        getId(R.id.error_text).setVisibility(View.GONE);
    }


}
