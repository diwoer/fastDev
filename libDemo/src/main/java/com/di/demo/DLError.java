package com.di.demo;

import android.os.Parcel;
import android.os.Parcelable;

import com.di.demo.data.bean.UrlPositionBean;

public class DLError implements Parcelable {

    /**
     * 通用错误码
     * */
    public static final int COMMON = 0;

    /**
     * 错误码
     * */
    public int code;

    /**
     * 错误描述
     * */
    public String description;

    /**
     * 列表中的位置
     * */
    public UrlPositionBean urlPosition;

    public DLError(String description, UrlPositionBean urlPosition){
        this(COMMON, description, urlPosition);
    }

    public DLError(int code, String description, UrlPositionBean urlPosition){
        this.code = code;
        this.description = description;
        this.urlPosition = urlPosition;
    }

    protected DLError(Parcel in) {
        code = in.readInt();
        description = in.readString();
        urlPosition = in.readParcelable(UrlPositionBean.class.getClassLoader());
    }

    public static final Creator<DLError> CREATOR = new Creator<DLError>() {
        @Override
        public DLError createFromParcel(Parcel in) {
            return new DLError(in);
        }

        @Override
        public DLError[] newArray(int size) {
            return new DLError[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(description);
        dest.writeParcelable(urlPosition, flags);
    }
}
