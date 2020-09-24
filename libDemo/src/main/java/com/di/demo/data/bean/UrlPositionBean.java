package com.di.demo.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class UrlPositionBean implements Parcelable {

    private String url;
    private int position;

    public UrlPositionBean(String url, int position) {
        this.url = url;
        this.position = position;
    }

    protected UrlPositionBean(Parcel in) {
        url = in.readString();
        position = in.readInt();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static final Creator<UrlPositionBean> CREATOR = new Creator<UrlPositionBean>() {
        @Override
        public UrlPositionBean createFromParcel(Parcel in) {
            return new UrlPositionBean(in);
        }

        @Override
        public UrlPositionBean[] newArray(int size) {
            return new UrlPositionBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlPositionBean that = (UrlPositionBean) o;
        return position == that.position &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, position);
    }
}
