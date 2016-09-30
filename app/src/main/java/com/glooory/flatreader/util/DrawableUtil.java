package com.glooory.flatreader.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by Glooory on 2016/9/30 0030 8:30.
 */

public class DrawableUtil {

    public static Drawable getTintListDrawable(Context context, int drawableResId, int resTintId) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawableResId).mutate());
        DrawableCompat.setTintList(drawable, ContextCompat.getColorStateList(context, resTintId));
        return drawable;
    }

    public static Drawable getTintDrawable(Context context, int drawableResId, @ColorInt int tint) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawableResId).mutate());
        DrawableCompat.setTint(drawable, tint);
        return drawable;
    }

}
