package com.jigdraw.draw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jigdraw.draw.util.DBUtil;

import static com.jigdraw.draw.util.DBUtil.DATABASE_NAME;
import static com.jigdraw.draw.util.DBUtil.DATABASE_VERSION;


/**
 * Image database class that extends the {@link SQLiteOpenHelper}
 * for database manipulations.
 *
 * @author Jay Paulynice
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    /**
     * Create new image db helper with the following parameters
     *
     * @param context the application context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "creating table jigsaw_images...");
        db.execSQL(DBUtil.CREATE_JIGSAW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from " + oldVersion + "to " + newVersion);
        db.execSQL(DBUtil.DROP_JIGSAW_TABLE);
        onCreate(db);
    }
}