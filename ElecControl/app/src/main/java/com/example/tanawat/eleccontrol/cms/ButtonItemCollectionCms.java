package com.example.tanawat.eleccontrol.cms;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonItemCollectionCms implements Parcelable {

    private List<ButtonItemCms> data;

    public ButtonItemCollectionCms(List<ButtonItemCms> data) {
        this.data = data;
    }

    protected ButtonItemCollectionCms(Parcel in) {
        data = in.createTypedArrayList(ButtonItemCms.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
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
}
