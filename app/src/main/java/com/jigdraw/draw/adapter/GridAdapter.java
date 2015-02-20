package com.jigdraw.draw.adapter;

public interface GridAdapter {
    void reorderItems(int originalPosition, int newPosition);

    int getColumnCount();

    boolean canReorder(int position);
}
