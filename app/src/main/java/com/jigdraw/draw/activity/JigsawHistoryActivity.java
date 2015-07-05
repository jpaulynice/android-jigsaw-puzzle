package com.jigdraw.draw.activity;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.jigdraw.draw.R;
import com.jigdraw.draw.tasks.JigsawHistoryLoader;

import static com.jigdraw.draw.util.ToastUtil.shortToast;

public class JigsawHistoryActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
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

    public void init() {
        ListView lv = getListView();
        JigsawHistoryLoader task = new JigsawHistoryLoader
                (getApplicationContext(), lv);
        task.execute();

        shortToast(getApplicationContext(), "Loading drawing history...");
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
}