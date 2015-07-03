package com.jigdraw.db;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.jigdraw.draw.db.JigsawDB;

public class JigsawDBTest extends AndroidTestCase {
    private JigsawDB db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext
                (getContext(), "test_");
        db = new JigsawDB(context);
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }

    public void testDBCreated() {
        assertNotNull(db);
        assertNotNull(db.getWritableDatabase());
        assertNotNull(db.getReadableDatabase());
    }
}