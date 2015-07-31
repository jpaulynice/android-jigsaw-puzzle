/*
 * Copyright (c) 2015. Jay Paulynice (jay.paulynice@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jigdraw.draw.dao;

import com.jigdraw.draw.model.ImageEntity;

import java.util.List;

/**
 * Simple interface for {@link ImageEntity} CRUD operations
 *
 * @author Jay Paulynice
 */
public interface ImageDao {
    /**
     * Save an image entity
     *
     * @param entity the entity to create
     * @return the generated id
     */
    Long create(ImageEntity entity);

    /**
     * Find entity by id
     *
     * @param id the id of the entity
     * @return the matching entity
     */
    ImageEntity find(Long id);

    /**
     * Find the jigsaw tiles for the original id
     *
     * @param id the original image id
     * @return the jigsaw entities
     */
    List<ImageEntity> findTiles(Long id);

    /**
     * Update the given image entity
     *
     * @param entity the entity to update
     * @return number of rows updated
     */
    int update(ImageEntity entity);

    /**
     * Delete the entity by id
     *
     * @param id the id of the entity
     * @return number of rows deleted
     */
    int delete(Long id);

    /**
     * Find all the original images for history
     *
     * @return list of images user created
     */
    List<ImageEntity> getHistory();
}