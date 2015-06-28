package com.jigdraw.draw.service;

import com.jigdraw.draw.model.ImageEntity;

import java.util.List;

/**
 * Simple interface for dao wrapper
 *
 * @author Jay Paulynice
 */
public interface ImageService {
    public Long insert(ImageEntity entity);

    public ImageEntity query(Long id);

    public List<ImageEntity> findTiles(Long id);

    public boolean update(ImageEntity entity);

    public boolean delete(Long id);
}