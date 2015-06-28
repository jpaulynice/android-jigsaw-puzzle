package com.jigdraw.draw.model;

import android.graphics.Bitmap;

import com.jigdraw.draw.util.Base64Util;

/**
 * Entity to encapsulate image attributes
 *
 * @author Jay Paulynice
 */
public class ImageEntity {
    private final Bitmap image;
    private final String name;
    private final String desc;
    private final Long originalId;
    private Long id;

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

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public Long getOriginalId() {
        return originalId;
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
    public int hashCode() {
        int result = image.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + desc.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ImageEntity{" + "image string="
                + Base64Util.bitMapToBase64(image) + ", name='" + name + '\''
                + ", desc='" + desc + '\'' + '}';
    }
}
