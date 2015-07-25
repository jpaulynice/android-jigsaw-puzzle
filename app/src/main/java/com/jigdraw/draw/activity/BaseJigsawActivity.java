package com.jigdraw.draw.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.jigdraw.draw.R;

/**
 * Base activity class for activities that share the menu bar
 *
 * @author Jay Paulynice
 */
public abstract class BaseJigsawActivity extends Activity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history:
                startActivity(new Intent(getApplicationContext(),
                        JigsawHistoryActivity.class));
                return true;
        }
        return false;
    }

    public void initMenuBar() {
        ActionBar bar = getActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }
}