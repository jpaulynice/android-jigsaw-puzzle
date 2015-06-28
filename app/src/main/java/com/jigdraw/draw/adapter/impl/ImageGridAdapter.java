package com.jigdraw.draw.adapter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.jigdraw.draw.adapter.BaseGridAdapter;

import java.util.List;

/**
 * Adapter for our jigsaw puzzle grid view
 *
 * @author Jay Paulynice
 */
public class ImageGridAdapter extends BaseGridAdapter {
    private Context context;
    private List<Bitmap> tiles;
    private int count;

    public ImageGridAdapter(Context context, List<Bitmap> tiles, int count) {
        super(context, tiles, count);
        this.count = count;
        this.context = context;
        this.tiles = tiles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Bitmap d = tiles.get(position);
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(d.getWidth(), d
                    .getHeight()));
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView
                .setImageDrawable(new BitmapDrawable(context.getResources(), d));
        return imageView;
    }
}