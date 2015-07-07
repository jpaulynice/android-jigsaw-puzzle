package com.jigdraw.draw.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseGridAdapter extends AbstractGridAdapter {
    private Context context;
    private List<Object> items = new ArrayList<>();

    private int columns;

    public BaseGridAdapter(Context context, List<?> items, int columnCount) {
        this.context = context;
        this.columns = columnCount;
        init(items);
    }

    private void init(List<?> items) {
        addAllStableId(items);
        this.items.addAll(items);
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
    public void reorderItems(int originalPosition, int newPosition) {
        if (newPosition < getCount()) {
            Collections.swap(items, originalPosition, newPosition);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getColumnCount() {
        return columns;
    }

    @Override
    public boolean canReorder(int position) {
        return true;
    }

    protected Context getContext() {
        return context;
    }
}