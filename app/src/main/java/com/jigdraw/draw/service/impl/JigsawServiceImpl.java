package com.jigdraw.draw.service.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.jigdraw.draw.model.Difficulty;
import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.ImageService;
import com.jigdraw.draw.service.JigsawService;

import java.util.UUID;

/**
 * Default implementation for {@link com.jigdraw.draw.service.JigsawService}
 *
 * @author Jay Paulynice
 */
public class JigsawServiceImpl implements JigsawService {
    /** class name for logging */
    private static final String TAG = "JigsawServiceImpl";

    /** image db service */
    private ImageService service;

    /**
     * Create new jigsaw service given context
     *
     * @param context the application context
     */
    public JigsawServiceImpl(Context context) {
        service = new ImageServiceImpl(context);
    }

    @Override
    public boolean createJigsaw(Bitmap original, Difficulty level) {
        createImageTiles(original, Difficulty.getNumberOfPieces(level));
        return true;
    }

    /**
     * Slice original image into tiles then save in the db
     *
     * @param original the original image to slice up
     * @param n        how many slices to cut the image into
     */
    private void createImageTiles(Bitmap original, int n) {
        saveOriginal(original);

        int w = original.getWidth();
        int h = original.getHeight();

        int tile_width = w / n;
        int tile_height = h / n;

        for (int y = 0; y + tile_height <= h; y += tile_height) {
            for (int x = 0; x + tile_width <= w; x += tile_width) {
                Bitmap tile = Bitmap.createBitmap(original, x, y, tile_width,
                        tile_height);
                saveTile(tile, x, y);
            }
        }
    }

    /**
     * Save the original image with a random UUID
     *
     * @param original image to save
     */
    private void saveOriginal(Bitmap original) {
        String originalName = UUID.randomUUID() + ".png";
        String originalDesc = "original image " + originalName;
        Log.d(TAG, "image name: " + originalName);

        service.insert(new ImageEntity(original, originalName, originalDesc));
    }

    /**
     * Save created tile in the database
     *
     * @param tile the tile to save
     * @param x    the tile's x coordinate
     * @param y    the tile's y coordinate
     */
    private void saveTile(Bitmap tile, int x, int y) {
        String name = "tile-" + x + "-" + y + ".png";
        String desc = "sub image " + name;
        Log.d(TAG, "image name: " + name);

        service.insert(new ImageEntity(tile, name, desc));
    }
}