package com.jigdraw.draw.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.jigdraw.draw.db.ImageDB;
import com.jigdraw.draw.util.Base64Util;

/**
 * Persistence layer for image storage and retrieval
 *
 * Created by Jay Paulynice
 */
public class ImageDao {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ImageDb";
    private static final String TABLE_NAME = "jigsaw_images";
    private static final String NAME_COLUMN = "name";
    private static final String IMAGE_COLUMN = "img";
    private static final String DESC_COLUMN = "desc";
    private ImageDB mdb;

    /**
     * Create new dao object with given context
     *
     * @param context the application context
     */
    public ImageDao(Context context) {
        mdb = new ImageDB(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Save the bitmap image in database by first converting to base 64
     * representation then save
     *
     * @param bitmap image to save
     * @param id     unique id for the image
     * @param desc   description
     */
    public void saveImageInDB(Bitmap bitmap, String id, String desc) {
        SQLiteDatabase db = mdb.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, id);
        cv.put(IMAGE_COLUMN, Base64Util.bitMapToBase64(bitmap));
        cv.put(DESC_COLUMN, desc);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    /**
     * Retrieve base64 representation then convert to bitmap image
     *
     * @param col columns for query
     * @return bitmap image
     */
    public Bitmap getImageFromDB(String[] col) {
        SQLiteDatabase db = mdb.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, col, null, null, null, null, null);
        String base64String = null;
        if (cursor != null) {
            cursor.moveToFirst();
            do {
                base64String = cursor.getString(cursor.getColumnIndex(IMAGE_COLUMN));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return base64String != null ? Base64Util.base64ToBitmap(base64String) : null;
    }
}