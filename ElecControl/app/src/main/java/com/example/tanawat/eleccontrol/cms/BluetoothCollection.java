package com.example.tanawat.eleccontrol.cms;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dookdikz on 7/4/2560.
 */

public class BluetoothCollection implements Parcelable {
    private List<String> data = new ArrayList<>();

    public BluetoothCollection() {

    }

    protected BluetoothCollection(Parcel in) {
        data = in.createStringArrayList();
    }

    public static final Creator<BluetoothCollection> CREATOR = new Creator<BluetoothCollection>() {
        @Override
        public BluetoothCollection createFromParcel(Parcel in) {
            return new BluetoothCollection(in);
        }

        @Override
        public BluetoothCollection[] newArray(int size) {
            return new BluetoothCollection[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(data);
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
    public void addData(String mac){
        this.data.add(mac);
    }
}
