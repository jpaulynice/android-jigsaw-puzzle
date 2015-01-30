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
        int row = 2;
        int col = 2;

        int w = original.getWidth();
        int h = original.getHeight();

        int newW = w / col;
        int newH = h / row;

        int x = 0;
        int y;

        for (int i = 0; i < row; i++) {
            y = 0;
            for (int j = 0; j < col; j++) {
                Bitmap sub = Bitmap.createBitmap(original, x, y, newH, newH);
                String name = "original" + i + j + ".png";
                String desc = "sub image " + i + j;
                ImageEntity entity = new ImageEntity(sub, name, desc);

                service.insert(entity);
                y += newW;
            }
            x += newH;
        }

        return true;
    }
}
