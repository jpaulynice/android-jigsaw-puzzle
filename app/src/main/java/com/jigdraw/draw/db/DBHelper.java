package com.jigdraw.draw.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jigdraw.draw.util.DBUtil;

import static com.jigdraw.draw.util.DBUtil.DATABASE_NAME;
import static com.jigdraw.draw.util.DBUtil.DATABASE_VERSION;


/**
 * Database helper that extends the {@link SQLiteOpenHelper} for database
 * manipulations.
 *
 * @author Jay Paulynice
 */
public class DBHelper extends SQLiteOpenHelper {
    /** tag name for logging */
    private static final String TAG = "DBHelper";

    /**
     * Create new db helper given application context
     *
     * @param context the application context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "creating table jigsaw_images if it doesn't exist");
        db.execSQL(DBUtil.CREATE_JIGSAW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Upgrading database from " + oldVersion + "to " +
                newVersion);
        db.execSQL(DBUtil.DROP_JIGSAW_TABLE);
        onCreate(db);
    }
}