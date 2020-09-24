package com.di.demo;

import android.os.Parcel;
import android.os.Parcelable;

import com.di.demo.data.bean.UrlPositionBean;

public class DLSuccess implements Parcelable {

    /**
     * 下载文件路径
     */
    public String path;

    /**
     * 文件下载成功码
     * 区分是下载 还是 本地已经存在
     */
    public int code;

    /**
     * 列表下载，用于确认列表中的位置
     * */
    public UrlPositionBean urlPosition;

    /**
     * 下载
     */
    public static final int DL_SUCCESS_CODE_DOWNLOAD = 0;

    /**
     * 本地存在
     */
    public static final int DL_SUCCESS_CODE_EXIST = 1;

    public DLSuccess(){
        this.code = DL_SUCCESS_CODE_DOWNLOAD;
    }

    protected DLSuccess(Parcel in) {
        path = in.readString();
        code = in.readInt();
        urlPosition = in.readParcelable(UrlPositionBean.class.getClassLoader());
    }

    public static final Creator<DLSuccess> CREATOR = new Creator<DLSuccess>() {
        @Override
        public DLSuccess createFromParcel(Parcel in) {
            return new DLSuccess(in);
        }

        @Override
        public DLSuccess[] newArray(int size) {
            return new DLSuccess[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeInt(code);
        dest.writeParcelable(urlPosition, flags);
    }
}
