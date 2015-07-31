/*
 * Copyright (c) 2015. Jay Paulynice (jay.paulynice@gmail.com)
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

package com.jigdraw.draw.service;

import android.graphics.Bitmap;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.jigdraw.draw.model.enums.Difficulty;
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