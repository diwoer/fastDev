package com.di.demo;

import android.os.Parcel;
import android.os.Parcelable;

import com.di.demo.data.bean.UrlPositionBean;

public class DLProgress implements Parcelable {

    public long progress;
    public long totalLength;
    public UrlPositionBean urlPosition;
    public int percentProgress;

    public DLProgress(long progress, long totalLength, UrlPositionBean urlPosition, int percentProgress) {
        this.progress = progress;
        this.totalLength = totalLength;
        this.urlPosition = urlPosition;
        this.percentProgress = percentProgress;
    }

    protected DLProgress(Parcel in) {
        progress = in.readLong();
        totalLength = in.readLong();
        urlPosition = in.readParcelable(UrlPositionBean.class.getClassLoader());
        percentProgress = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(progress);
        dest.writeLong(totalLength);
        dest.writeParcelable(urlPosition, flags);
        dest.writeInt(percentProgress);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DLProgress> CREATOR = new Creator<DLProgress>() {
        @Override
        public DLProgress createFromParcel(Parcel in) {
            return new DLProgress(in);
        }

        @Override
        public DLProgress[] newArray(int size) {
            return new DLProgress[size];
        }
    };
}
