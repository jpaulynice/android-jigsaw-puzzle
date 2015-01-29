package com.jigdraw.draw.util;

import android.content.ContentValues;

import com.jigdraw.draw.model.ImageEntity;

import static com.jigdraw.draw.util.Base64Util.bitMapToBase64;
import static com.jigdraw.draw.util.DBUtil.DESC_COLUMN;
import static com.jigdraw.draw.util.DBUtil.IMAGE_COLUMN;
import static com.jigdraw.draw.util.DBUtil.NAME_COLUMN;

/**
 * @author Jay Paulynice
 */
public class EntityUtil {

    public static ContentValues entityToContentValues(ImageEntity entity) {
        ContentValues cv = new ContentValues();
        cv.put(NAME_COLUMN, entity.getName());
        cv.put(IMAGE_COLUMN, bitMapToBase64(entity.getImage()));
        cv.put(DESC_COLUMN, entity.getDesc());

        return cv;
    }
}
