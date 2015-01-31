package com.jigdraw.draw.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.jigdraw.draw.model.Difficulty;
import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.impl.ImageServiceImpl;

/**
 * Service to do jigsaw manipulations
 *
 * @author Jay Paulynice
 */
public class JigsawService {
    private static final String TAG = "JigsawService";

    private ImageService service;

    public JigsawService(Context context) {
        service = new ImageServiceImpl(context);
    }

    public boolean createJigsaw(Bitmap original, Difficulty level) {
        sliceImage(original, Difficulty.getMatrixByDifficulty(level));
        return true;
    }

    private void sliceImage(Bitmap original, int slices) {
        int w = original.getWidth();
        int h = original.getHeight();

        int x_slices = w / slices;
        int y_slices = h / slices;

        for (int y = 0; y + y_slices <= h; y += y_slices) {
            for (int x = 0; x + x_slices <= w; x += x_slices) {

                String name = "tile-" + x + "-" + y + ".png";
                String desc = "sub image " + name;
                Log.d(TAG, "image name: " + name);


                Bitmap sub = Bitmap.createBitmap(original, x, y, x_slices,
                        y_slices);
                ImageEntity entity = new ImageEntity(sub, name, desc);

                service.insert(entity);
            }
        }
    }
}
