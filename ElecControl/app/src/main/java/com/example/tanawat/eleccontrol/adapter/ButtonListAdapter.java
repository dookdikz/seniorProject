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
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.view.ButtonListItem;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ButtonListAdapter extends BaseAdapter {
    ButtonItemCollectionCms buttonItemCollectionCms;
    Button btnDelete;
    private Context activity;

    public ButtonListAdapter(ButtonItemCollectionCms buttonItemCollectionCms, Context activity) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
        this.activity = activity;
    }

    public ButtonItemCollectionCms getButtonItemCollectionCms() {
        return buttonItemCollectionCms;
    }

    public void setButtonItemCollectionCms(ButtonItemCollectionCms buttonItemCollectionCms) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
    }

    @Override
    public int getCount() {
        if (buttonItemCollectionCms != null) {
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

    private class ViewHolder {
        TextView tvNameCommand;
        TextView tvNameType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ButtonListItem item;
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView != null) {
            item = (ButtonListItem) convertView;

        } else {
            convertView = inflater.inflate(R.layout.list_item_button, null);
            holder = new ViewHolder();
            holder.tvNameCommand = (TextView) convertView.findViewById(R.id.tvNameCommand);
            holder.tvNameType = (TextView) convertView.findViewById(R.id.tvTypeCommand);
            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }
        ButtonItemCms buttonItemCms = (ButtonItemCms) getItem(position);
        if (buttonItemCms != null) {
            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());
            holder.tvNameCommand.setText(buttonItemCms.getName());
            holder.tvNameType.setText(buttonItemCms.getType());
        }
        TextView tvTest = (TextView) convertView.findViewById(R.id.tvNameCommand);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("eiei", "eiei");
            }
        });
        btnDelete = (Button) convertView.findViewById(R.id.btnDeleted);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity.getApplicationContext(), "fdsfsdf", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


}
