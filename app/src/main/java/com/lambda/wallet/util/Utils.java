package com.lambda.wallet.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.lambda.wallet.R;

import java.util.List;

import me.ljp.permission.HiPermission;
import me.ljp.permission.PermissionCallback;
import me.ljp.permission.PermissionItem;


/**
 * Utils初始化相关
 */
public final class Utils {

    private static Context context;
    private static SPUtils spUtils;


    private Utils() {
        throw new UnsupportedOperationException("...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
        spUtils = new SPUtils("USER");
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext context
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException(context.getString(R.string.error_application));
    }

    /**
     * Gets sp utils.
     *
     * @return the sp utils
     */
    public static SPUtils getSpUtils() {
        return spUtils;
    }

    public static boolean getPermissions(List<PermissionItem> permissonItems, String msg) {
        final Boolean[] isGetPermissions = {false};
        HiPermission.create(context)
                .permissions(permissonItems)
                .title(context.getResources().getString(R.string.dear_user))
                .msg(msg)
                .animStyle(R.style.PermissionAnimScale)
                .style(R.style.PermissionThemeStyle)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {
                        isGetPermissions[0] = false;
                        ToastUtils.showShortToast(context.getResources().getString(R.string.close_permisson_toast));
                    }

                    @Override
                    public void onFinish() {
                        isGetPermissions[0] = true;
                    }

                    @Override
                    public void onDeny(String permisson, int position) {
                    }

                    @Override
                    public void onGuarantee(String permisson, int position) {
                    }
                });
        return isGetPermissions[0];
    }
    public static int getScreenWidth() {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    public static boolean calculateShowCheckAllText(String content) {
        Paint textPaint = new Paint();
        textPaint.setTextSize(DensityUtil.dip2px(context,16f));
        float textWidth = textPaint.measureText(content);
        float maxContentViewWidth = Utils.getScreenWidth() -DensityUtil.dip2px(context,74f);
        float maxLines = textWidth / maxContentViewWidth;
        return maxLines > 4;
    }
    public static int calcStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
    /**
     * 获取app版本名
     *
     * @param context the context
     * @return the string
     */
    public static String getAppVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void fitsSystemWindow(Activity activity, View otherView) {
        boolean adjustByRoot = false;
        final View content = activity.findViewById(android.R.id.content);
        if (content instanceof ViewGroup) {
            final View root = ((ViewGroup) content).getChildAt(0);
            if (root != null) {
                boolean fitsSystemWindows = ViewCompat.getFitsSystemWindows(root);
                if (fitsSystemWindows) {
                    otherView.setPadding(root.getPaddingLeft(), root.getPaddingTop(), root.getPaddingRight(), root.getPaddingBottom());
                    adjustByRoot = true;
                }
            }
        }
        if (!adjustByRoot) {
            ViewCompat.requestApplyInsets(otherView);
        }
    }
    public static String getBaseUrl(String utl) {
        if (TextUtils.isEmpty(utl)) {
            return "";
        }
        String result = utl;
        if (utl.indexOf("?") == -1) {
            result += "?";
        } else if (utl.lastIndexOf("?") != utl.length() - 1 && utl.charAt(utl.length() - 1) != '&') {
            result += "&";
        }
        return result;
    }

    public static String ByteArrayToHexString(byte[] bytes) {
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] HexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Hex string must have even number of characters");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
