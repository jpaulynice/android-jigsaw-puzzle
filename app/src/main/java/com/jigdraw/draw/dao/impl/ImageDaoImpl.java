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

package com.jigdraw.draw.dao.impl;

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
import static com.jigdraw.draw.util.DBUtil.ORIGINAL_SELECTION_NULL;
import static com.jigdraw.draw.util.DBUtil.getIdArguments;
import static com.jigdraw.draw.util.EntityUtil.entityToContentValues;

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

/**
 * Default implementation for {@link com.jigdraw.draw.dao.ImageDao}. Provides CRUD database operations for {@link
 * com.jigdraw.draw.model.ImageEntity}
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
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
    public ImageEntity find(Long id) {
        Cursor cursor = db.query(JIGSAW_TABLE, ALL_COLUMNS, ID_SELECTION, getIdArguments(id), null, null, null);
        ImageEntity entity = getEntityFromCursor(cursor);
        cleanUp(cursor);

        return entity;
    }

    @Override
    public List<ImageEntity> findTiles(Long id) {
        List<ImageEntity> entities = new ArrayList<>();
        Cursor cursor = db.query(JIGSAW_TABLE, ALL_COLUMNS, ORIGINAL_SELECTION, getIdArguments(id), null, null, null);
        entities.addAll(getAllFromCursor(cursor));
        cleanUp(cursor);

        Log.d(TAG, "Found " + entities.size() + " tiles for the original id "
                + id);
        return entities;
    }

    @Override
    public int update(ImageEntity entity) {
        Log.d(TAG, "Updating entity with id: " + entity.getId());
        ContentValues cv = entityToContentValues(entity);

        return db.update(JIGSAW_TABLE, cv, ID_SELECTION, getIdArguments(entity.getId()));
    }

    @Override
    public int delete(Long id) {
        Log.d(TAG, "Deleting entity with id: " + id);
        return db.delete(JIGSAW_TABLE, ID_SELECTION, getIdArguments(id));
    }

    @Override
    public List<ImageEntity> getHistory() {
        Cursor cursor = db.query(JIGSAW_TABLE, ALL_COLUMNS, ORIGINAL_SELECTION_NULL, null, null, null, null);

        List<ImageEntity> entities = getAllFromCursor(cursor);
        cleanUp(cursor);

        Log.d(TAG, "Found " + entities.size() + " images from history");
        return entities;
    }

    private List<ImageEntity> getAllFromCursor(Cursor cursor) {
        List<ImageEntity> entities = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ImageEntity entity = getEntity(cursor);
                entities.add(entity);
            }
        }
        return entities;
    }

    private ImageEntity getEntityFromCursor(Cursor cursor) {
        ImageEntity entity = null;
        if (cursor != null && cursor.moveToFirst()) {
            entity = getEntity(cursor);
        }
        return entity;
    }

    private void cleanUp(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    private ImageEntity getEntity(Cursor cursor) {
        String name = cursor.getString(getIndex(cursor, NAME_COLUMN));
        String base64String = cursor.getString(getIndex(cursor, IMAGE_COLUMN));
        String desc = cursor.getString(getIndex(cursor, DESC_COLUMN));

        Long originalId = cursor.getLong(getIndex(cursor, ORIGINAL_COLUMN));
        Long id = cursor.getLong(getIndex(cursor, ID_COLUMN));

        Log.d(TAG, "image entity found with name: " + name);
        ImageEntity entity = new ImageEntity(base64ToBitmap(base64String), name, desc, originalId);
        entity.setId(id);

        return entity;
    }

    private int getIndex(final Cursor cursor, final String col) {
        return cursor.getColumnIndex(col);
    }
}
