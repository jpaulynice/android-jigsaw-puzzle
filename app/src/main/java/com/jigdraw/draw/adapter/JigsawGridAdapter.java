package com.jigdraw.draw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Adapter for our jigsaw puzzle grid view
 *
 * @author Jay Paulynice
 */
public class JigsawGridAdapter extends BaseGridAdapter {
    private Context context;
    private List<Bitmap> tiles;

    public JigsawGridAdapter(Context context, List<Bitmap> tiles, int count) {
        super(context, tiles, count);
        this.context = context;
        this.tiles = tiles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;
        Bitmap d = tiles.get(position);
        if (convertView == null) {
            view = new ImageView(context);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new GridView.LayoutParams(d.getWidth(), d
                    .getHeight()));
            view.setPadding(1, 1, 1, 1);
        } else {
            view = (ImageView) convertView;
        }
        view.setImageDrawable(new BitmapDrawable(context.getResources(), d));

        return view;
    }
}