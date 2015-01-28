package com.jigdraw.draw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.jigdraw.draw.util.DBUtil.DATABASE_NAME;
import static com.jigdraw.draw.util.DBUtil.DATABASE_VERSION;


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
     */
    public ImageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table jigsaw_images if not exists (name TEXT, img TEXT, desc TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table jigsaw_images if exists;");
        onCreate(db);
    }
}