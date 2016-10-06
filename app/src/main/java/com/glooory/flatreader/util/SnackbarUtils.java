package com.glooory.flatreader.util;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Glooory on 2016/10/6 0006 17:46.
 */

public class SnackbarUtils {

    private static final int color_error = 0xffa94442;
    private static final int color_success = 0xff3c763d;
    private static final int color_info = 0xff31708f;
    private static final int color_warning = 0xff8a6d3b;

    private static final int action_color = 0xffCDC5BF;

    private Snackbar mSnackbar;

    private SnackbarUtils(Snackbar snackbar) {
        mSnackbar = snackbar;
    }

    public static SnackbarUtils makeShort(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        return new SnackbarUtils(snackbar);
    }

    public static SnackbarUtils makeLong(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        return new SnackbarUtils(snackbar);
    }

    private View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;

    }


    private Snackbar setSnackBarBackColor(int colorId) {
        View snackBarView = getSnackBarLayout(mSnackbar);
        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
        }
        return mSnackbar;
    }

    public void info() {
        setSnackBarBackColor(color_info);
        show();
    }

    public void info(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_info);
        show(actionText, listener);
    }

    public void warning() {
        setSnackBarBackColor(color_warning);
        show();
    }

    public void warning(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_warning);
        show(actionText, listener);
    }

    public void error() {
        setSnackBarBackColor(color_error);
        show();
    }

    public void error(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_error);
        show(actionText, listener);
    }

    public void confirm() {
        setSnackBarBackColor(color_success);
        show();
    }

    public void confirm(String actionText, View.OnClickListener listener) {
        setSnackBarBackColor(color_success);
        show(actionText, listener);
    }

    public void show() {
        mSnackbar.show();
    }

    public void show(String actionText, View.OnClickListener listener) {
        mSnackbar.setActionTextColor(action_color);
        mSnackbar.setAction(actionText, listener).show();
    }

}
