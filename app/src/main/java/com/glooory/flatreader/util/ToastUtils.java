package com.glooory.flatreader.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Glooory on 2016/10/1 0001 9:49.
 */

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, CharSequence text) {
        showToast(context, text, Toast.LENGTH_SHORT);
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
