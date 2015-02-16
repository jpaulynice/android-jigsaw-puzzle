package com.jigdraw.draw.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jigdraw.draw.R;
import com.jigdraw.draw.model.LongParceable;
import com.jigdraw.draw.tasks.JigsawLoader;

/**
 * Represents the jigsaw puzzle solving activity.
 *
 * @author Jay Paulynice
 */
public class JigsawActivity extends Activity implements View.OnClickListener {
    /** activity name for logging */
    private static final String TAG = "JigsawActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jigsaw);
        initGridView();
    }

    private void initGridView() {
        LongParceable p = getIntent().getExtras().getParcelable("originalId");
        GridView gridView = (GridView) findViewById(R.id.gridview);

        JigsawLoader task = new JigsawLoader(getApplicationContext(), gridView);
        task.execute(p.getData());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View itemView,
                                    int position, long id) {
                Log.d(TAG, "clicked element at position: " + position);
                Toast.makeText(getApplicationContext(), "position: " + position,
                        Toast.LENGTH_SHORT).show();
                //TODO: drag and drop to solve puzzle
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        //TODO: do work
    }
}