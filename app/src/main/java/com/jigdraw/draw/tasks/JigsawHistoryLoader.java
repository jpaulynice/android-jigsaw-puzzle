package com.jigdraw.draw.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jigdraw.draw.R;
import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.dao.impl.ImageDaoImpl;
import com.jigdraw.draw.model.ImageEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Map<String, Bitmap>> items = new ArrayList<>();

        for (Bitmap image : tiles) {
            Map<String, Bitmap> map = new HashMap<>();
            map.put("image_entity", image);
            items.add(map);
        }

        String[] from = new String[]{"image_entity"};
        int[] to = new int[]{R.id.image_entity};

        BaseAdapter adapter = new SimpleAdapter(context,
                items, R.layout.activity_history, from, to);
        listView.setAdapter(adapter);
    }
}
