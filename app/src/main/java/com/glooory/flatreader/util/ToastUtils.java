package com.glooory.flatreader.util;

import android.content.Context;
import android.widget.Toast;

import com.glooory.flatreader.base.MyApplication;

/**
 * Created by Glooory on 2016/10/1 0001 9:49.
 */

public class ToastUtils {

    private static Toast mToast = null;

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(CharSequence text) {
        showToast(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(int textId) {
        String text = MyApplication.getInstance().getString(textId);
        showToast(MyApplication.getInstance(), text, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(CharSequence text) {
        showToast(MyApplication.getInstance(), text, Toast.LENGTH_LONG);
    }

    public static void showToastLong(int textId) {
        String text = MyApplication.getInstance().getString(textId);
        showToast(MyApplication.getInstance(), text, Toast.LENGTH_LONG);
    }

    /**
     * 非阻塞式显示Toast，防止出现连续显示Toast的问题
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(Context context, CharSequence text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration);
        } else {
            mToast.setText(text);
            mToast.setDuration(duration);
        }
        mToast.show();
    }

}
