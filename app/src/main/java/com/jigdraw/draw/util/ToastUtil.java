package com.jigdraw.draw.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Utils for toasting messages
 * *
 *
 * @author Jay Paulynice
 */
public class ToastUtil {
    /**
     * Create short toast and show message
     *
     * @param context the context
     * @param message the message
     */
    public static void shortToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message,
                Toast.LENGTH_SHORT);
        toast.show();
    }
}