package com.example.tanawat.eleccontrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
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
import com.example.tanawat.eleccontrol.activity.MainActivity;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.example.tanawat.eleccontrol.view.ButtonListItem;
import com.google.gson.Gson;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ButtonListItem item;
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (convertView != null) {
//            item = (ButtonListItem) convertView;
//
//        } else {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_button, null);
            holder = new ViewHolder();
            holder.tvNameCommand = (TextView) convertView.findViewById(R.id.tvNameCommand);
            holder.tvNameType = (TextView) convertView.findViewById(R.id.tvTypeCommand);
            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }

        btnDelete = (Button) convertView.findViewById(R.id.btnDeleted);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder adb=new AlertDialog.Builder(activity);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);

                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        String jsonRead = pref.getString("json", null);
                        buttonItemCollectionCms = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
                        buttonItemCollectionCms.deleteData(positionToRemove);
                        String json = new Gson().toJson(buttonItemCollectionCms);
                        editor.putString("json", json);
                        Log.d("saveAdd", buttonItemCollectionCms.getData().toString());
                        editor.apply();
                        
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setButtonItemCollectionCms(buttonItemCollectionCms);
                                notifyDataSetChanged();
                            }
                        }, 3000);

//                        notifyDataSetChanged();

                    }});
                adb.show();

            }
        });


        ButtonItemCms buttonItemCms = (ButtonItemCms) getItem(position);
        if (buttonItemCms != null) {
            Log.d("testD",String.valueOf(position));
            if(buttonItemCms.getName() !=null){
                if(holder!=null){
                    holder.tvNameCommand.setText(buttonItemCms.getName());
                    holder.tvNameType.setText(buttonItemCms.getType());
                    notifyDataSetChanged();
                }

            }
            else{
                Log.d("testD","name_null");
            }
            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());

        }

        return convertView;
    }


}
