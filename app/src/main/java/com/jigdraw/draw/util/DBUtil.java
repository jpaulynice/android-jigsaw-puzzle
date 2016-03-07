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

package com.jigdraw.draw.util;

/**
 * Constants for database, table and columns
 *
 * @author Jay Paulynice
 */
public final class DBUtil {

    private DBUtil() {}

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jigsaw.db";
    public static final String JIGSAW_TABLE = "jigsaw_images";
    public static final String NAME_COLUMN = "name";
    public static final String ID_COLUMN = "id";
    public static final String IMAGE_COLUMN = "img";
    public static final String DESC_COLUMN = "desc";
    public static final String ORIGINAL_COLUMN = "original";

    /** create jigsaw_images table */
    public static final String CREATE_JIGSAW_TABLE = "create table if not"
            + " exists " + JIGSAW_TABLE + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME_COLUMN + " TEXT,"
            + IMAGE_COLUMN + " TEXT," + DESC_COLUMN + " TEXT,"
            + ORIGINAL_COLUMN + " INTEGER);";

    /** drop jigsaw_images table */
    public static final String DROP_JIGSAW_TABLE = "drop table if exists " +
            JIGSAW_TABLE;

    /** for querying like in prepared statements */
    public static final String ID_SELECTION = "id = ?";

    /** original image selection */
    public static final String ORIGINAL_SELECTION = "original = ?";

    /** original image selection is null */
    public static final String ORIGINAL_SELECTION_NULL = "original is null";

    /** all columns selection */
    public static String[] ALL_COLUMNS = new String[]{ID_COLUMN, NAME_COLUMN,
            IMAGE_COLUMN, DESC_COLUMN, ORIGINAL_COLUMN};

    /** arguments to set for the prepared statements */
    public static String[] getIdArguments(final Long id) {
        return new String[]{String.valueOf(id)};
    }
}