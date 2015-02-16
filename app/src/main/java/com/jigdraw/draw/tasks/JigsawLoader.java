package com.jigdraw.draw.tasks;

import android.os.AsyncTask;

import com.jigdraw.draw.model.ImageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to load jigsaw pieces from the sqlite database asynchronously
 * returning a list of {@link com.jigdraw.draw.model.ImageEntity} objects
 * that were created from an original image with id
 *
 * @author Jay Paulynice
 */
public class JigsawLoader extends AsyncTask<Long, Integer, List<ImageEntity>> {
    @Override
    protected List<ImageEntity> doInBackground(Long[] params) {
        //TODO: implement
        return new ArrayList<>();
    }
}
