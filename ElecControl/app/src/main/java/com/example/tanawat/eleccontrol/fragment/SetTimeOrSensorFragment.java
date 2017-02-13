package com.example.tanawat.eleccontrol.fragment;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Build;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.activity.AlarmReceiver;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.google.gson.Gson;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SetTimeOrSensorFragment extends Fragment {

    public interface FragmentListener{
        void onAddSceneButtonClicked(ButtonItemCollectionCms buttonItemCollectionCms);
    }
    static Button buttonstartSetTime;
    static Button buttonstartCancelTime;
    ButtonItemCollectionCms buttonItemCollectionCms;
    static TextView tvSetTime;
    static String time = "None";
    static Calendar calendar;
    public SetTimeOrSensorFragment() {
        super();
    }

    public static SetTimeOrSensorFragment newInstance(ButtonItemCollectionCms buttonItemCollectionCms) {
        SetTimeOrSensorFragment fragment = new SetTimeOrSensorFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_set_time_scene, container, false);
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
        buttonstartSetTime = (Button) rootView.findViewById(R.id.startSetTime);
        tvSetTime = (TextView) rootView.findViewById(R.id.tvShowSetTime);
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
                calendar.clear();
                tvSetTime.setText("None");
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
        DialogFragment newFragment = DateDialogFragment.newInstance(2);
        newFragment.show(ft, "dialog");


    }

    public void setTime(String strDay, int day, int hour, int minute) {
calendar = Calendar.getInstance();
        calendar.set(calendar.DAY_OF_WEEK, day);
        calendar.set(calendar.HOUR_OF_DAY, hour);
        calendar.set(calendar.MINUTE, minute);
        time = strDay + ":" + String.valueOf(hour) + ":" + String.valueOf(minute);

        tvSetTime.setText(time);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_scene, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.actionNext){
            if(calendar!=null){
                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                intent.putExtra("sceneAlarm",buttonItemCollectionCms);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), buttonItemCollectionCms.getId(), intent, 0);
                AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(getContext().ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                buttonItemCollectionCms.setTime(time);
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onAddSceneButtonClicked(buttonItemCollectionCms);

            }
            else{
                buttonItemCollectionCms.setTime(time);
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onAddSceneButtonClicked(buttonItemCollectionCms);
            }


        }
        return super.onOptionsItemSelected(item);
    }

}
