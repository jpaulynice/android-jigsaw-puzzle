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

package com.jigdraw.draw.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ListView;

import com.jigdraw.draw.adapter.JigsawListAdapter;
import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.dao.impl.ImageDaoImpl;
import com.jigdraw.draw.model.ImageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Async task to load a user's drawing history to show in the history list
 *
 * @author Jay Paulynice
 */
public class JigsawHistoryLoader extends AsyncTask<Long, Integer,
        List<Bitmap>> {
    private ImageDao dao;
    private Context context;
    private ListView listView;

    public JigsawHistoryLoader(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
        this.dao = new ImageDaoImpl(context);
    }

    @Override
    protected List<Bitmap> doInBackground(Long[] params) {
        List<ImageEntity> data = dao.getHistory();
        List<Bitmap> bitmaps = new ArrayList<>();
        for (ImageEntity entity : data) {
            bitmaps.add(entity.getImage());
        }

        return bitmaps;
    }

    @Override
    protected void onPostExecute(List<Bitmap> tiles) {
        JigsawListAdapter adapter = new JigsawListAdapter(context, tiles,
                tiles.size());
        listView.setAdapter(adapter);
    }
}