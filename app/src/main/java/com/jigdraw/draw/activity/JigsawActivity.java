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

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Chronometer;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.jigdraw.draw.R;
import com.jigdraw.draw.model.LongParcelable;
import com.jigdraw.draw.model.enums.JigsawState;
import com.jigdraw.draw.tasks.JigsawLoader;
import com.jigdraw.draw.views.JigsawGridView;

/**
 * Represents the jigsaw puzzle solving activity.  A user creates a drawing then
 * selects a difficulty level (easy, medium, hard). On selecting ok, this
 * activity starts.
 * <p>
 * To initialize the puzzle grid, we create an asynchronous
 * task to load the images from the database, randomize them and render them in
 * the grid.
 * <p>
 * The images are stored in the local SQLite DB as Base64 encoded
 * strings. In the future, the ideal thing would be to offload the data through
 * a REST API to a central MySQL, PostGreSQL or NoSQL database.
 *
 * @author Jay Paulynice
 */
public class JigsawActivity extends BaseJigsawActivity {
    /** The original image id to look up for jigsaw */
    public static final String ORIGINAL_IMG_ID = "originalId";

    /** Class name for logging */
    private static final String TAG = "JigsawActivity";

    /** Chronometer to display elapsed time */
    private Chronometer chronometer;

    /** Used to get current state of the chronometer play or pause */
    private JigsawState state = JigsawState.RUNNING;

    /** Need to keep track of time to reset the chronometer */
    private long elapsedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Starting jigsaw activity...");
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * Initialize all the views
     */
    private void init() {
        setContentView(R.layout.activity_jigsaw);
        enableMenuBarUpButton();
        initViews();
        initTimer();
    }

    /**
     * Initialize the jigsaw grid view
     */
    private void initViews() {
        Log.d(TAG, "initializing jigsaw grid view");
        final JigsawGridView gridView = (JigsawGridView) findViewById(R.id
                .jigsaw_grid);

        JigsawLoader task = new JigsawLoader(getApplicationContext(), gridView);
        LongParcelable longParcelable = getIntent().getExtras().getParcelable(
                ORIGINAL_IMG_ID);

        if (longParcelable == null) {
            throw new IllegalArgumentException("Parcelable can not be null.");
        }
        task.execute(longParcelable.getData());

        gridView.setOnItemLongClickListener(onItemLongClickListener
                (gridView));
        gridView.setOnDropListener(onDropListener(gridView));
        gridView.setOnDragListener(onDragListener());
    }

    /**
     * Initialize the chronometer
     */
    private void initTimer() {
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    /**
     * Listener to get hold of the click event to start the grid edit mode
     *
     * @param gridView the grid view
     * @return the item long click listener
     */
    private JigsawGridView.OnItemLongClickListener onItemLongClickListener(
            final JigsawGridView gridView) {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        };
    }

    /**
     * Listener to get hold of the drop event to stop the grid edit mode
     *
     * @param gridView the grid view
     * @return the drop listener
     */
    private JigsawGridView.OnDropListener onDropListener(
            final JigsawGridView gridView) {
        return new JigsawGridView.OnDropListener() {
            @Override
            public void onActionDrop() {
                Log.d(TAG, "dropped element");
                gridView.stopEditMode();
            }
        };
    }

    /**
     * Listener to get hold of the drag and position changed events
     *
     * @return the drag listener
     */
    private JigsawGridView.OnDragListener onDragListener() {
        return new JigsawGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "dragging starts...position: " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition,
                                               int newPosition) {
                Log.d(TAG, String.format("drag changed from %d to %d",
                        oldPosition, newPosition));
            }
        };
    }

    /**
     * Get the play/pause view and update to pause if currently running
     * otherwise update to running.
     *
     * @param view the view
     */
    public void playPauseTimer(View view) {
        FontAwesomeText v = (FontAwesomeText) view;
        if (state == JigsawState.RUNNING) {
            pause(v);
        } else {
            restart(v);
        }
    }

    private void pause(final FontAwesomeText v) {
        state = JigsawState.PAUSED;
        v.setIcon("fa-play");
        chronometer.stop();
        elapsedTime = SystemClock.elapsedRealtime() - chronometer.getBase();
    }

    private void restart(final FontAwesomeText v) {
        state = JigsawState.RUNNING;
        v.setIcon("fa-pause");
        chronometer.setBase(SystemClock.elapsedRealtime() - elapsedTime);
        chronometer.start();
    }
}