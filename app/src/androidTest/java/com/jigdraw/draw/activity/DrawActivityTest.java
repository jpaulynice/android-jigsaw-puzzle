package com.jigdraw.draw.activity;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Unit test for draw activity class
 * 
 * @author Jay Paulynice
 */
public class DrawActivityTest extends 
        ActivityInstrumentationTestCase2<DrawActivity> {
    private DrawActivity activity;
    
    public DrawActivityTest(){
        super(DrawActivity.class);
        
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