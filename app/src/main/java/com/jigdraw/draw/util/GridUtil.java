package com.jigdraw.draw.util;

import android.view.View;

import java.util.List;

public class GridUtil {
    public static void reorder(List list, int indexFrom, int indexTwo) {
        Object obj = list.remove(indexFrom);
        list.add(indexTwo, obj);
    }

    public static void swap(List list, int firstIndex, int secondIndex) {
        Object firstObject = list.get(firstIndex);
        Object secondObject = list.get(secondIndex);
        list.set(firstIndex, secondObject);
        list.set(secondIndex, firstObject);
    }

    public static float getViewX(View view) {
        return Math.abs((view.getRight() - view.getLeft()) / 2);
    }

    public static float getViewY(View view) {
        return Math.abs((view.getBottom() - view.getTop()) / 2);
    }
}
