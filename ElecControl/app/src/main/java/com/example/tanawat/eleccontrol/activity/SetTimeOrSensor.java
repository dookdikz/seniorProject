package com.example.tanawat.eleccontrol.activity;

import android.app.AlarmManager;
import android.app.Dialog;

import android.app.DialogFragment;
import android.app.Fragment;

import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.fragment.DateDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Tanawat on 8/2/2560.
 */
public class SetTimeOrSensor extends AppCompatActivity {

    TimePicker myTimePicker;
    Button buttonstartSetDialog;
    private ListView listAlarm;
    public static ArrayList<String> listValue;

    TimePickerDialog timePickerDialog;

    final static int RQS_1 = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_time_scene);



        buttonstartSetDialog = (Button)findViewById(R.id.startSetDialog);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //openTimePickerDialog(true);
                showTimePickerDialog(buttonstartSetDialog);
            }});

    }

    public void showTimePickerDialog(View v) {
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

    class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        int callCount = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            if(callCount==0){
                // Do something with the time chosen by the user
                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);

                setAlarm(cal);
            }
            callCount++;
        }
    }

    public void setAlarm(Calendar targetCal){


    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
