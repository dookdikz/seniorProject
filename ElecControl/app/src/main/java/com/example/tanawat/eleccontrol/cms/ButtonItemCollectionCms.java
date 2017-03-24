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
    private String id;
    private String time;
    private String temp;
    private String bluetooth;
    private String light;
    private int numId;
    private String checkTime;
    private String checkBluetooth;
    private String checkLightSen;
    private String checkTempSen;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getCheckBluetooth() {
        return checkBluetooth;
    }

    public void setCheckBluetooth(String checkBluetooth) {
        this.checkBluetooth = checkBluetooth;
    }

    public String getCheckLightSen() {
        return checkLightSen;
    }

    public void setCheckLightSen(String checkLightSen) {
        this.checkLightSen = checkLightSen;
    }

    public String getCheckTempSen() {
        return checkTempSen;
    }

    public void setCheckTempSen(String checkTempSen) {
        this.checkTempSen = checkTempSen;
    }



    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        id = in.readString();
        time = in.readString();
        temp = in.readString();
        light = in.readString();
        bluetooth = in.readString();
        numId = in.readInt();
        checkTime = in.readString();
        checkBluetooth = in.readString();
        checkTempSen = in.readString();
        checkLightSen = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(data);
        dest.writeString(id);
        dest.writeString(time);
        dest.writeString(bluetooth);
        dest.writeString(temp);
        dest.writeString(light);
        dest.writeInt(numId);
        dest.writeString(checkTime);
        dest.writeString(checkTempSen);
        dest.writeString(checkLightSen);
        dest.writeString(checkBluetooth);
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
