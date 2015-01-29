package com.jigdraw.draw.model;

import android.graphics.Bitmap;

import com.jigdraw.draw.util.Base64Util;

/**
 * Entity to encapsulate image attributes
 *
 * @author Jay Paulynice
 */
public class ImageEntity {
    private long id;
    private Bitmap image;
    private String name;
    private String desc;

    public ImageEntity(Bitmap image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageEntity)) return false;

        ImageEntity entity = (ImageEntity) o;

        return desc.equals(entity.desc) &&
                image.equals(entity.image) &&
                name.equals(entity.name);

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
        return "ImageEntity{" +
                "image string=" + Base64Util.bitMapToBase64(image) +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
