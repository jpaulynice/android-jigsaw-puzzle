package com.jigdraw.draw.util;

/**
 * Constants for database, table and columns
 *
 * @author Jay Paulynice
 */
public class DBUtil {
    /** database version */
    public static final int DATABASE_VERSION = 1;

    /** database name */
    public static final String DATABASE_NAME = "jigsaw.db";

    /** jigsaw table */
    public static final String JIGSAW_TABLE = "jigsaw_images";

    /** table columns */
    public static final String NAME_COLUMN = "name";
    public static final String IMAGE_COLUMN = "img";
    public static final String DESC_COLUMN = "desc";
    public static final String ORIGINAL_COLUMN = "original";

    /** create jigsaw_images table */
    public static final String CREATE_JIGSAW_TABLE = "create table if not" +
            " exists " + JIGSAW_TABLE + " (" +
            NAME_COLUMN + " TEXT," +
            IMAGE_COLUMN + " TEXT," +
            DESC_COLUMN + " TEXT," +
            ORIGINAL_COLUMN + " INTEGER);";

    /** drop jigsaw_images table */
    public static final String DROP_JIGSAW_TABLE =
            "drop table " + JIGSAW_TABLE + " if exists;";

    /** for querying like in prepared statements */
    public static final String ID_SELECTION = "rowid = ?";
    public static final String ORIGINAL_SELECTION = "original = ?";

    /** arguments to set for the prepared statements */
    public static String[] getIdArguments(final long id) {
        return new String[]{String.valueOf(id)};
    }
}