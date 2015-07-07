package com.jigdraw.draw.activity;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
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
    /** Class name for logging */
    private static final String TAG = "JigsawHistoryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "menu item selected: " + item.getItemId());
        return true;
    }

    public void init() {
        initMenuBar();

        Log.d(TAG, "initializing history list view...");
        ListView lv = getListView();
        JigsawHistoryLoader task = new JigsawHistoryLoader
                (getApplicationContext(), lv);

        shortToast(getApplicationContext(), "Loading drawing history...");
        task.execute();

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
                Log.d(TAG, "clicked element with id: " + id);
            }
        });
    }

    private void initMenuBar() {
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }
}