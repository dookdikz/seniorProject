package com.example.tanawat.eleccontrol.cms;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonItemCms implements Parcelable {
    private int id;
    private String name;
    private String roomName;
    private String type;
    public ButtonItemCms() {

    }

    protected ButtonItemCms(Parcel in) {
        id = in.readInt();
        name = in.readString();
        roomName = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(roomName);
        dest.writeString(type);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
