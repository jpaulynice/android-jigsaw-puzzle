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

package com.jigdraw.draw.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class JigsawBaseAdapter extends AbstractBaseAdapter {
    private Context context;
    private List<Object> items = new ArrayList<>();
    private int columns;

    JigsawBaseAdapter(Context context, List<?> items, int columnCount) {
        this.context = context;
        this.columns = columnCount;
        init(items);
    }

    private void init(List<?> items) {
        addAllStableId(items);
        this.items.addAll(items);
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
        if (newPosition < getCount()) {
            Collections.swap(items, originalPosition, newPosition);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getColumnCount() {
        return columns;
    }

    protected Context getContext() {
        return context;
    }
}
