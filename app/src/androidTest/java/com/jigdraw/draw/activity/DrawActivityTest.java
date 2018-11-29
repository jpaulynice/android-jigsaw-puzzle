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

package com.jigdraw.draw.activity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for draw activity class
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
@RunWith(AndroidJUnit4.class)
public class DrawActivityTest {
    private static final String TAG = "DrawActivityTest";
    private DrawActivity activity;

    @Rule
    public ActivityTestRule<DrawActivity> mActivityRule = new ActivityTestRule<>(DrawActivity.class);

    @Before
    public void init() {
        activity = mActivityRule.getActivity();
    }

    @Test
    public void testActivityNotNull() {
        Log.d(TAG, "testing activity not null...");
        assertNotNull(activity);
    }

    @Test
    public void testControlsVisible() {
        Log.d(TAG, "testing ui controls visible...");
        for (View v : activity.getMenuOptions()) {
            assertEquals(View.VISIBLE, v.getVisibility());
        }

        for (View b : activity.getBrushes()) {
            assertEquals(View.VISIBLE, b.getVisibility());
        }
    }
}