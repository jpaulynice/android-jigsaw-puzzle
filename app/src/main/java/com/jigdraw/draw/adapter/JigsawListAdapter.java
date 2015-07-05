package com.jigdraw.draw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

/**
 * Adapter for our jigsaw puzzle grid view
 *
 * @author Jay Paulynice
 */
public class JigsawListAdapter extends BaseAdapter {
    private Context context;
    private List<Bitmap> tiles;

    public JigsawListAdapter(Context context, List<Bitmap> tiles) {
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
            view.setLayoutParams(new ListView.LayoutParams(d.getWidth(), d
                    .getHeight()));
        } else {
            view = (ImageView) convertView;
        }
        view.setImageDrawable(new BitmapDrawable(context.getResources(), d));

        return view;
    }

    @Override
    public final long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return tiles.size();
    }

    @Override
    public Object getItem(int position) {
        return tiles.get(position);
    }

    protected Context getContext() {
        return context;
    }
}