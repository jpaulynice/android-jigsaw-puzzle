package com.jigdraw.draw.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.GridView;

import com.jigdraw.draw.adapter.JigsawGridAdapter;
import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.dao.impl.ImageDaoImpl;
import com.jigdraw.draw.model.ImageEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to load jigsaw pieces from the sqlite database asynchronously returning
 * a list of {@link com.jigdraw.draw.model.ImageEntity} objects that were
 * created from an original image with id
 *
 * @author Jay Paulynice
 */
public class JigsawLoader extends AsyncTask<Long, Integer, List<Bitmap>> {
    /** Image dao */
    private ImageDao dao;

    /** The grid view */
    private GridView gridView;

    /** The application context */
    private Context context;

    public JigsawLoader(Context context, GridView gridView) {
        this.context = context;
        this.gridView = gridView;
        this.dao = new ImageDaoImpl(context);
    }

    @Override
    protected List<Bitmap> doInBackground(Long[] params) {
        List<ImageEntity> entities = dao.findTiles(params[0]);
        Collections.shuffle(entities);
        List<Bitmap> tiles = new ArrayList<>();

        for (ImageEntity entity : entities) {
            tiles.add(entity.getImage());
        }

        return tiles;
    }

    @Override
    protected void onPostExecute(List<Bitmap> tiles) {
        int pieces = (int) Math.sqrt(tiles.size());
        JigsawGridAdapter adapter = new JigsawGridAdapter(context, tiles,
                pieces);

        gridView.setAdapter(adapter);
        gridView.setNumColumns(pieces);
    }
}