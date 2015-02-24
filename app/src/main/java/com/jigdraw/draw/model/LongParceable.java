package com.jigdraw.draw.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LongParceable implements Parcelable {
    private long data;

    public static final Parcelable.Creator<LongParceable> CREATOR = new Parcelable.Creator<LongParceable>() {
        @Override
        public LongParceable createFromParcel(Parcel in) {
            return new LongParceable(in);
        }

        @Override
        public LongParceable[] newArray(int size) {
            return new LongParceable[size];
        }
    };
    public LongParceable(long in) {
        data = in;
    }

    private LongParceable(Parcel in) {
        data = in.readLong();
    }

    public long getData() {
        return data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(data);
    }
}