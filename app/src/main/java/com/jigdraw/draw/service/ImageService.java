package com.jigdraw.draw.service;

import com.jigdraw.draw.model.ImageEntity;

/**
 * Simple interface for dao wrapper
 *
 * @author Jay Paulynice
 */
public interface ImageService {
    /**
     * Save the given image entity
     *
     * @param entity the entity to save
     * @return id of the created entity
     */
    public long insert(ImageEntity entity);

    /**
     * Get image entity from db matching id
     *
     * @param id unique id for the image
     * @return image entity
     */
    public ImageEntity query(long id);

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
