package com.jigdraw.draw.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by julespaulynice on 2/12/15.
 */
public class LongParceable implements Parcelable {
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
    private long mData;


    private LongParceable(Parcel in) {
        mData = in.readLong();
    }

    public LongParceable(long in) {
        mData = in;
    }

    public long getmData() {
        return mData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mData);
    }
}
