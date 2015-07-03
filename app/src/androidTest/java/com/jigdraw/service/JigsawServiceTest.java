package com.jigdraw.service;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.jigdraw.draw.model.enums.Difficulty;
import com.jigdraw.draw.service.JigsawService;
import com.jigdraw.draw.service.impl.JigsawServiceImpl;

public class JigsawServiceTest extends AndroidTestCase {
    private JigsawService service;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext
                (getContext(), "test_");
        service = new JigsawServiceImpl(context);
    }

    public void testCreateEasyJigsaw() {
        Long id = service.create(Bitmap.createBitmap(20, 20,
                        Bitmap.Config.ARGB_8888),
                Difficulty.EASY);
        assertNotNull(id);
    }

    public void testCreateMediumDiffJigsaw() {
        Long id = service.create(Bitmap.createBitmap(20, 20,
                        Bitmap.Config.ARGB_8888),
                Difficulty.MEDIUM);
        assertNotNull(id);
    }

    public void testCreateHardJigsaw() {
        Long id = service.create(Bitmap.createBitmap(20, 20,
                        Bitmap.Config.ARGB_8888),
                Difficulty.HARD);
        assertNotNull(id);
    }
}