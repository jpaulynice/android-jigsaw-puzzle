/*
 * Copyright (c) 2015. Jay Paulynice (jay.paulynice@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jigdraw.draw.db;

import static com.jigdraw.draw.util.DBUtil.DATABASE_NAME;
import static com.jigdraw.draw.util.DBUtil.DATABASE_VERSION;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jigdraw.draw.util.DBUtil;


/**
 * Database helper that extends the {@link SQLiteOpenHelper} for database
 * manipulations.
 *
 * @author Jay Paulynice
 */
public class JigsawDB extends SQLiteOpenHelper {
    /** tag name for logging */
    private static final String TAG = "DBHelper";

    /**
     * Create new db helper given application context
     *
     * @param context the application context
     */
    public JigsawDB(Context context) {
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