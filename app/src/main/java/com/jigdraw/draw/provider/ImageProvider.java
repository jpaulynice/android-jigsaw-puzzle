package com.jigdraw.draw.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.jigdraw.draw.db.DBHelper;

/**
 * @author Jay Paulynice
 */
public class ImageProvider extends ContentProvider {
    private DBHelper dbHelper;

    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues cv) {
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }


    @Override
    public int update(Uri uri, ContentValues values, String whereClause,
                      String[] whereArgs) {
        return 0;
    }

    @Override
    public int delete(Uri uri, String where, String[] args) {
        return 0;
    }

    @Override
    public String getType(Uri arg0) {
        return null;
    }
}
