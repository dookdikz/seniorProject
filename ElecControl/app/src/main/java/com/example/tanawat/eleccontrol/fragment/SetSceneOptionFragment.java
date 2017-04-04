package com.example.tanawat.eleccontrol.fragment;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import java.util.Calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.activity.AlarmReceiver;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SetSceneOptionFragment extends Fragment {

    public interface FragmentListener {
        void onAddSceneButtonClicked(ButtonItemCollectionCms buttonItemCollectionCms);
    }

    static Button buttonstartSetTime;
    static Button buttonstartCancelTime;
    static Button btnStartSetTemp;
    static Button btnStartCancelTemp;
    static Button btnStartSetLight;
    static Button btnStartCancelLight;
    static Button btnStartSetBluetooth;
    static Button btnStartCancelBluetooth;
    static ButtonItemCollectionCms buttonItemCollectionCms;
    static TextView tvSetTime;
    static TextView tvSetTemp;
    static TextView tvSetLight;
    static TextView tvSetBluetooth;

    static String time;
    static String chooseTime;

    static int temp;
    static String chooseTemp;

    static int light;
    static String chooseLight;

    static String bluetooth;

    static Calendar calendar;
    static int id;

    public SetSceneOptionFragment() {
        super();
    }

    public static SetSceneOptionFragment newInstance(ButtonItemCollectionCms buttonItemCollectionCms) {
        SetSceneOptionFragment fragment = new SetSceneOptionFragment();
        Bundle args = new Bundle();
        args.putParcelable("newScene", buttonItemCollectionCms);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        setHasOptionsMenu(true);

        buttonItemCollectionCms = getArguments().getParcelable("newScene");

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_set_option_scene, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("numId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                id = dataSnapshot.getValue(Integer.class) + 1;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tvSetTime = (TextView) rootView.findViewById(R.id.tvShowSetTime);
        tvSetTemp = (TextView) rootView.findViewById(R.id.tvSetTemp);
        tvSetLight = (TextView) rootView.findViewById(R.id.tvSetLight);
        tvSetBluetooth = (TextView) rootView.findViewById(R.id.tvSetBluetooth);


        buttonstartSetTime = (Button) rootView.findViewById(R.id.startSetTime);
        buttonstartSetTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //openTimePickerDialog(true);
                showTimePickerDialog(buttonItemCollectionCms);
            }
        });
        buttonstartCancelTime = (Button) rootView.findViewById(R.id.btnCancelAlarm);
        buttonstartCancelTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                tvSetTime.setText("No Set");
            }
        });


        btnStartSetTemp = (Button) rootView.findViewById(R.id.btnSetTemp);
        btnStartSetTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTempPickerDialog(buttonItemCollectionCms);
            }
        });
        btnStartCancelTemp  = (Button) rootView.findViewById(R.id.btnCancelTemp);
        btnStartCancelTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSetTemp.setText("No Set");
            }
        });


        btnStartSetLight = (Button) rootView.findViewById(R.id.btnSetLight);
        btnStartSetLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLightPickerDialog(buttonItemCollectionCms);
            }
        });
        btnStartCancelLight  = (Button) rootView.findViewById(R.id.btnCancelLight);
        btnStartCancelLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSetLight.setText("No Set");
            }
        });


        btnStartSetBluetooth = (Button) rootView.findViewById(R.id.btnSetBluetooth);
        btnStartSetBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBluetoothPickerDialog(buttonItemCollectionCms);
            }
        });
        btnStartCancelBluetooth  = (Button) rootView.findViewById(R.id.btnCancelBluetooth);
        btnStartCancelBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSetBluetooth.setText("No Set");
            }
        });
       

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

    public void showTimePickerDialog(ButtonItemCollectionCms buttonItemCollectionCms) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SetTimeDialogFragment.newInstance(4);
        newFragment.show(ft, "dialog");


    }
    public void showTempPickerDialog(ButtonItemCollectionCms buttonItemCollectionCms) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SetTempDiaLogFragment.newInstance(4);
        newFragment.show(ft, "dialog");
    }


    public void showLightPickerDialog(ButtonItemCollectionCms buttonItemCollectionCms) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SetLightDialogFragment.newInstance(4);
        newFragment.show(ft, "dialog");
    }

    public void showBluetoothPickerDialog(ButtonItemCollectionCms buttonItemCollectionCms) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = SetBluetoothDialogFragment.newInstance(4);
        newFragment.show(ft, "dialog");
    }

    public void setTime(String strDay, int day, int hour, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(calendar.DAY_OF_WEEK, day);
        calendar.set(calendar.HOUR_OF_DAY, hour);
        calendar.set(calendar.MINUTE, minute);
        String strHour=String.valueOf(hour);
        String strMinute=String.valueOf(minute);
        if(hour<10){
            strHour = "0"+String.valueOf(hour);
        }
        if(minute<10){
            strMinute = "0"+String.valueOf(minute);
        }

        time = strDay.substring(0,3) + ":" + strHour + ":" + strMinute;
        tvSetTime.setText(time);
    }
    public void setTemp(String choose,int temp){
        this.temp = temp;
        chooseTemp = choose;
        tvSetTemp.setText(choose+" "+temp + " C");
    }
    public void setLight(String choose,int light){
        this.light = light;
        chooseLight = choose;
        tvSetLight.setText(choose+" "+light + " Lux");
    }
    public void setBluetooth(String choose){
        bluetooth = choose;
        tvSetBluetooth.setText(choose);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_scene, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionNext) {

            buttonItemCollectionCms.setNumId(id);





            if (!tvSetTime.getText().equals("No Set")) {
                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                intent.putExtra("sceneAlarm", buttonItemCollectionCms);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                buttonItemCollectionCms.setTime(this.time);
                buttonItemCollectionCms.setCheckTime("On");



            }
            else {
                buttonItemCollectionCms.setTime("No Set");
                buttonItemCollectionCms.setCheckTime("Off");


            }
            if (!tvSetTemp.getText().equals("No Set")) {
                buttonItemCollectionCms.setTemp(String.valueOf(this.temp));
                buttonItemCollectionCms.setCheckTempSen(chooseTemp);




            } else {
                buttonItemCollectionCms.setTemp("No Set");
                buttonItemCollectionCms.setCheckTempSen("Off");

            }

            if (!tvSetLight.getText().equals("No Set")) {
                buttonItemCollectionCms.setLight(String.valueOf(this.light));
                buttonItemCollectionCms.setCheckLightSen(chooseLight);



            } else {
                buttonItemCollectionCms.setLight("No Set");
                buttonItemCollectionCms.setCheckLightSen("Off");

            }

            if (!tvSetBluetooth.getText().equals("No Set")) {
                buttonItemCollectionCms.setBluetooth(this.bluetooth);
                buttonItemCollectionCms.setCheckBluetooth("On");



            } else {
                buttonItemCollectionCms.setBluetooth("No Set");
                buttonItemCollectionCms.setCheckBluetooth("Off");

            }
            FragmentListener listener = (FragmentListener) getActivity();
            listener.onAddSceneButtonClicked(buttonItemCollectionCms);
            final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            mRootRef.child("numId").setValue(id);


        }
        return super.onOptionsItemSelected(item);
    }


}
