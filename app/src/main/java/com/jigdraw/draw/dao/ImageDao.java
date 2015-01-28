package com.jigdraw.draw.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.jigdraw.draw.db.ImageDB;
import com.jigdraw.draw.util.Base64Util;

/**
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

    public ImageDao(Context context) {
        mdb = new ImageDB(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void saveImageInDB(Bitmap bitmap, String id, String desc) {
        SQLiteDatabase db = mdb.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, id);
        cv.put(IMAGE_COLUMN, Base64Util.bitMapToBase64(bitmap));
        cv.put(DESC_COLUMN, desc);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

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