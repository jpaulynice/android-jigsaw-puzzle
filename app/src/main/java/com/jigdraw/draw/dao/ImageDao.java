package com.jigdraw.draw.dao;

import com.jigdraw.draw.model.ImageEntity;

import java.util.List;

/**
 * Simple interface for {@link ImageEntity} CRUD operations
 *
 * @author Jay Paulynice
 */
public interface ImageDao {
    public Long create(ImageEntity entity);

    public ImageEntity find(Long id);

    public List<ImageEntity> findTiles(Long id);

    public int update(ImageEntity entity);

    public int delete(Long id);
}