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

import com.jigdraw.draw.model.enums.Difficulty;

/**
 * Simple interface for jigsaw manipulations.
 *
 * @author Jay Paulynice
 */
public interface JigsawService {
    /**
     * Create jisaw puzzle from given original image and difficulty level
     *
     * @param original the original image to create jigsaw puzzle
     * @param level difficulty level
     * @return the id of the original image once saved
     */
    Long create(Bitmap original, Difficulty level);
}