package com.lambda.wallet.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by coder.
 * User: blue
 * Date: 2019/6/20
 * Time: 10:04
 */
public class EventBusUtil {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }

    // 其他
}

