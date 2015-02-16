package com.jigdraw.draw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.ImageService;
import com.jigdraw.draw.service.impl.ImageServiceImpl;

import java.util.Collections;
import java.util.List;

/**
 * Adapter for our jigsaw puzzle grid view
 *
 * @author Jay Paulynice
 */
public class ImageAdapter extends BaseAdapter {
    private static final String TAG = "ImageAdapter";
    private ImageService imgServ;
    private Context context;
    private Bitmap[] tiles;
    private int numColumns;

    public ImageAdapter(Context context, long id) {
        this.context = context;
        this.imgServ = new ImageServiceImpl(context);
        initJigsaw(id);
    }

    private void initJigsaw(long id) {
        Log.d(TAG, "fetching tiles with original image id: " + id);
        List<ImageEntity> entities = imgServ.findTiles(id);

        Log.d(TAG, "found " + entities.size() + " tiles");
        initThumbnails(entities);
    }

    private void initThumbnails(List<ImageEntity> entities) {
        int size = entities.size();
        numColumns = (int) Math.sqrt(size);
        tiles = new Bitmap[size];

        Collections.shuffle(entities);

        int n = 0;
        for (ImageEntity entity : entities) {
            tiles[n] = entity.getImage();
            n++;
        }
    }

    @Override
    public int getCount() {
        return tiles.length;
    }

    @Override
    public Object getItem(int position) {
        return tiles[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Bitmap d = tiles[position];
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(d
                    .getWidth(),
                    d.getHeight()));
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(new BitmapDrawable(context.getResources()
                , d));
        return imageView;
    }

    public int getNumColumns() {
        return numColumns;
    }
}