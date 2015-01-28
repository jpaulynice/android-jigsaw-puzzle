package com.jigdraw.draw.model;

import android.graphics.Bitmap;

/**
 * Created by julespaulynice on 1/28/15.
 */
public class ImageEntity {
    private Bitmap image;
    private String name;
    private String desc;

    public ImageEntity() {
    }

    public ImageEntity(Bitmap image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
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
}
