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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jigdraw.draw.R;

/**
 * Base activity class for activities that share the menu bar
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public abstract class BaseJigsawActivity extends Activity {
    /** Class name for logging */
    private static final String TAG = "BaseJigsawActivity";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "menu item selected: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.menu_history:
                startActivity(new Intent(getApplicationContext(), JigsawHistoryActivity.class));
                return true;
            default:
                return false;
        }
    }

    /**
     * Enable the go back to parent activity in menu bar
     */
    public void enableMenuBarUpButton() {
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }
}