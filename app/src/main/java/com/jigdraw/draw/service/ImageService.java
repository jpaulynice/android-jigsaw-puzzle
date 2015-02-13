package com.jigdraw.draw.service;

import com.jigdraw.draw.model.ImageEntity;

import java.util.List;

/**
 * Simple interface for dao wrapper
 *
 * @author Jay Paulynice
 */
public interface ImageService {
    public long insert(ImageEntity entity);

    public ImageEntity query(long id);

    public List<ImageEntity> findTiles(long id);

    public boolean update(ImageEntity entity);

    public boolean delete(long id);
}