package com.jigdraw.draw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Image database class that extends the {@link SQLiteOpenHelper}
 * for database manipulations.
 * <p/>
 * Created by Jay Paulynice
 */
public class ImageDB extends SQLiteOpenHelper {

    /**
     * Create new image db object with the following parameters
     *
     * @param context the application context
     * @param name the database name
     * @param factory the sqlite cursor factory
     * @param version the database version
     */
    public ImageDB(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table jigsaw_images (name TEXT, img TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: what to do here?
    }
}