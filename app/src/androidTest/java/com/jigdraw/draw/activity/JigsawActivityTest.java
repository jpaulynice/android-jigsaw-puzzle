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
import android.widget.Chronometer;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

import com.jigdraw.draw.R;

/**
 * Unit test for jigsaw activity class
 *
 * @author Jay Paulynice (jay.paulynice@gmail.com)
 */
@RunWith(AndroidJUnit4.class)
public class JigsawActivityTest {
    private JigsawActivity activity;

    @Rule
    public ActivityTestRule<JigsawActivity> mActivityRule = new ActivityTestRule<>(JigsawActivity.class);

    @Before
    public void init() {
        activity = mActivityRule.getActivity();
    }

    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void testChronometer() {
        Chronometer chronometer = activity.findViewById(R.id.chronometer);
        assertNotNull(chronometer);
    }
}