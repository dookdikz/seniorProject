package com.example.tanawat.eleccontrol.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.example.tanawat.eleccontrol.view.ButtonListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Tanawat on 7/11/2559.
 */
public class ToolListAdapter extends BaseAdapter {
    ButtonItemCollectionCms buttonItemCollectionCms;
    Button btnDelete;
    ListScene listScene;
    String mUser;
    String pathListTool;
    String pathListScene;


    private Context activity;

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    public ToolListAdapter(ButtonItemCollectionCms buttonItemCollectionCms, Context activity, String mUser) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;
        this.activity = activity;
        this.mUser = mUser;
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
        ImageView ivOnOff;
        ImageView ivTabTool;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ButtonListItem item;
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (convertView != null) {
//            item = (ButtonListItem) convertView;
//
//        } else {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_button, null);
            holder = new ViewHolder();
            holder.tvNameCommand = (TextView) convertView.findViewById(R.id.tvNameCommand);
            holder.tvNameType = (TextView) convertView.findViewById(R.id.tvTypeCommand);
            holder.ivOnOff = (ImageView) convertView.findViewById(R.id.ivOnOff);
            holder.ivTabTool = (ImageView) convertView.findViewById(R.id.ivTabTool);
            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }

        pathListTool = mUser+"/listTool";
        mRootRef.child(pathListTool).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                buttonItemCollectionCms = dataSnapshot.getValue(ButtonItemCollectionCms.class);
                setButtonItemCollectionCms(buttonItemCollectionCms);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        pathListScene = mUser+"/listScene";
        mRootRef.child(pathListScene).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listScene = dataSnapshot.getValue(ListScene.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        btnDelete = (Button) convertView.findViewById(R.id.btnDeleted);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + buttonItemCollectionCms.getData().get(position).getName());

                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();

//                        String jsonRead = pref.getString("json", null);

//                        buttonItemCollectionCms = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
                        if (listScene != null) {
                            for (int i = 0; i < listScene.getData().size(); i++) {
                                ButtonItemCollectionCms tempScene = listScene.getData().get(i);
                                for (int j = 0; j < tempScene.getData().size(); j++) {
                                    Log.d("deleteScene", buttonItemCollectionCms.getData().get(positionToRemove).getId() + " + " + tempScene.getId());
                                    if (buttonItemCollectionCms.getData().get(positionToRemove).getId().equals(tempScene.getData().get(j).getId())) {
                                        listScene.getData().get(i).deleteData(j);
                                    }
                                }
                            }
                            mRootRef.child(pathListScene).setValue(listScene);
                        }


                        buttonItemCollectionCms.deleteData(positionToRemove);
                        mRootRef.child(pathListTool).setValue(buttonItemCollectionCms);

//                        setButtonItemCollectionCms(buttonItemCollectionCms);
//                        String json = new Gson().toJson(buttonItemCollectionCms);
//                        editor.putString("json", json);
//                        editor.apply();

                        MainFragment mainFragment = new MainFragment();
                        mainFragment.update();
                        notifyDataSetChanged();

                    }
                });
                adb.show();

            }
        });


        ButtonItemCms buttonItemCms = (ButtonItemCms) getItem(position);
        if (buttonItemCms != null) {
            Log.d("testD", String.valueOf(position));
            if (buttonItemCms.getName() != null) {
                if (holder != null) {
                    holder.tvNameCommand.setText(buttonItemCms.getName());
                    holder.tvNameType.setText(buttonItemCms.getType());
                    Log.d("testStatus", buttonItemCollectionCms.getData().get(position).getstatus().toString());
                    if (buttonItemCollectionCms.getData().get(position).getstatus().equals("Off")) {
                        holder.ivOnOff.setImageResource(R.drawable.switch_off);
                    } else {
                        holder.ivOnOff.setImageResource(R.drawable.switch_on);
                    }

                    if (buttonItemCollectionCms.getData().get(position).getType().equals("Air") || buttonItemCollectionCms.getData().get(position).getType().equals("Tv")) {
                        holder.ivTabTool.setImageResource(R.drawable.remote_icon);
                    } else if (buttonItemCollectionCms.getData().get(position).getType().equals("Switch1") || buttonItemCollectionCms.getData().get(position).getType().equals("Switch2")) {
                        holder.ivTabTool.setImageResource(R.drawable.switch_icon);
                    } else {
                        holder.ivTabTool.setImageResource(R.drawable.curtain_icon);
                    }

                }

            } else {
                Log.d("testD", "name_null");
            }
            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());

        }

        return convertView;
    }


}
