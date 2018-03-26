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

package com.jigdraw.draw.activity;

import static com.jigdraw.draw.util.ToastUtil.shortToast;

import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.jigdraw.draw.R;
import com.jigdraw.draw.tasks.JigsawHistoryLoader;

/**
 * History activity to see list of jigsaw images created.
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public class JigsawHistoryActivity extends BaseJigsawActivity {
    /** Class name for logging */
    private static final String TAG = "JigsawHistoryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting jigsaw history activity...");
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * Initialize all the views
     */
    private void init() {
        setContentView(R.layout.activity_history);
        enableMenuBarUpButton();
        initViews();
    }

    /**
     * Initialize jigsaw history list view
     */
    private void initViews() {
        Log.d(TAG, "initializing history list view...");
        ListView lv = findViewById(R.id.history_list);
        JigsawHistoryLoader task = new JigsawHistoryLoader
                (getApplicationContext(), lv);

        shortToast(getApplicationContext(), "Loading drawing history...");
        task.execute();

        lv.setLongClickable(true);
        lv.setOnItemLongClickListener(onItemLongClickListener());
        lv.setOnItemClickListener(onItemClickListener());
    }

    /**
     * Item long click listener for image click
     *
     * @return the item long click listener
     */
    private OnItemLongClickListener onItemLongClickListener() {
        return (arg0, arg1, pos, id) -> {
            Log.d(TAG, "item long clicked element with id: " + id);
            return true;
        };
    }

    /**
     * Item click listener for image click
     *
     * @return the item click listener
     */
    private OnItemClickListener onItemClickListener() {
        return (arg0, arg1, pos, id) -> Log.d(TAG, "clicked element with id: " + id);
    }
}