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

package com.jigdraw.draw.adapter;

import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractAdapter extends BaseAdapter implements
        OrderableAdapter {
    public static final int INVALID_ID = -1;
    private int nextStableId = 0;
    private Map<Object, Integer> mIdMap = new HashMap<>();

    @Override
    public final boolean hasStableIds() {
        return true;
    }

    protected void addAllStableId(List<?> items) {
        for (Object item : items) {
            addStableId(item);
        }
    }

    protected void addStableId(Object item) {
        mIdMap.put(item, nextStableId++);
    }

    @Override
    public final long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        Object item = getItem(position);
        return mIdMap.get(item);
    }
}
