package com.example.tanawat.eleccontrol.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.view.ButtonListItem;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonListAdapter extends BaseAdapter {
    ButtonItemCollectionCms buttonItemCollectionCms;

    public ButtonItemCollectionCms getButtonItemCollectionCms() {
        return buttonItemCollectionCms;
    }

    public void setButtonItemCollectionCms(ButtonItemCollectionCms buttonItemCollectionCms) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
    }

    @Override
    public int getCount() {
        return buttonItemCollectionCms.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return buttonItemCollectionCms.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ButtonListItem item;

       if(convertView != null){
           item = (ButtonListItem) convertView;
       }
        else {
           item = new ButtonListItem(parent.getContext());
       }
        ButtonItemCms buttonItemCms =(ButtonItemCms) getItem(position) ;
        item.setTvNameText(buttonItemCms.getName());
        item.setTvTypeText(buttonItemCms.getType());
        return item;
    }
}
