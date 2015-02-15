package com.jigdraw.draw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.ImageService;
import com.jigdraw.draw.service.impl.ImageServiceImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ImageAdapter extends BaseAdapter {
    private ImageService imgserv;
    private Context mContext;
    private Bitmap[] mThumbIds;
    private int numColumns;

    public ImageAdapter(Context c, long id) {
        mContext = c;
        imgserv = new ImageServiceImpl(c);
        initJigsaw(id);
    }

    private void initJigsaw(long id) {
        List<ImageEntity> entities = new LinkedList<>(imgserv.findTiles(id));
        List<Bitmap> ramdomArrangement = getRandomArrangement(entities);
        initThumbnails(ramdomArrangement, entities.size());
    }

    private void initThumbnails(List<Bitmap> bitmaps, int size) {
        numColumns = (int) Math.sqrt(size);
        mThumbIds = new Bitmap[size];

        //add the images to the thumbs list
        int n = 0;
        for (Bitmap b : bitmaps) {
            mThumbIds[n] = b;
            n++;
        }
    }

    private List<Bitmap> getRandomArrangement(List<ImageEntity> entities) {
        List<Bitmap> ramdomArrangement = new ArrayList<>();
        Random r = new Random();

        //build a list with random arrangements
        for (ImageEntity e : entities) {
            int index = r.nextInt(entities.size());

            Bitmap d = entities.get(index).getImage();
            ramdomArrangement.add(d);
            entities.remove(d);
        }
        return ramdomArrangement;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Bitmap d = mThumbIds[position];
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(d
                    .getWidth(),
                    d.getHeight()));
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(new BitmapDrawable(d));
        return imageView;
    }
}
