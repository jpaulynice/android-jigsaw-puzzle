package com.jigdraw.draw.activity;

import android.test.ActivityInstrumentationTestCase2;

public class JigsawHistoryActivityTest
        extends ActivityInstrumentationTestCase2<JigsawHistoryActivity> {
    private JigsawHistoryActivity activity;

    public JigsawHistoryActivityTest() {
        super(JigsawHistoryActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    public void testActivityNotNull() {
        assertNotNull(activity);
    }
}