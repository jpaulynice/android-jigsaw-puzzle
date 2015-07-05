package com.jigdraw.draw.activity;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
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

public class JigsawHistoryActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        final ImageDao dao = new ImageDaoImpl(this);

        List<Map<String, Bitmap>> items = new ArrayList<>();
        List<ImageEntity> data = dao.findAllOriginals();

        for (ImageEntity entity : data) {
            Map<String, Bitmap> map = new HashMap<>();
            map.put("image_entity", entity.getImage());
            items.add(map);
        }

        String[] from = new String[]{"rowid"};
        int[] to = new int[]{R.id.image_entity};

        BaseAdapter adapter = new SimpleAdapter(getApplicationContext(),
                items, R.layout.activity_history, from, to);

        setListAdapter(adapter);

        ListView lv = getListView();
        lv.setLongClickable(true);

        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                return true;
            }
        });

        lv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    final int pos, long id) {
                //TODO: implement
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO: implement
        return true;
    }
}
