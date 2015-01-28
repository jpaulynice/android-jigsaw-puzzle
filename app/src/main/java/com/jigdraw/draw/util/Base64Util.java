package com.jigdraw.draw.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Utilities class for converting image from base 64 to bitmap
 * and bitmap to base 64
 *
 * @author Jay Paulynice
 */
public class Base64Util {

    /**
     * Convert image to base 64 string from bitmap
     *
     * @param bitmap the image to convert to base 64
     * @return base64 representation of the bitmap
     */
    public static String bitMapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] byteArray = os.toByteArray();
        return Base64.encodeToString(byteArray, 0);
    }

    /**
     * Convert base 64 string to bitmap image
     *
     * @param base64String the string to convert to bitmap
     * @return bitmap representation of the base 64 string
     */
    public static Bitmap base64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}