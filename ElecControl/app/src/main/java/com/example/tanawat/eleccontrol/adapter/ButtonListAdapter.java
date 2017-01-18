package com.example.tanawat.eleccontrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.view.ButtonListItem;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonListAdapter extends BaseAdapter  {
    ButtonItemCollectionCms buttonItemCollectionCms;

    private Context context;

    public ButtonListAdapter( Context context) {

        this.context = context;
    }

    public ButtonItemCollectionCms getButtonItemCollectionCms() {
        return buttonItemCollectionCms;
    }

    public void setButtonItemCollectionCms(ButtonItemCollectionCms buttonItemCollectionCms) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
    }

    @Override
    public int getCount() {
        if(buttonItemCollectionCms!=null){
            return buttonItemCollectionCms.getData().size();
        }
      return 0;
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
        LayoutInflater inflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

       if(convertView != null){
           item = (ButtonListItem) convertView;

       }
        else {
           convertView = inflater.inflate(R.layout.list_item_button,null);
           item = new ButtonListItem(parent.getContext());
       }
        ButtonItemCms buttonItemCms =(ButtonItemCms) getItem(position) ;
        if(buttonItemCms!=null) {
            item.setTvNameText(buttonItemCms.getName());
            item.setTvTypeText(buttonItemCms.getType());

        }
        Button btnDelete =(Button) convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("eiei","eiei");
            }
        });

        return item;
    }


}
