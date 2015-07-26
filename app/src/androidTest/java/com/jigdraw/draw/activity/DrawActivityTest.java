package com.jigdraw.draw.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;

/**
 * Unit test for draw activity class
 *
 * @author Jay Paulynice
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
        for (View v : activity.getTopButtons()) {
            assertTrue(View.VISIBLE == v.getVisibility());
        }

        for (View b : activity.getBottomButtons()) {
            assertTrue(View.VISIBLE == b.getVisibility());
        }
    }
}