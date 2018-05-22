package com.example.manifestexplorer;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Comparator;

public class AppliInfo implements Parcelable,Comparable<AppliInfo> {
    private Bitmap appIcon;
    private String appTitle;
    private String publicSourceDir;

    public AppliInfo(String name,Bitmap image,String dir) {
        appTitle = name;
        appIcon = image;
        publicSourceDir = dir;
    }

    protected AppliInfo(Parcel in) {
        appTitle = in.readString();
        publicSourceDir = in.readString();
        appIcon = (Bitmap)in.readValue(Bitmap.class.getClassLoader());
    }
    public static final Creator<AppliInfo> CREATOR = new Creator<AppliInfo>() {
        @Override
        public AppliInfo createFromParcel(Parcel in) {
            return new AppliInfo(in);
        }

        @Override
        public AppliInfo[] newArray(int size) {
            return new AppliInfo[size];
        }
    };

    public String getTitle() {
        return appTitle;
    }

    public String getPublicSourceDir() {
        return publicSourceDir;
    }

    public Bitmap getIcon() {
        return appIcon;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appTitle);
        dest.writeString(publicSourceDir);
        dest.writeValue(appIcon);
    }

    @Override
    public int compareTo(@NonNull AppliInfo o) {

        return appTitle.compareTo(o.getTitle());
    }
}