package com.jigdraw.draw.adapter;

import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractGridAdapter extends BaseAdapter implements
        GridAdapter {
    public static final int INVALID_ID = -1;
    private int nextStableId = 0;
    private HashMap<Object, Integer> mIdMap = new HashMap<>();

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