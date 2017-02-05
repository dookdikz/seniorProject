package com.example.tanawat.eleccontrol.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.view.ButtonListItem;

import java.util.ArrayList;

/**
 * Created by Tanawat on 3/2/2560.
 */
public class AddSceneAdapter extends BaseAdapter {
    ButtonItemCollectionCms buttonItemCollectionCms;
    Context activity;
   ArrayList<Boolean> checkAdd = new ArrayList<>();
    private Spinner spinAddCommand;

    public ArrayList<Boolean> getCheckAdd() {
        return checkAdd;
    }

    CheckBox cbAddScene;

    public ButtonItemCollectionCms getButtonItemCollectionCms() {
        return buttonItemCollectionCms;
    }

    public void setButtonItemCollectionCms(ButtonItemCollectionCms buttonItemCollectionCms) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
    }

    public AddSceneAdapter(ButtonItemCollectionCms buttonItemCollectionCms, Context activity) {

        this.buttonItemCollectionCms = buttonItemCollectionCms;
        this.activity = activity;
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
        checkAdd.add(false);
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_add_scene, null);
            holder = new ViewHolder();
            holder.tvNameCommand = (TextView) convertView.findViewById(R.id.tvNameCommand);
            holder.tvNameType = (TextView) convertView.findViewById(R.id.tvTypeCommand);
            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }

        final ButtonItemCms buttonItemCms = (ButtonItemCms) getItem(position);
        if (buttonItemCms != null) {
            if(buttonItemCms.getName() !=null){
                if(holder!=null){

                    holder.tvNameCommand.setText(buttonItemCms.getName());
                    holder.tvNameType.setText(buttonItemCms.getType());


                }

            }
            spinAddCommand = (Spinner) convertView.findViewById(R.id.spinOnOrOff);
            final String[] onOrOff = convertView.getResources().getStringArray(R.array.onOrOff);
            final ArrayAdapter<String> adapterType = new ArrayAdapter<String>(convertView.getContext(),
                    android.R.layout.simple_dropdown_item_1line,onOrOff);
            spinAddCommand.setAdapter(adapterType);
            spinAddCommand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if( buttonItemCms.getstatus().equals("On")){
                    buttonItemCms.setstatus("Off");

                }
                else if(buttonItemCms.getstatus().equals("Off")){
                   buttonItemCms.setstatus("On");

                }
                Log.d("spin",buttonItemCms.getstatus());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            });
            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());


        }
        cbAddScene = (CheckBox) convertView.findViewById(R.id.cbAddScene);
        cbAddScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("position", String.valueOf(position));
                if(checkAdd.get(position)==true){
                    checkAdd.set(position, false);
                }
                else{
                    checkAdd.set(position, true);
                }

                Log.d("positionAll", checkAdd.toString());
            }
        });


        return convertView;



    }
}
