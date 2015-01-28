package com.jigdraw.draw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.jigdraw.draw.util.DBUtil.DATABASE_NAME;
import static com.jigdraw.draw.util.DBUtil.DATABASE_VERSION;


/**
 * Image database class that extends the {@link SQLiteOpenHelper}
 * for database manipulations.
 * <p/>
 * Created by Jay Paulynice
 */
public class ImageDB extends SQLiteOpenHelper {
    private static final String TAG = "ImageDB";

    /**
     * Create new image db object with the following parameters
     *
     * @param context the application context
     */
    public ImageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "creating table jigsaw_images...");
        db.execSQL("create table if not exists jigsaw_images (name TEXT, img TEXT, desc TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "dropping table jigsaw_images...");
        db.execSQL("drop table jigsaw_images if exists;");
        onCreate(db);
    }
}