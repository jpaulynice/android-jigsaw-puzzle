package com.jigdraw.draw.activity;

import static com.jigdraw.draw.util.ToastUtil.shortToast;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.jigdraw.draw.R;
import com.jigdraw.draw.tasks.JigsawHistoryLoader;

/**
 * History activity to see list of jigsaw images created.
 *
 * @author Jay Paulynice
 */
public class JigsawHistoryActivity extends BaseJigsawActivity {
    /** Class name for logging */
    private static final String TAG = "JigsawHistoryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Staring jigsaw history activity...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initMenuBar();
        initViews();
    }

    private void initViews() {
        Log.d(TAG, "initializing history list view...");
        ListView lv = (ListView) findViewById(R.id.history_list);
        JigsawHistoryLoader task = new JigsawHistoryLoader
                (getApplicationContext(), lv);

        shortToast(getApplicationContext(), "Loading drawing history...");
        task.execute();

        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(getOnItemLongClickListener());
        lv.setOnItemClickListener(getOnItemClickListener());
    }

    private OnItemLongClickListener getOnItemLongClickListener() {
        return new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                return true;
            }
        };
    }

    private OnItemClickListener getOnItemClickListener() {
        return new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    final int pos, long id) {
                Log.d(TAG, "clicked element with id: " + id);
            }
        };
    }
}