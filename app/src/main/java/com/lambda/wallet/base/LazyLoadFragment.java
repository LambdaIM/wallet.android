package com.lambda.wallet.base;

import android.os.Bundle;
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

import javax.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * Fragment预加载问题的解决方案：
 * 1.可以懒加载的Fragment
 * 2.切换到其他页面时停止加载数据（可选）
 */

public abstract class LazyLoadFragment<V, P extends BasePresent<V>> extends Fragment implements BaseView {

    protected P presenter;

    protected ImmersionBar mImmersionBar;
    private static final String TAG = BaseFragment.class.getSimpleName();
    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private View rootView;


    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //
    /*为了保证加载之后的布局复用，需要在viewpager的daapter中重写destroy方法，去除super
        @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }*/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(setContentView(), container, false);
        ButterKnife.bind(this, rootView);
        /**初始化的时候去加载数据**/
        presenter = initPresenter();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }

        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (getUserVisibleHint()) {
            if (isFirstVisible) {
                onFragmentFirstVisible();
                isFirstVisible = false;
            }
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
        return rootView;
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
    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int setContentView();

    public abstract P initPresenter();

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }


    @Override
    public void onResume() {
        super.onResume();
        // 在Activity中初始化P，并且连接V
        presenter.attach((V) this);
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        initVariable();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
        //在onDestroy()生命周期中释放P中引用的V。
        presenter.detach();
        //在onDestroy()生命周期中取消所有子线程里面的网络连接。
        OkGo.getInstance().cancelTag(getActivity());
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }

    /**
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可用于加载数据，防止每次进入都重复加载数据
     */
    protected abstract void onFragmentFirstVisible();

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }
    protected boolean isFirstVisible() {
        return isFirstVisible;
    }
    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T getId(int id) {

        return (T) getContentView().findViewById(id);
    }

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return rootView;
    }

    @Override
    public void showProgress() {
        ShowDialog.showDialog(getActivity(), "", true, null);
    }

    @Override
    public void hideProgress() {
        ShowDialog.dissmiss();
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

    }

    @Override
    public void hideErrorLayout() {

    }


}
