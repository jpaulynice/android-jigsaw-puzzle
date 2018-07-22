/*
 * Copyright (c) 2018. Jay Paulynice (jay.paulynice@gmail.com)
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

package com.jigdraw.draw.tasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.jigdraw.draw.activity.JigsawActivity;
import com.jigdraw.draw.model.LongParcelable;
import com.jigdraw.draw.model.enums.Difficulty;
import com.jigdraw.draw.service.JigsawService;
import com.jigdraw.draw.service.impl.JigsawServiceImpl;

/**
 * Class to generate jigsaw pieces and save the tiles in the sqlite database asynchronously returning the id of the
 * original image
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public class JigsawGenerator extends AsyncTask<Bitmap, Integer, Long> {
    /** Jigsaw service */
    private JigsawService service;

    /** Difficulty level */
    private Difficulty level;

    /** Application context */
    private Context context;

    public JigsawGenerator(Context context, Difficulty level) {
        this.context = context;
        this.level = level;
        this.service = new JigsawServiceImpl(context);
    }

    @Override
    protected Long doInBackground(Bitmap... params) {
        return service.create(params[0], level);
    }

    @Override
    protected void onPostExecute(Long id) {
        startJigsaw(id);
    }

    private void startJigsaw(long id) {
        Intent intent = new Intent(context, JigsawActivity.class).putExtra(
                JigsawActivity.ORIGINAL_IMG_ID, new LongParcelable(id));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
