package com.jigdraw.draw.dao;

import com.jigdraw.draw.model.ImageEntity;

/**
 * Simple interface for {@link ImageEntity} data access
 *
 * @author Jay Paulynice
 */
public interface ImageDao {
    /**
     * Save the given image entity
     *
     * @param entity the entity to save
     * @return id of the created entity
     */
    public long create(ImageEntity entity);

    /**
     * Get image entity from db matching id
     *
     * @param id unique id for the image
     * @return image entity
     */
    public ImageEntity find(long id);

    /**
     * Update given image entity
     *
     * @param entity image to update
     * @return update image
     */
    public boolean update(ImageEntity entity);

    /**
     * Delete the image entity with the given id
     *
     * @param id the id of the image to delete
     */
    public boolean delete(long id);
}