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

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;

/**
 * Unit test for draw activity class
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
public class DrawActivityTest extends
        ActivityInstrumentationTestCase2<DrawActivity> {
    private static final String TAG = "DrawActivityTest";
    private DrawActivity activity;

    public DrawActivityTest() {
        super(DrawActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testActivityNotNull() {
        Log.d(TAG, "testing activity not null...");
        assertNotNull(activity);
    }

    public void testControlsVisible() {
        Log.d(TAG, "testing ui controls visible...");
        for (View v : activity.getTopOptions()) {
            assertTrue(View.VISIBLE == v.getVisibility());
        }

        for (View b : activity.getBrushes()) {
            assertTrue(View.VISIBLE == b.getVisibility());
        }
    }
}