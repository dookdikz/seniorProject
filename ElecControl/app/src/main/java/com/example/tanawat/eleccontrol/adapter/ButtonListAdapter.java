package com.example.tanawat.eleccontrol.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import com.example.tanawat.eleccontrol.view.ButtonListItem;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonListAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        if(position%2==0){
            SharedPreferences pref = parent.getContext().getSharedPreferences("dummy", Context.MODE_PRIVATE);
            item.setNameText(pref.getString("Hello",null));

        }
        return item;
    }
}
