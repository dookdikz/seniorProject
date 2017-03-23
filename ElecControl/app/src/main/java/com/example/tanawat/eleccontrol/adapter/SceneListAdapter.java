package com.example.tanawat.eleccontrol.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.activity.AlarmReceiver;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.example.tanawat.eleccontrol.fragment.SceneFragment;
import com.example.tanawat.eleccontrol.view.ButtonListItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Calendar;

/**
 * Created by Tanawat on 31/1/2560.
 */
public class SceneListAdapter extends BaseAdapter {
    ListScene listScene;
    Button btnDelete;
    ImageView ivTimeSet;
    private Context activity;


    public SceneListAdapter(ListScene listScene, Context context) {
        this.listScene = listScene;
        this.activity = context;
    }

    public ListScene getListScene() {
        return listScene;
    }

    public void setListScene(ListScene listScene) {
        this.listScene = listScene;
    }

    @Override
    public int getCount() {
        if (listScene != null) {
            return listScene.getData().size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listScene.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvNameScene;
        TextView tvTimerScene;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder = null;
        ButtonListItem item;
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (convertView != null) {
//            item = (ButtonListItem) convertView;
//
//        } else {


//        spinAddCommand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                typeOfAddComand = typeCommand[position];
//                Toast.makeText(getContext(),typeOfAddComand,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_scene, null);
            holder = new ViewHolder();
            holder.tvNameScene = (TextView) convertView.findViewById(R.id.tvNameScene);
            holder.tvTimerScene = (TextView) convertView.findViewById(R.id.tvTimerScene);


            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }

        mRootRef.child("listScene").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listScene = dataSnapshot.getValue(ListScene.class);
                setListScene(listScene);
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
                adb.setMessage("Are you sure you want to delete " + listScene.getData().get(position).getName());

                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("listScene", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = pref.edit();
//
//                        String jsonRead = pref.getString("json", null);
//                        listScene = new Gson().fromJson(jsonRead, ListScene.class);


                        listScene.deleteData(positionToRemove);
                        mRootRef.child("listScene").setValue(listScene);

//                        String json = new Gson().toJson(listScene);
//                        editor.putString("json", json);
//                        editor.apply();

                        SceneFragment sceneFragment = new SceneFragment();
                        sceneFragment.update();
                        notifyDataSetChanged();

                    }
                });
                adb.show();

            }
        });


        ivTimeSet = (ImageView) convertView.findViewById(R.id.ivTimeSet);

        final ButtonItemCollectionCms buttonItemCollectionCms = (ButtonItemCollectionCms) getItem(position);
        if (buttonItemCollectionCms.getCheckTime().equals("Off")) {
            ivTimeSet.setImageResource(R.drawable.alarm_off);
        } else {
            ivTimeSet.setImageResource(R.drawable.alarm);
        }
        if (buttonItemCollectionCms != null) {
            Log.d("testD", String.valueOf(position));
            if (buttonItemCollectionCms.getName() != null) {
                if (holder != null) {
                    holder.tvNameScene.setText(buttonItemCollectionCms.getName());
                    holder.tvTimerScene.setText(buttonItemCollectionCms.getTime());


                }

            } else {

            }
            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());

        }
        ivTimeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Intent intent = new Intent(parent.getContext(), AlarmReceiver.class);
                intent.putExtra("sceneAlarm", buttonItemCollectionCms);

                int dayOfWeek = 0;
                if (!buttonItemCollectionCms.getTime().equals("None")) {
                    String[] date = buttonItemCollectionCms.getTime().split(":");
                    switch (date[0]) {
                        case "Sunday":
                            dayOfWeek = 1;
                            break;
                        case "Monday":
                            dayOfWeek = 2;
                            break;
                        case "Tuesday":
                            dayOfWeek = 3;
                            break;
                        case "Wednesday":
                            dayOfWeek = 4;
                            break;
                        case "Thursday":
                            dayOfWeek = 5;
                            break;
                        case "Friday":
                            dayOfWeek = 6;
                            break;
                        case "Saturday":
                            dayOfWeek = 7;
                            break;

                    }
                    calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(date[1]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(date[2]));
                    if (buttonItemCollectionCms.getCheckTime().equals("On")) {
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(), buttonItemCollectionCms.getNumId(), intent, 0);
                        pendingIntent.cancel();
                        buttonItemCollectionCms.setCheckTime("Off");
                    } else {
                        buttonItemCollectionCms.setCheckTime("On");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(parent.getContext(), buttonItemCollectionCms.getNumId(), intent, 0);
                        AlarmManager alarmManager = (AlarmManager) parent.getContext().getSystemService(parent.getContext().ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }
                } else {
                    if (buttonItemCollectionCms.getCheckTime().equals("On")) {
                        buttonItemCollectionCms.setCheckTime("Off");
                        Log.d("timeset", buttonItemCollectionCms.getTime());
                    } else {
                        buttonItemCollectionCms.setCheckTime("On");
                        Log.d("timeset", buttonItemCollectionCms.getTime());
                    }
                }

                for (int i = 0; i < listScene.getData().size(); i++) {
                    if (buttonItemCollectionCms.getId().equals(listScene.getData().get(i).getId())) {
                        listScene.getData().get(i).setCheckTime(buttonItemCollectionCms.getCheckTime());
                    }
                }
                mRootRef.child("listScene").setValue(listScene);

            }

        });

        return convertView;
    }
}
