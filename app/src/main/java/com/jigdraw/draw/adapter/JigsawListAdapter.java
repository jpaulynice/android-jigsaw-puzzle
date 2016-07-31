/*
 * Copyright (c) 2015. Jay Paulynice (jay.paulynice@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jigdraw.draw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

/**
 * Adapter for our jigsaw puzzle drawing list history view
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public class JigsawListAdapter extends JigsawBaseAdapter {
    private Context context;
    private List<Bitmap> items;

    public JigsawListAdapter(Context context, List<Bitmap> items, int count) {
        super(context, items, count);
        this.context = context;
        this.items = items;
    }

    @Override
    public boolean canReorder(int position) {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;
        if (convertView == null) {
            view = newView(position);
        } else {
            view = (ImageView) convertView;
        }

        return view;
    }

    private ImageView newView(int position) {
        Bitmap d = items.get(position);
        ImageView view = new ImageView(context);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ListView.LayoutParams(d.getWidth(), d.getHeight()));
        view.setImageDrawable(new BitmapDrawable(context.getResources(), d));

        return view;
    }
}
