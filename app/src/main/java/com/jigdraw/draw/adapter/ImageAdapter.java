package com.jigdraw.draw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Adapter for our jigsaw puzzle grid view
 *
 * @author Jay Paulynice
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> tiles;

    public ImageAdapter(Context context, List<Bitmap> tiles) {
        this.context = context;
        this.tiles = tiles;
    }

    @Override
    public int getCount() {
        return tiles.size();
    }

    @Override
    public Bitmap getItem(int position) {
        return tiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        Bitmap d = getItem(position);
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
}