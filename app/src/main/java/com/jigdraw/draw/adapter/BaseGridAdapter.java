package com.jigdraw.draw.adapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseGridAdapter extends AbstractGridAdapter {
    private Context mContext;
    private ArrayList<Object> mItems = new ArrayList<>();
    private int mColumnCount;

    public BaseGridAdapter(Context context, List<?> items, int columnCount) {
        mContext = context;
        mColumnCount = columnCount;
        init(items);
    }

    private void init(List<?> items) {
        addAllStableId(items);
        this.mItems.addAll(items);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getColumnCount() {
        return mColumnCount;
    }

    @Override
    public void reorderItems(int originalPosition, int newPosition) {
        if (newPosition < getCount()) {
            Collections.swap(mItems, originalPosition, newPosition);
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean canReorder(int position) {
        return true;
    }

    protected Context getContext() {
        return mContext;
    }
}