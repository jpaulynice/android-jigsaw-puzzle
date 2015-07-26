package com.jigdraw.draw.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.jigdraw.draw.R;

/**
 * Unit test for draw activity class
 *
 * @author Jay Paulynice
 */
public class DrawActivityTest extends
        ActivityInstrumentationTestCase2<DrawActivity> {
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
        assertNotNull(activity);
    }

    public void testControlsVisible() {
        for (ImageButton b : activity.getBrushes()) {
            assertTrue(View.VISIBLE == b.getVisibility());
        }

        LinearLayout layout = (LinearLayout) activity.findViewById(R.id.top_options);
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = layout.getChildAt(i);
            assertTrue(View.VISIBLE == view.getVisibility());
        }
    }
}