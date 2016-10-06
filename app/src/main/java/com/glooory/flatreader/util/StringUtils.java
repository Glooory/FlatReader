package com.glooory.flatreader.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Glooory on 2016/10/6 0006 17:28.
 */

public class StringUtils {

    /**
     * 复制到粘贴板
     * @param context
     * @param text
     * @param success
     */
    public static void copyToClipBoardToast(Context context, String text, String success) {
        ClipData clipData = ClipData.newPlainText("flat_reader", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }

    public static void copyToClipBoardSnackbar(Context context, View view, String text, String success) {
        ClipData clipData = ClipData.newPlainText("flat_reader", text);
        ClipboardManager manager = (ClipboardManager) context.getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        SnackbarUtils.makeShort(view, success).confirm();
    }

}
