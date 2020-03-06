package com.lambda.wallet.net;

import com.lambda.wallet.net.callbck.JsonCallback;
import com.lambda.wallet.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/**
 * app网络请求管理类
 */
public class HttpUtils {
    /**
     * Gets requets.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param tag      the tag
     * @param map      the map
     * @param callback the callback
     */
    public static <T> void getRequets(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {
        HttpHeaders headers = new HttpHeaders();
        url = Utils.getSpUtils().getString("url") + url;
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .headers(headers)
                .execute(callback);
    }

    public static <T> void getRequets(String url, Object tag, Map<String, String> map, StringCallback callback) {
        HttpHeaders headers = new HttpHeaders();
        url = Utils.getSpUtils().getString("url") + url;
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .headers(headers)
                .execute((Callback<T>) callback);
    }
    public static <T> void getRequets(String url, Object tag, JsonCallback<T> callback) {
        HttpHeaders headers = new HttpHeaders();
        OkGo.<T>get(url)
                .tag(tag)
                .params(new HashMap<>())
                .headers(headers)
                .execute((Callback<T>) callback);
    }
    /**
     * Post request.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param tag      the tag
     * @param parms    the parms
     * @param callback the callback
     */
    public static <T> void postRequest(String url, Object tag, String parms, JsonCallback<T> callback) {
        HttpHeaders headers = new HttpHeaders();
        url = Utils.getSpUtils().getString("url") + url;
        OkGo.<T>post(url)
                .tag(tag)
                .upJson(parms)
                .headers(headers)
                .execute(callback);
    }

    public static <T> void postRequest(String url, Object tag, String parms, StringCallback callback) {
        HttpHeaders headers = new HttpHeaders();
        url = Utils.getSpUtils().getString("url") + url;
        OkGo.<T>post(url)
                .tag(tag)
                .upJson(parms)
                .headers(headers)
                .execute((Callback<T>) callback);
    }

    /**
     * Post request.
     *
     * @param <T>      the type parameter
     * @param url      the url
     * @param tag      the tag
     * @param map      the map
     * @param callback the callback
     */
    public static <T> void postRequest(String url, Object tag, Map<String, String> map, JsonCallback<T> callback) {

        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }
}
