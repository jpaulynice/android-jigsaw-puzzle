package com.jigdraw.draw.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.GridView;

import com.jigdraw.draw.adapter.ImageAdapter;
import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.ImageService;
import com.jigdraw.draw.service.impl.ImageServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to load jigsaw pieces from the sqlite database asynchronously
 * returning a list of {@link com.jigdraw.draw.model.ImageEntity} objects
 * that were created from an original image with id
 *
 * @author Jay Paulynice
 */
public class JigsawLoader extends AsyncTask<Long, Integer, List<Bitmap>> {
    private ImageService service;
    private GridView gridView;
    private Context context;

    public JigsawLoader(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        this.service = new ImageServiceImpl(context);
    }

    @Override
    protected List<Bitmap> doInBackground(Long[] params) {
        List<ImageEntity> entities = service.findTiles(params[0]);
        Collections.shuffle(entities);
        List<Bitmap> tiles = new ArrayList<>();

        for (ImageEntity entity : entities) {
            tiles.add(entity.getImage());
        }

        return tiles;
    }

    @Override
    protected void onPostExecute(List<Bitmap> tiles) {
        ImageAdapter adapter = new ImageAdapter(context, tiles);

        gridView.setAdapter(adapter);
        gridView.setNumColumns((int) Math.sqrt(tiles.size()));
    }
}