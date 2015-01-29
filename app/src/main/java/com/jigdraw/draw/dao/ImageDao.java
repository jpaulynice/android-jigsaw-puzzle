package com.jigdraw.draw.dao;

import com.jigdraw.draw.model.ImageEntity;

/**
 * Persistence interface for image storage and retrieval
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
    public ImageEntity find(int id);

    /**
     * Update given image entity
     *
     * @param entity image to update
     * @return update image
     */
    public ImageEntity update(ImageEntity entity);

    /**
     * Delete the image entity with the given id
     *
     * @param id the id of the image to delete
     */
    public void delete(int id);

    /**
     * Delete the specified image entity
     *
     * @param entity image to delete
     */
    public void delete(ImageEntity entity);
}