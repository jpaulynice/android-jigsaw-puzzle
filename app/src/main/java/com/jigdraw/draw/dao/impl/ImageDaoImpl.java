package com.jigdraw.draw.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.db.JigsawDB;
import com.jigdraw.draw.model.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import static com.jigdraw.draw.util.Base64Util.base64ToBitmap;
import static com.jigdraw.draw.util.DBUtil.ALL_COLUMNS;
import static com.jigdraw.draw.util.DBUtil.DESC_COLUMN;
import static com.jigdraw.draw.util.DBUtil.ID_COLUMN;
import static com.jigdraw.draw.util.DBUtil.ID_SELECTION;
import static com.jigdraw.draw.util.DBUtil.IMAGE_COLUMN;
import static com.jigdraw.draw.util.DBUtil.JIGSAW_TABLE;
import static com.jigdraw.draw.util.DBUtil.NAME_COLUMN;
import static com.jigdraw.draw.util.DBUtil.ORIGINAL_COLUMN;
import static com.jigdraw.draw.util.DBUtil.ORIGINAL_SELECTION;
import static com.jigdraw.draw.util.DBUtil.getIdArguments;
import static com.jigdraw.draw.util.EntityUtil.entityToContentValues;

/**
 * Default implementation for {@link com.jigdraw.draw.dao.ImageDao}. Provides
 * CRUD database operations for {@link com.jigdraw.draw.model.ImageEntity}
 *
 * @author Jay Paulynice
 */
public class ImageDaoImpl implements ImageDao {
    /** Class name for logging */
    private static final String TAG = "ImageDaoImpl";

    /** SQLite database */
    private SQLiteDatabase db;

    /**
     * Create new dao object with given context
     *
     * @param context the application context
     */
    public ImageDaoImpl(Context context) {
        JigsawDB mdb = new JigsawDB(context);
        db = mdb.getWritableDatabase();
    }

    @Override
    public Long create(ImageEntity entity) {
        Long id = db.insert(JIGSAW_TABLE, null, entityToContentValues(entity));
        Log.d(TAG, "successfully saved image...id: " + id);

        return id;
    }

    @Override
    public List<ImageEntity> findTiles(Long id) {
        Cursor cursor = db.query(JIGSAW_TABLE, ALL_COLUMNS, ORIGINAL_SELECTION,
                getIdArguments(id), null, null, null);
        return getAllFromCursor(cursor);
    }

    @Override
    public ImageEntity find(Long id) {
        Cursor cursor = db.query(JIGSAW_TABLE, ALL_COLUMNS, ID_SELECTION,
                getIdArguments(id), null, null, null);

        return getEntityFromCursor(cursor);
    }

    @Override
    public int update(ImageEntity entity) {
        Log.d(TAG, "Updating entity with id: " + entity.getId());
        ContentValues cv = entityToContentValues(entity);
        return db.update(JIGSAW_TABLE, cv, ID_SELECTION,
                getIdArguments(entity.getId()));
    }

    @Override
    public int delete(Long id) {
        Log.d(TAG, "Deleting entity with id: " + id);
        return db.delete(JIGSAW_TABLE, ID_SELECTION, getIdArguments(id));
    }

    @Override
    public List<ImageEntity> findAllOriginals() {
        Cursor cursor = db.query(JIGSAW_TABLE, ALL_COLUMNS,
                "original is null",
                null, null, null, null);

        return getAllFromCursor(cursor);
    }


    private ImageEntity getEntityFromCursor(Cursor cursor) {
        ImageEntity entity = null;
        if (cursor != null && cursor.moveToFirst()) {
            entity = getEntity(cursor);
            cursor.close();
        }
        return entity;
    }

    private ImageEntity getEntity(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(NAME_COLUMN));
        String base64String = cursor.getString(cursor
                .getColumnIndex(IMAGE_COLUMN));
        String desc = cursor.getString(cursor.getColumnIndex(DESC_COLUMN));
        Long originalId = cursor.getLong(cursor.getColumnIndex
                (ORIGINAL_COLUMN));
        Long id = cursor.getLong(cursor.getColumnIndex(ID_COLUMN));

        Log.d(TAG, "image entity found with name: " + name);
        ImageEntity entity = new ImageEntity(base64ToBitmap(base64String),
                name, desc, originalId);
        entity.setId(id);

        return entity;
    }

    private List<ImageEntity> getAllFromCursor(Cursor cursor) {
        List<ImageEntity> entities = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ImageEntity entity = getEntity(cursor);
                entities.add(entity);
            }
            cursor.close();
        }
        return entities;
    }
}