package com.jigdraw.draw.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Utilities class for converting image from base 64 to bitmap and bitmap to
 * base 64
 *
 * @author Jay Paulynice
 */
public class Base64Util {

    /**
     * Convert bitmap to base 64 string
     *
     * @param bitmap the bitmap to convert
     * @return base64 string
     */
    public static String bitMapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] byteArray = os.toByteArray();
        return Base64.encodeToString(byteArray, 0);
    }

    /**
     * Convert base 64 string to bitmap
     *
     * @param base64String the string to convert
     * @return bitmap image
     */
    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
    }
}