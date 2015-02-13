package com.jigdraw.draw.util;

import android.content.ContentValues;

import com.jigdraw.draw.model.ImageEntity;

import static com.jigdraw.draw.util.Base64Util.bitMapToBase64;
import static com.jigdraw.draw.util.DBUtil.DESC_COLUMN;
import static com.jigdraw.draw.util.DBUtil.IMAGE_COLUMN;
import static com.jigdraw.draw.util.DBUtil.NAME_COLUMN;
import static com.jigdraw.draw.util.DBUtil.ORIGINAL_COLUMN;

/**
 * Utilities class to convert from {@link ImageEntity} to {@link ContentValues}
 * for database persistence.
 *
 * @author Jay Paulynice
 */
public class EntityUtil {

    /**
     * Convert the given image entity to content values
     *
     * @param entity the entity to convert
     * @return content values object
     */
    public static ContentValues entityToContentValues(ImageEntity entity) {
        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, entity.getName());
        cv.put(IMAGE_COLUMN, bitMapToBase64(entity.getImage()));
        cv.put(DESC_COLUMN, entity.getDesc());
        cv.put(ORIGINAL_COLUMN, entity.getOriginalId());

        return cv;
    }
}
