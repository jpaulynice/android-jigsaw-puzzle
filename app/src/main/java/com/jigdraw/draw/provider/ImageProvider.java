package com.jigdraw.draw.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.jigdraw.draw.db.JigsawDB;

import java.util.HashMap;

import static com.jigdraw.draw.util.DBUtil.ID_SELECTION;
import static com.jigdraw.draw.util.DBUtil.JIGSAW_TABLE;

/**
 * @author Jay Paulynice
 */
public class ImageProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.jigdraw.draw.provider.jigsaw";
    static final String URL = "content://" + PROVIDER_NAME + "/jigsaw_images";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int IMAGES = 1;
    static final int IMAGES_ID = 2;
    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "jigsaw_images", IMAGES);
        uriMatcher.addURI(PROVIDER_NAME, "jisaw_images/#", IMAGES_ID);
    }

    private static HashMap<String, String> map;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        JigsawDB dbHelper = new JigsawDB(getContext());
        db = dbHelper.getWritableDatabase();

        return db != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(JIGSAW_TABLE);

        switch (uriMatcher.match(uri)) {
            case IMAGES:
                queryBuilder.setProjectionMap(map);
                break;
            case IMAGES_ID:
                queryBuilder.appendWhere(ID_SELECTION + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = db.insert(JIGSAW_TABLE, "", values);

        if (row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count;

        switch (uriMatcher.match(uri)) {
            case IMAGES:
                count = db.update(JIGSAW_TABLE, values, selection, selectionArgs);
                break;
            case IMAGES_ID:
                count = db.update(
                        JIGSAW_TABLE,
                        values,
                        ID_SELECTION
                                + uri.getLastPathSegment()
                                + (!TextUtils.isEmpty(selection) ? " AND ("
                                + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count;

        switch (uriMatcher.match(uri)) {
            case IMAGES:
                count = db.delete(JIGSAW_TABLE, selection, selectionArgs);
                break;
            case IMAGES_ID:
                String id = uri.getLastPathSegment();
                count = db.delete(JIGSAW_TABLE,
                        ID_SELECTION
                                + id
                                + (!TextUtils.isEmpty(selection) ? " AND ("
                                + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case IMAGES:
                return "vnd.android.cursor.dir/vnd.example.jigsaw_images";
            case IMAGES_ID:
                return "vnd.android.cursor.item/vnd.example.jigsaw_images";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}