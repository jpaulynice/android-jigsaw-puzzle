package com.jigdraw.draw.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.jigdraw.draw.model.Difficulty;
import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.impl.ImageServiceImpl;

import java.util.UUID;

/**
 * Service to do jigsaw manipulations
 *
 * @author Jay Paulynice
 */
public class JigsawService {
    /** class name for logging */
    private static final String TAG = "JigsawService";

    /** image db service */
    private ImageService service;

    /**
     * Create new jigsaw service given context
     *
     * @param context the application context
     */
    public JigsawService(Context context) {
        service = new ImageServiceImpl(context);
    }

    /**
     * Create jigsaw by taking original image and difficulty level
     *
     * @param original the original image
     * @param level    difficulty level
     * @return true if no errors
     */
    public boolean createJigsaw(Bitmap original, Difficulty level) {
        sliceImage(original, Difficulty.getNumberOfPieces(level));
        return true;
    }

    /**
     * Slice original image into tiles then save in the db
     *
     * @param original the original image to slice up
     * @param n        how many slices to cut the image into
     */
    private void sliceImage(Bitmap original, int n) {
        createOriginal(original);

        int w = original.getWidth();
        int h = original.getHeight();

        int x_slices = w / n;
        int y_slices = h / n;

        for (int y = 0; y + y_slices <= h; y += y_slices) {
            for (int x = 0; x + x_slices <= w; x += x_slices) {
                Bitmap sub = Bitmap.createBitmap(original, x, y, x_slices,
                        y_slices);
                createTile(sub, x, y);
            }
        }
    }

    /**
     * Save the original image with a random UUID
     *
     * @param original image to save
     */
    private void createOriginal(Bitmap original) {
        String originalName = UUID.randomUUID() + ".png";
        String originalDesc = "original image " + originalName;
        Log.d(TAG, "image name: " + originalName);

        createEntity(original, originalName, originalDesc);
    }

    /**
     * Save created tile in the database
     *
     * @param sub   the tile to save
     * @param xTile the tile's x coordinate
     * @param yTile the tile's y coordinate
     */
    private void createTile(Bitmap sub, int xTile, int yTile) {
        String name = "tile-" + xTile + "-" + yTile + ".png";
        String desc = "sub image " + name;
        Log.d(TAG, "image name: " + name);

        createEntity(sub, name, desc);
    }

    /**
     * Create and save an image entity
     *
     * @param image the image field
     * @param name  the name field
     * @param desc  the description field
     */
    private void createEntity(Bitmap image, String name, String desc) {
        ImageEntity originalEntity = new ImageEntity(image, name,
                desc);
        service.insert(originalEntity);
    }
}