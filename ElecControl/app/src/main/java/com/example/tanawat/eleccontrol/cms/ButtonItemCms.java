package com.example.tanawat.eleccontrol.cms;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class
ButtonItemCms implements Parcelable {
    private String id;
    private String name;
    private String status;
    private String type;
    private String value;
    private String checked ;

    public ButtonItemCms() {

    }

    protected ButtonItemCms(Parcel in) {
        id = in.readString();
        name = in.readString();
        status = in.readString();
        type = in.readString();
        value = in.readString();
        checked = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(type);
        dest.writeString(value);
        dest.writeString(checked);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ButtonItemCms> CREATOR = new Creator<ButtonItemCms>() {
        @Override
        public ButtonItemCms createFromParcel(Parcel in) {
            return new ButtonItemCms(in);
        }

        @Override
        public ButtonItemCms[] newArray(int size) {
            return new ButtonItemCms[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
