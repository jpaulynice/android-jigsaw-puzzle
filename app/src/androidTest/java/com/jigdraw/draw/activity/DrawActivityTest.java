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
        View save = activity.findViewById(R.id.save_btn);
        assertTrue(View.VISIBLE == save.getVisibility());

        View newB = activity.findViewById(R.id.new_btn);
        assertTrue(View.VISIBLE == newB.getVisibility());

        View erase = activity.findViewById(R.id.erase_btn);
        assertTrue(View.VISIBLE == erase.getVisibility());
        
        for(ImageButton b: activity.getBrushes()){
            assertTrue(View.VISIBLE == b.getVisibility());
        }
    }
}