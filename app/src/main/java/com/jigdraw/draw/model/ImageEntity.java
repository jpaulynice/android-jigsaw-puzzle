/*
 * Copyright (c) 2018. Jay Paulynice (jay.paulynice@gmail.com)
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

package com.jigdraw.draw.model;

import android.graphics.Bitmap;

import com.jigdraw.draw.util.Base64Util;

/**
 * Entity to encapsulate image attributes
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public class ImageEntity {
    private Bitmap image;
    private String name;
    private String desc;
    private Long originalId;
    private Long id;

    public ImageEntity() {

    }

    /**
     * Create new image entity given the parameters
     *
     * @param image the image as bitmap
     * @param name the name
     * @param desc the description
     */
    public ImageEntity(Bitmap image, String name, String desc, Long originalId) {
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.originalId = originalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOriginalId() {
        return originalId;
    }

    public void setOriginalId(Long originalId) {
        this.originalId = originalId;
    }

    @Override
    public int hashCode() {
        int result = image.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + desc.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ImageEntity))
            return false;

        ImageEntity entity = (ImageEntity) o;

        return desc.equals(entity.desc) && image.equals(entity.image)
                && name.equals(entity.name);
    }

    @Override
    public String toString() {
        return "ImageEntity{" + "image string="
                + Base64Util.bitMapToBase64(image) + ", name='" + name + '\''
                + ", desc='" + desc + '\'' + '}';
    }
}
