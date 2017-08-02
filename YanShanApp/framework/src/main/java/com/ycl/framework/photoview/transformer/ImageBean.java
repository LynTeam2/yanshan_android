package com.ycl.framework.photoview.transformer;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageBean implements Parcelable {

    private String parentName;
    private long size;
    private String displayName;
    private String path;
    private boolean isChecked;


    public ImageBean() {
        super();
    }

    public ImageBean(String path) {
        super();
        this.path = path;
    }

    public ImageBean(String parentName, long size, String displayName,
                     String path, boolean isChecked) {
        super();
        this.parentName = parentName;
        this.size = size;
        this.displayName = displayName;
        this.path = path;
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "ImageBean [parentName=" + parentName + ", size=" + size
                + ", displayName=" + displayName + ", path=" + path
                + ", isChecked=" + isChecked + "]";
    }


    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(parentName);
        out.writeLong(size);
        out.writeString(displayName);
        out.writeString(path);
        out.writeInt(isChecked ? 1 : 0);
    }

    public static final Creator<ImageBean> CREATOR = new Creator<ImageBean>() {
        public ImageBean createFromParcel(Parcel in) {
            return new ImageBean(in);
        }

        public ImageBean[] newArray(int size) {
            return new ImageBean[size];
        }
    };

    private ImageBean(Parcel in) {
        parentName = in.readString();
        size = in.readLong();
        displayName = in.readString();
        path = in.readString();
        isChecked = in.readInt() == 0;
    }

}
