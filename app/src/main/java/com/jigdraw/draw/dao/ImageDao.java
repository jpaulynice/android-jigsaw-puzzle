package com.jigdraw.draw.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.jigdraw.draw.db.ImageDB;
import com.jigdraw.draw.util.Base64Util;

/**
 * Created by Jay Paulynice
 */
public class ImageDao {
    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ImageDb";
    private static final String TABLE_NAME = "images";
    private Context context;
    private SQLiteDatabase db;


    public ImageDao(Context context) {
        this.context = context;
        ImageDB mdb = new ImageDB(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = mdb.getWritableDatabase();
    }

    public void saveImageInDB(Bitmap bitmap, String name) {
        ContentValues cv = new ContentValues();
        cv.put(name, Base64Util.bitMapToBase64(bitmap));
        db.insert(TABLE_NAME, null, cv);
        Toast.makeText(context, "inserted successfully", Toast.LENGTH_SHORT).show();
    }

    public Bitmap getImageFromDB(String[] col) {
        Cursor cursor = db.query(TABLE_NAME, col, null, null, null, null, null);
        String base64String = null;

        if (cursor != null) {
            cursor.moveToFirst();
            do {
                base64String = cursor.getString(cursor.getColumnIndex("image"));
            } while (cursor.moveToNext());
        }

        return base64String != null ? Base64Util.base64ToBitmap(base64String) : null;
    }
}
