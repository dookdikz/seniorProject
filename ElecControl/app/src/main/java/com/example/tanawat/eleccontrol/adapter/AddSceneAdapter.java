package com.example.tanawat.eleccontrol.adapter;

import android.content.Context;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.activity.AddSceneActivity;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.fragment.SetTempAirDialog;
import com.example.tanawat.eleccontrol.fragment.SetTempDiaLogFragment;
import com.example.tanawat.eleccontrol.view.ButtonListItem;

import java.util.ArrayList;



/**
 * Created by Tanawat on 3/2/2560.
 */
public class AddSceneAdapter extends BaseAdapter {
    ButtonItemCollectionCms buttonItemCollectionCms;
    Context activity;

    ArrayList<Boolean> checkAdd = new ArrayList<>();
    ArrayList<Boolean> checkOnOrOff = new ArrayList<>();
//    private Spinner spinAddCommand;


    public ArrayList<Boolean> getCheckOnOrOff() {
        return checkOnOrOff;
    }

    public ArrayList<Boolean> getCheckAdd() {
        return checkAdd;
    }

    CheckBox cbAddScene;
    CheckBox ivOnOrOff;

    public ButtonItemCollectionCms getButtonItemCollectionCms() {
        return buttonItemCollectionCms;
    }

    public void setButtonItemCollectionCms(ButtonItemCollectionCms buttonItemCollectionCms) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
    }
    public AddSceneAdapter() {

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
        ImageView ivTabTool;
        TextView tvTempAir;


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
        checkOnOrOff.add(false);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_add_scene, null);
            holder = new ViewHolder();
            holder.tvNameCommand = (TextView) convertView.findViewById(R.id.tvNameCommand);
            holder.tvNameType = (TextView) convertView.findViewById(R.id.tvTypeCommand);
            holder.ivTabTool = (ImageView) convertView.findViewById(R.id.ivTabTool);
            holder.tvTempAir = (TextView) convertView.findViewById(R.id.tvTempAir);
            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }

        final ButtonItemCms buttonItemCms = (ButtonItemCms) getItem(position);
        if (buttonItemCms != null) {
            if (buttonItemCms.getName() != null) {
                if (holder != null) {

                    holder.tvNameCommand.setText(buttonItemCms.getName());
                    holder.tvNameType.setText(buttonItemCms.getType());
                    if(buttonItemCollectionCms.getData().get(position).getType().equals("Air")|| buttonItemCollectionCms.getData().get(position).getType().equals("Tv")){
                        holder.ivTabTool.setImageResource(R.drawable.remote_icon);
                    }
                    else if(buttonItemCollectionCms.getData().get(position).getType().equals("Switch1") || buttonItemCollectionCms.getData().get(position).getType().equals("Switch2")){
                        holder.ivTabTool.setImageResource(R.drawable.switch_icon);
                    }
                    else {
                        holder.ivTabTool.setImageResource(R.drawable.curtain_icon);
                    }
                    if(buttonItemCollectionCms.getData().get(position).getType().equals("Air")){
                        holder.tvTempAir.setVisibility(View.VISIBLE);
                    }


                }

            }

            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());


        }

holder.tvTempAir.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FragmentTransaction ft =((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
        Fragment prev = ((FragmentActivity) activity).getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SetTempAirDialog.newInstance(position);
        newFragment.show(ft, "dialog");

    }
});
        ivOnOrOff = (CheckBox) convertView.findViewById(R.id.ivOnOrOff) ;
        ivOnOrOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOnOrOff.get(position) == true) {
                    checkOnOrOff.set(position, false);
                } else {
                    checkOnOrOff.set(position, true);
                }

//                Log.d("position", String.valueOf(position));
//                if(ivOnOrOff.isChecked()){
//
//
//                    buttonItemCms.setstatus("Off");
//                }
//                else if(!ivOnOrOff.isChecked()){
//
//                    buttonItemCms.setstatus("On");
//                }
//
//                Log.d("position", buttonItemCms.getName()+" "+buttonItemCms.getstatus());
            }
        });
        cbAddScene = (CheckBox) convertView.findViewById(R.id.cbAddScene);
        cbAddScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAdd.get(position) == true) {
                    checkAdd.set(position, false);
                } else {
                    checkAdd.set(position, true);
                }

                Log.d("positionAll", checkAdd.toString());
            }
        });


        return convertView;


    }
    public void setTempAir(int position,String temp){
        buttonItemCollectionCms.getData().get(position).setValue(temp);
    }

//    public void checkBoxClicked(int positionClicked){
//        if (checkAdd.get(positionClicked) == true) {
//            checkAdd.set(positionClicked, false);
//            cbAddScene.setChecked(false);
//        } else {
//            checkAdd.set(positionClicked, true);
//            cbAddScene.setChecked(true);
//        }
//        Log.d("positionAll", checkAdd.toString());
//
//    }


}
