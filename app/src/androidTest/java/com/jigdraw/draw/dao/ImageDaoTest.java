/*
 * Copyright (c) 2018. Jay Paulynice (jay.paulynice@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jigdraw.draw.dao;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.jigdraw.draw.dao.impl.ImageDaoImpl;
import com.jigdraw.draw.model.ImageEntity;

import java.util.List;

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

    private ImageEntity createImageEntity() {
        ImageEntity e = new ImageEntity();
        e.setName("name");
        e.setDesc("description");
        e.setOriginalId(null);
        e.setImage(Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888));

        return e;
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

    public void testFindOriginals() {
        dao.create(createImageEntity());
        List<ImageEntity> originals = dao.getHistory();
        assertFalse(originals.isEmpty());
    }
}