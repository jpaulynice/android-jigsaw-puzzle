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
        List<ImageEntity> data = dao.findAllOriginals();
        List<Bitmap> bitmaps = new ArrayList<>();
        for (ImageEntity entity : data) {
            bitmaps.add(entity.getImage());
        }

        return bitmaps;
    }

    @Override
    protected void onPostExecute(List<Bitmap> tiles) {
        JigsawListAdapter adapter = new JigsawListAdapter(context, tiles);
        listView.setAdapter(adapter);
    }
}