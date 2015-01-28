package com.jigdraw.draw.dao;

import android.graphics.Bitmap;

/**
 * Persistence interface for image storage and retrieval
 * <p/>
 * Created by Jay Paulynice
 */
public interface ImageDao {

    /**
     * Save the bitmap image in database by first converting to base 64
     * representation then save
     *
     * @param bitmap image to save
     * @param id     unique id for the image
     * @param desc   description
     */
    public void saveImageInDB(Bitmap bitmap, String id, String desc);

    /**
     * Retrieve base64 representation then convert to bitmap image
     *
     * @param col columns for query
     * @return bitmap image
     */
    public Bitmap getImageFromDB(String[] col);
}