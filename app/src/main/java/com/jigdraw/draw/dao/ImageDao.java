package com.jigdraw.draw.dao;

import com.jigdraw.draw.model.ImageEntity;

/**
 * Simple interface for {@link ImageEntity} CRUD operations
 *
 * @author Jay Paulynice
 */
public interface ImageDao {
    public long create(ImageEntity entity);

    public ImageEntity find(long id);

    public int update(ImageEntity entity);

    public int delete(long id);
}