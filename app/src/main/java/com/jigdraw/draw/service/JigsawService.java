package com.jigdraw.draw.service;

import android.content.Context;
import android.graphics.Bitmap;

import com.jigdraw.draw.model.Difficulty;
import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.impl.ImageServiceImpl;

/**
 * Service to do jigsaw manipulations
 *
 * @author Jay Paulynice
 */
public class JigsawService {
    private ImageService service;

    public JigsawService(Context context) {
        service = new ImageServiceImpl(context);
    }

    public boolean createJigsaw(Bitmap original, Difficulty level) {
        sliceImage(original, Difficulty.getMatrixByDifficulty(level));
        return true;
    }

    private void sliceImage(Bitmap original, int numberOfSlices) {
        int w = original.getWidth();
        int h = original.getHeight();

        int xslice = w / numberOfSlices;
        int yslice = h / numberOfSlices;

        for (int i = 0; i < h; i += yslice) {
            for (int j = 0; j < w; j += xslice) {

                int mx = Math.min(i + xslice, w);
                int my = Math.min(j + yslice, h);

                Bitmap sub = Bitmap.createBitmap(original, mx, my, xslice, yslice);
                String name = "tile-" + i + "-" + j + ".png";
                String desc = "sub image " + name;
                ImageEntity entity = new ImageEntity(sub, name, desc);

                service.insert(entity);
            }
        }
    }
}
