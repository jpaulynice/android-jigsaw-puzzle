package com.jigdraw.draw.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.db.ImageDB;
import com.jigdraw.draw.model.ImageEntity;

import static com.jigdraw.draw.util.Base64Util.base64ToBitmap;
import static com.jigdraw.draw.util.Base64Util.bitMapToBase64;
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
    public void create(ImageEntity entity) {
        SQLiteDatabase db = mdb.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, entity.getName());
        cv.put(IMAGE_COLUMN, bitMapToBase64(entity.getImage()));
        cv.put(DESC_COLUMN, entity.getDesc());

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    @Override
    public ImageEntity find(int id) {
        SQLiteDatabase db = mdb.getReadableDatabase();
        String[] col = new String[]{NAME_COLUMN, IMAGE_COLUMN, DESC_COLUMN};
        Cursor cursor = db.query(TABLE_NAME, col, null, null, null, null, null);

        ImageEntity entity = null;
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
            String base64String = cursor.getString(cursor.getColumnIndex(IMAGE_COLUMN));
            String desc = cursor.getString(cursor.getColumnIndex(DESC_COLUMN));

            entity = new ImageEntity(base64ToBitmap(base64String), name, desc);
            cursor.close();
        }
        db.close();

        return entity;
    }

    @Override
    public ImageEntity update(ImageEntity entity) {
        //TODO: add implementation
        return null;
    }

    @Override
    public void delete(int id) {
        //TODO: add implementation
    }

    @Override
    public void delete(ImageEntity entity) {
        //TODO: add implementation
    }
}