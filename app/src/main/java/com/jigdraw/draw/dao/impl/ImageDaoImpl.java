package com.jigdraw.draw.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.db.ImageDB;
import com.jigdraw.draw.util.Base64Util;

import static com.jigdraw.draw.util.DBUtil.DATABASE_NAME;
import static com.jigdraw.draw.util.DBUtil.DATABASE_VERSION;
import static com.jigdraw.draw.util.DBUtil.DESC_COLUMN;
import static com.jigdraw.draw.util.DBUtil.IMAGE_COLUMN;
import static com.jigdraw.draw.util.DBUtil.NAME_COLUMN;
import static com.jigdraw.draw.util.DBUtil.TABLE_NAME;

/**
 * Default implementation for {@link com.jigdraw.draw.dao.ImageDao} layer for image storage
 * and retrieval
 * <p/>
 * Created by Jay Paulynice
 */
public class ImageDaoImpl implements ImageDao {
    private final ImageDB mdb;

    /**
     * Create new dao object with given context
     *
     * @param context the application context
     */
    public ImageDaoImpl(Context context) {
        mdb = new ImageDB(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void saveImageInDB(Bitmap bitmap, String id, String desc) {
        SQLiteDatabase db = mdb.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, id);
        cv.put(IMAGE_COLUMN, Base64Util.bitMapToBase64(bitmap));
        cv.put(DESC_COLUMN, desc);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    @Override
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