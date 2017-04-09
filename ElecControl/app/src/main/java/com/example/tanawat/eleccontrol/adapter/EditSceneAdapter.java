package com.example.tanawat.eleccontrol.adapter;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.fragment.EditSceneFragment;
import com.example.tanawat.eleccontrol.fragment.EditTempAirDialog;
import com.example.tanawat.eleccontrol.view.ButtonListItem;

import java.util.ArrayList;

/**
 * Created by Tanawat on 3/2/2560.
 */
public class EditSceneAdapter extends BaseAdapter {
    static ButtonItemCollectionCms buttonItemCollectionCms;
    static int sizeOftype;
    static ButtonItemCollectionCms editScene;
    TextView tvTempAir;

    public ButtonItemCollectionCms getEditScene() {
        return editScene;
    }

    public void setEditScene(ButtonItemCollectionCms editScene) {
        this.editScene = editScene;
    }

    Context activity;

    public void setCheckEdit(ArrayList<Boolean> checkEdit) {
        this.checkEdit = checkEdit;
    }

    static ArrayList<Boolean> checkEdit;
    static ArrayList<Boolean> checkOnOrOff = new ArrayList<>();
//    private Spinner spinEditCommand;


    public static void setCheckOnOrOff(ArrayList<Boolean> checkOnOrOff) {
        EditSceneAdapter.checkOnOrOff = checkOnOrOff;
    }

    public ArrayList<Boolean> getCheckOnOrOff() {
        return checkOnOrOff;
    }

    public ArrayList<Boolean> getCheckEdit() {
        return checkEdit;
    }

    CheckBox cbEditScene;
    CheckBox ivOnOrOff;


    public ButtonItemCollectionCms getButtonItemCollectionCms() {
        return buttonItemCollectionCms;
    }

    public void setButtonItemCollectionCms(ButtonItemCollectionCms buttonItemCollectionCms) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
    }

    public EditSceneAdapter() {

    }

    public EditSceneAdapter(ButtonItemCollectionCms buttonItemCollectionCms, ArrayList<Boolean> checkEdit, Context activity) {

        this.buttonItemCollectionCms = buttonItemCollectionCms;
        this.checkEdit = checkEdit;
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


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ButtonListItem item;

        checkOnOrOff.add(false);
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (convertView != null) {
//            item = (ButtonListItem) convertView;
//
//        } else {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_edit_scene, null);
            holder = new ViewHolder();
            holder.tvNameCommand = (TextView) convertView.findViewById(R.id.tvNameCommand);
            holder.tvNameType = (TextView) convertView.findViewById(R.id.tvTypeCommand);
            holder.ivTabTool = (ImageView) convertView.findViewById(R.id.ivTabTool);

            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }

        final ButtonItemCms buttonItemCms = (ButtonItemCms) getItem(position);
        if (buttonItemCms != null) {
            if (buttonItemCms.getName() != null) {
                if (holder != null) {

                    holder.tvNameCommand.setText(buttonItemCms.getName());
                    holder.tvNameType.setText(buttonItemCms.getType());
                    if (buttonItemCollectionCms.getData().get(position).getType().equals("Air") || buttonItemCollectionCms.getData().get(position).getType().equals("Tv")) {
                        holder.ivTabTool.setImageResource(R.drawable.remote_icon);
                    } else if (buttonItemCollectionCms.getData().get(position).getType().equals("Switch1") || buttonItemCollectionCms.getData().get(position).getType().equals("Switch2")) {
                        holder.ivTabTool.setImageResource(R.drawable.switch_icon);
                    } else {
                        holder.ivTabTool.setImageResource(R.drawable.curtain_icon);
                    }


                }

            }

            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());


        }


        checkOnOrOff.set(position, false);
        checkEdit.set(position, false);
        ivOnOrOff = (CheckBox) convertView.findViewById(R.id.ivOnOrOff);
        ivOnOrOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOnOrOff.get(position) == true) {
                    checkOnOrOff.set(position, false);
                    buttonItemCollectionCms.getData().get(position).setstatus("Off");
                } else {
                    checkOnOrOff.set(position, true);
                    buttonItemCollectionCms.getData().get(position).setstatus("On");
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
        tvTempAir = (TextView) convertView.findViewById(R.id.tvTempAir);
        if (buttonItemCollectionCms.getData().get(position).getType().equals("Air")) {
            tvTempAir.setVisibility(View.VISIBLE);
            if(buttonItemCollectionCms.getData().get(position).getValue()!=null){
                tvTempAir.setText(buttonItemCollectionCms.getData().get(position).getValue());
            }
        }

        tvTempAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonItemCollectionCms(buttonItemCollectionCms);
//                for(int i=0;i<buttonItemCollectionCms.getData().size();i++){
//                    for(int j=0;j<editScene.getData().size();j++){
//                        if(buttonItemCollectionCms.getData().get(i).getId().equals(editScene.getData().get(j).getId())){
//                            editScene.getData().get(j).setstatus();
//                        }
//                    }
//                }
                setCheckEdit(checkEdit);
                setCheckOnOrOff(checkOnOrOff);
                FragmentTransaction ft = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                Fragment prev = ((FragmentActivity) activity).getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                // Create and show the dialog.
                DialogFragment newFragment = EditTempAirDialog.newInstance(position);

                newFragment.show(ft, "dialog");


            }
        });

        cbEditScene = (CheckBox) convertView.findViewById(R.id.cbEditScene);

//        for (int j = 0; j < editScene.getData().size(); j++) {
//            if (buttonItemCollectionCms.getData().get(position).getId().equals(editScene.getData().get(j).getId())) {
//                buttonItemCollectionCms.getData().get(position).setValue(editScene.getData().get(j).getValue());
//                checkEdit.set(position, true);
//                cbEditScene.setChecked(true);
//                if (editScene.getData().get(j).getstatus().equals("On")) {
//                    checkOnOrOff.set(position, true);
//                    ivOnOrOff.setChecked(true);
//                } else {
//                    checkOnOrOff.set(position, false);
//                    ivOnOrOff.setChecked(false);
//                }
//            }
//        }
        if(buttonItemCollectionCms.getData().get(position).getChecked()!=null){


        if (buttonItemCollectionCms.getData().get(position).getChecked().equals("true")) {
            checkEdit.set(position, true);
            cbEditScene.setChecked(true);
        } else {
            checkEdit.set(position, false);
            cbEditScene.setChecked(false);
        }
        }
        if (buttonItemCollectionCms.getData().get(position).getstatus().equals("On")) {
            checkOnOrOff.set(position, true);
            ivOnOrOff.setChecked(true);
        } else {
            checkOnOrOff.set(position, false);
            ivOnOrOff.setChecked(false);
        }


//        for(int k=0;k<editScene.getData().size();k++){
//            if(checkEdit.get(k) == true){
//                cbEditScene.setChecked(true);
//            }
//        }


        cbEditScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkEdit.get(position) == true) {
                    checkEdit.set(position, false);
                    buttonItemCollectionCms.getData().get(position).setChecked("false");
                } else {
                    checkEdit.set(position, true);
                    buttonItemCollectionCms.getData().get(position).setChecked("true");

                }

            }
        });


        return convertView;


    }

    public static void setTempAir(int position, String temp) {
        buttonItemCollectionCms.getData().get(position).setValue(temp);
        EditSceneFragment.setTempRemote(buttonItemCollectionCms);

    }
//    public void checkBoxClicked(int positionClicked){
//        if (checkEdit.get(positionClicked) == true) {
//            checkEdit.set(positionClicked, false);
//            cbEditScene.setChecked(false);
//        } else {
//            checkEdit.set(positionClicked, true);
//            cbEditScene.setChecked(true);
//        }
//        Log.d("positionAll", checkEdit.toString());
//
//    }
}
