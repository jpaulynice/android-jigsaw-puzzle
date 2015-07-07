package com.jigdraw.draw.service;

import android.graphics.Bitmap;

import com.jigdraw.draw.model.enums.Difficulty;

/**
 * Simple interface for jigsaw manipulations.
 *
 * @author Jay Paulynice
 */
public interface JigsawService {
    /**
     * Create jisaw puzzle from given original image and difficulty level
     *
     * @param original the original image to create jigsaw puzzle
     * @param level difficulty level
     * @return the id of the original image once saved
     */
    Long create(Bitmap original, Difficulty level);
}