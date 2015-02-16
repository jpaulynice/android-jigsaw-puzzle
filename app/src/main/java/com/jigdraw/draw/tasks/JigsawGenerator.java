package com.jigdraw.draw.tasks;

import android.os.AsyncTask;

/**
 * Class to generate jigsaw pieces and save the tiles in the sqlite database
 * asynchronously returning the id of the original image
 *
 * @author Jay Paulynice
 */
public class JigsawGenerator extends AsyncTask<Long, Integer, Long> {

    @Override
    protected Long doInBackground(Long[] params) {
        //TODO: implement
        return new Long(0);
    }
}
