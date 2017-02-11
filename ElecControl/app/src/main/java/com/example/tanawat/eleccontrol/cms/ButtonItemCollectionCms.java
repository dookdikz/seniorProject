package com.example.tanawat.eleccontrol.cms;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonItemCollectionCms implements Parcelable {

    private String name;
    private int id;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private List<ButtonItemCms> data = new ArrayList<>();

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ButtonItemCollectionCms() {


    }

    protected ButtonItemCollectionCms(Parcel in) {
        name = in.readString();
        data = in.createTypedArrayList(ButtonItemCms.CREATOR);
        id = in.readInt();
        time = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(data);
        dest.writeInt(id);
        dest.writeString(time);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ButtonItemCollectionCms> CREATOR = new Creator<ButtonItemCollectionCms>() {
        @Override
        public ButtonItemCollectionCms createFromParcel(Parcel in) {
            return new ButtonItemCollectionCms(in);
        }

        @Override
        public ButtonItemCollectionCms[] newArray(int size) {
            return new ButtonItemCollectionCms[size];
        }
    };

    public List<ButtonItemCms> getData() {
        return data;
    }

    public void setData(List<ButtonItemCms> data) {
        this.data = data;
    }

    public void addData(ButtonItemCms cms){
        data.add(cms);
    }
    public void  deleteData(int position){

        data.remove(position);

    }
}
