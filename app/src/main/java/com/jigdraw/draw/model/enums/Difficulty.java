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

package com.jigdraw.draw.model.enums;

/**
 * Jigsaw difficulty levels
 *
 * @author Jay Paulynice
 */
public enum Difficulty {
    EASY(2), MEDIUM(4), HARD(8);
    private int value;

    Difficulty(int value) {
        this.value = value;
    }

    public static Difficulty fromValue(int which) {
        switch (which) {
            case 0:
                return EASY;
            case 1:
                return MEDIUM;
            case 2:
                return HARD;
            default:
                throw new IllegalArgumentException("Unknown level");
        }
    }

    public int getValue() {
        return value;
    }
}