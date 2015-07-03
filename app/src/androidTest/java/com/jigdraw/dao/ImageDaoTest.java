package com.jigdraw.dao;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.dao.impl.ImageDaoImpl;
import com.jigdraw.draw.model.ImageEntity;

public class ImageDaoTest extends AndroidTestCase {
    private ImageDao dao;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext
                (getContext(), "test_");
        dao = new ImageDaoImpl(context);
    }

    public void testCreate() {
        Long id = dao.create(createImageEntity());
        assertNotNull(id);
    }

    public void testGet() {
        Long id = dao.create(createImageEntity());

        ImageEntity entity = dao.find(id);
        assertNotNull(entity);
    }

    public void testUpdate() {
        Long id = dao.create(createImageEntity());
        ImageEntity entity = dao.find(id);
        entity.setName("updated description");

        int val = dao.update(entity);
        assertEquals(val, 1);
    }

    public void testDelete() {
        Long id = dao.create(createImageEntity());
        dao.delete(id);
        assertNull(dao.find(id));
    }

    private ImageEntity createImageEntity() {
        ImageEntity e = new ImageEntity();
        e.setName("name");
        e.setDesc("description");
        e.setOriginalId(null);
        e.setImage(Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888));

        return e;
    }
}