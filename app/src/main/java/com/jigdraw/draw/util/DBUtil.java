package com.jigdraw.draw.util;

/**
 * Constants for database and tables
 *
 * @author Jay Paulynice
 */
public class DBUtil {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jigsaw.db";
    public static final String JIGSAW_TABLE = "jigsaw_images";
    public static final String DROP_JIGSAW_TABLE =
            "drop table " + JIGSAW_TABLE + " if exists;";
    public static final String NAME_COLUMN = "name";
    public static final String IMAGE_COLUMN = "img";
    public static final String DESC_COLUMN = "desc";
    public static final String ORIGINAL_COLUMN = "original";
    public static final String CREATE_JIGSAW_TABLE = "create table if not " +
            "exists " + JIGSAW_TABLE +
            "(" + NAME_COLUMN + " TEXT, " +
            "(" + IMAGE_COLUMN + " TEXT, " +
            "(" + DESC_COLUMN + " TEXT, " +
            "(" + ORIGINAL_COLUMN + " INTEGER, ";
    public static String ID_SELECTION = "rowid = ?";

    public static String ORIGINAL_SELECTION = "original = ?";

    public static String[] getIdSelection(final long id) {
        return new String[]{String.valueOf(id)};
    }
}