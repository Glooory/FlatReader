package com.glooory.flatreader.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.glooory.flatreader.BuildConfig;
import com.glooory.flatreader.constants.Constants;

/**
 * Created by Glooory on 2016/10/31 0031 22:28.
 */

public class SPUtils {
    public static final String SP_NAME = "share_data";

    public static boolean isDonnotRemindAnymore(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(Constants.DONNOT_REMIND_ANYMORE, false);
    }

    public static int getLastUpdateVersion(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(Constants.NEWEST_VERSION_CODE, BuildConfig.VERSION_CODE);
    }

}
