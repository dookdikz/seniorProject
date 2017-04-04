package com.example.tanawat.eleccontrol.cms;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanawat on 31/1/2560.
 */
public class ListScene implements Parcelable {
    private List<ButtonItemCollectionCms> data = new ArrayList<>();

    public void setData(List<ButtonItemCollectionCms> data) {
        this.data = data;
    }

    public ListScene() {

    }

    protected ListScene(Parcel in) {
        data = in.createTypedArrayList(ButtonItemCollectionCms.CREATOR);
    }

    public static final Creator<ListScene> CREATOR = new Creator<ListScene>() {
        @Override
        public ListScene createFromParcel(Parcel in) {
            return new ListScene(in);
        }

        @Override
        public ListScene[] newArray(int size) {
            return new ListScene[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    public List<ButtonItemCollectionCms> getData() {
        return data;
    }

    public void addData(ButtonItemCollectionCms buttonItemCollectionCms) {
        data.add(buttonItemCollectionCms);

    }
    public void editData(ButtonItemCollectionCms buttonItemCollectionCms){


        for(int i=0;i<data.size();i++){
            Log.d("NameEdit",buttonItemCollectionCms.getName());
            if(buttonItemCollectionCms.getId().equals(data.get(i).getId())){
                data.set(i,buttonItemCollectionCms);
            }
        }

    }

    public void deleteData(int position) {
            data.remove(position);
    }
}
