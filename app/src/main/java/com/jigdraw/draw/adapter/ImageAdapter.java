package com.jigdraw.draw.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.ImageService;

public class ImageAdapter extends BaseAdapter {
    ImageService imgserv;
    private Context mContext;
    private Drawable[] mThumbIds;

    public ImageAdapter(Context c, long id) {
        mContext = c;
        ImageEntity entity = imgserv.query(3);
        Drawable d = new BitmapDrawable(entity.getImage());

        mThumbIds[0] = d;
        mThumbIds[1] = d;
        mThumbIds[2] = d;
        mThumbIds[3] = d;
        mThumbIds[4] = d;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageDrawable(mThumbIds[position]);
        return imageView;
    }
}
