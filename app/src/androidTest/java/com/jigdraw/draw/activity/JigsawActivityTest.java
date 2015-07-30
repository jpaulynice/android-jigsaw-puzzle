package com.jigdraw.draw.activity;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.RenamingDelegatingContext;

import com.jigdraw.draw.model.LongParcelable;

/**
 * Unit test for jigsaw activity class
 *
 * @author Jay Paulynice
 */
public class JigsawActivityTest extends
        ActivityInstrumentationTestCase2<JigsawActivity> {
    private JigsawActivity activity;

    public JigsawActivityTest() {
        super(JigsawActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext
                (this.getInstrumentation().getTargetContext()
                        .getApplicationContext(), "test_");
        setActivityIntent(new Intent(context, JigsawActivity.class).putExtra(
                "originalId", new LongParcelable(1L)));
        activity = getActivity();
    }

    public void testActivityNotNull() {
        assertNotNull(activity);
    }
}