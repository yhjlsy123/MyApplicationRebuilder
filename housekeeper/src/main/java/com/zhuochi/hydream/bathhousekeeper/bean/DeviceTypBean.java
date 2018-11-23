package com.zhuochi.hydream.bathhousekeeper.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceTypBean implements Parcelable {


    /**
     * name : faucet
     * name_text : 洗浴
     * icon : http://zaotang.94feel.com/static/images/management_shower.png
     */

    private String name;
    private String name_text;
    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_text() {
        return name_text;
    }

    public void setName_text(String name_text) {
        this.name_text = name_text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.name_text);
        dest.writeString(this.icon);
    }

    public DeviceTypBean() {
    }

    protected DeviceTypBean(Parcel in) {
        this.name = in.readString();
        this.name_text = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<DeviceTypBean> CREATOR = new Parcelable.Creator<DeviceTypBean>() {
        @Override
        public DeviceTypBean createFromParcel(Parcel source) {
            return new DeviceTypBean(source);
        }

        @Override
        public DeviceTypBean[] newArray(int size) {
            return new DeviceTypBean[size];
        }
    };
}
