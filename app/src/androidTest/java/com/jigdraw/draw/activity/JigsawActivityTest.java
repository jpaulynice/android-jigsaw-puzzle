package com.jigdraw.draw.activity;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Unit test for jigsaw activity class
 * 
 * @author Jay Paulynice
 */
public class JigsawActivityTest extends 
        ActivityInstrumentationTestCase2<JigsawActivity> {
    private JigsawActivity activity;

    public JigsawActivityTest(){
        super(JigsawActivity.class);
        
    }
    @Override
    public void setUp() throws Exception {
        super.setUp();
        activity =  getActivity();
    }
    
    public void testActivityNotNull(){
        assertNotNull(activity);
    }
}