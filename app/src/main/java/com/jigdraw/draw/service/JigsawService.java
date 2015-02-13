package com.jigdraw.draw.service;

import android.graphics.Bitmap;

import com.jigdraw.draw.model.Difficulty;

/**
 * Simple interface for jigsaw manipulations.
 *
 * @author Jay Paulynice
 */
public interface JigsawService {
    public long createJigsaw(Bitmap original, Difficulty level);
}
