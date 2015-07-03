package com.jigdraw.draw.activity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.ImageButton;

import com.jigdraw.draw.R;

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

    @SmallTest
    public void testActivityNotNull(){
        assertNotNull(activity);
    }

    @MediumTest
    public void testControlsVisible() {
        for(ImageButton b: activity.getBrushes()){
            assertTrue(View.VISIBLE == b.getVisibility());
        }
        
        for(View v: activity.getMenuButtons()){
            assertTrue(View.VISIBLE == v.getVisibility());
        }
    }
}