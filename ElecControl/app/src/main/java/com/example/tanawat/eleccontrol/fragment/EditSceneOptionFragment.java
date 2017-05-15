package com.example.tanawat.eleccontrol.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.activity.AlarmReceiver;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;

import java.util.Calendar;
import java.util.List;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class EditSceneOptionFragment extends Fragment {
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

    CheckBox cbEditTime;
    CheckBox cbEditTemp;
    CheckBox cbEditLight;
    CheckBox cbEditBluetooth;

    static String time;
    static String chooseTime;

    static int temp;
    static String chooseTemp;

    static int light;
    static String chooseLight;

    static String bluetooth;

    static Calendar calendar;
    static int id;

    String mUser;
    String pathListTool;
    String pathListScene;

    public interface FragmentListener {
        void onEditSceneButtonClicked(ButtonItemCollectionCms buttonItemCollectionCms);
    }

    public EditSceneOptionFragment() {
        super();
    }

    public static EditSceneOptionFragment newInstance(ButtonItemCollectionCms buttonItemCollectionCms,String mUser) {
        EditSceneOptionFragment fragment = new EditSceneOptionFragment();
        Bundle args = new Bundle();
        args.putParcelable("editScene", buttonItemCollectionCms);
        args.putString("mUser",mUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        setHasOptionsMenu(true);
        mUser = getArguments().getString("mUser");
        buttonItemCollectionCms = getArguments().getParcelable("editScene");
        pathListTool = mUser + "/listTool";
        pathListScene = mUser + "/listScene";
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_option_scene, container, false);
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

        cbEditTime = (CheckBox) rootView.findViewById(R.id.cbEditTime);
        cbEditTemp = (CheckBox) rootView.findViewById(R.id.cbEditTemp);
        cbEditLight = (CheckBox) rootView.findViewById(R.id.cbEditLight);
        cbEditBluetooth = (CheckBox) rootView.findViewById(R.id.cbEditBluetooth);

        tvSetTime = (TextView) rootView.findViewById(R.id.tvShowSetTime);
        tvSetTime.setText(buttonItemCollectionCms.getTime().toString());
        tvSetTemp = (TextView) rootView.findViewById(R.id.tvSetTemp);
        tvSetTemp.setText(buttonItemCollectionCms.getTemp());

        if (!buttonItemCollectionCms.getTime().equals("No Set")) {
            cbEditTime.setChecked(true);
            calendar = Calendar.getInstance();

time = buttonItemCollectionCms.getTime();
            String[] splitTime = buttonItemCollectionCms.getTime().split(":");
            if(splitTime[0].equals("Sun")){
                calendar.set(calendar.DAY_OF_WEEK, 1);
            }
            else if(splitTime[0].equals("Mon")){
                calendar.set(calendar.DAY_OF_WEEK, 2);
            }
            else if(splitTime[0].equals("Tue")){
                calendar.set(calendar.DAY_OF_WEEK, 3);
            }
            else if(splitTime[0].equals("Wed")){
                calendar.set(calendar.DAY_OF_WEEK, 4);
            }
            else if(splitTime[0].equals("Thu")){
                calendar.set(calendar.DAY_OF_WEEK, 5);
            }
            else if(splitTime[0].equals("Fri")){
                calendar.set(calendar.DAY_OF_WEEK, 6);
            }
            else if(splitTime[0].equals("Sat")){
                calendar.set(calendar.DAY_OF_WEEK, 7);
            }
            calendar.set(calendar.HOUR_OF_DAY, Integer.parseInt(splitTime[1]));
            calendar.set(calendar.MINUTE, Integer.parseInt(splitTime[2]));
            calendar.set(calendar.SECOND, 0);
            calendar.set(calendar.MILLISECOND, 0);


        }
        if(!buttonItemCollectionCms.getTemp().equals("No Set")){
            tvSetTemp.setText(buttonItemCollectionCms.getCheckTempSen()+" "+buttonItemCollectionCms.getTemp()+" C");
            chooseTemp  = buttonItemCollectionCms.getCheckTempSen();
            cbEditTemp.setChecked(true);
            temp = Integer.parseInt(buttonItemCollectionCms.getTemp());
        }

        tvSetLight = (TextView) rootView.findViewById(R.id.tvSetLight);
        tvSetLight.setText(buttonItemCollectionCms.getLight());
        if(!buttonItemCollectionCms.getLight().equals("No Set")){
            tvSetLight.setText(buttonItemCollectionCms.getCheckLightSen()+" "+buttonItemCollectionCms.getLight()+" Lux");
            chooseLight=buttonItemCollectionCms.getCheckLightSen();
            light = Integer.parseInt(buttonItemCollectionCms.getLight());
            cbEditLight.setChecked(true);
        }
        tvSetBluetooth = (TextView) rootView.findViewById(R.id.tvSetBluetooth);
        tvSetBluetooth.setText(buttonItemCollectionCms.getBluetooth());
        if (!buttonItemCollectionCms.getBluetooth().equals("No Set")) {
            cbEditBluetooth.setChecked(true);
            bluetooth = buttonItemCollectionCms.getBluetooth();
        }


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

        if (!cbEditTime.isChecked()) {
            buttonstartSetTime.setEnabled(false);
            buttonstartCancelTime.setEnabled(false);
            tvSetTime.setEnabled(false);
            tvSetTime.setText("No Set");

            cbEditTemp.setEnabled(true);
            cbEditLight.setEnabled(true);
            cbEditBluetooth.setEnabled(true);
        } else {
            buttonstartSetTime.setEnabled(true);
            buttonstartCancelTime.setEnabled(true);
            tvSetTime.setEnabled(true);

            cbEditTemp.setEnabled(false);
            cbEditTemp.setChecked(false);
            btnStartSetTemp.setEnabled(false);
            btnStartCancelTemp.setEnabled(false);
            tvSetTemp.setEnabled(false);
            tvSetTemp.setText("No Set");

            cbEditLight.setEnabled(false);
            cbEditLight.setChecked(false);
            btnStartSetLight.setEnabled(false);
            btnStartCancelLight.setEnabled(false);
            tvSetLight.setEnabled(false);
            tvSetLight.setText("No Set");

            cbEditBluetooth.setEnabled(false);
            cbEditBluetooth.setChecked(false);
            btnStartSetBluetooth.setEnabled(false);
            btnStartCancelBluetooth.setEnabled(false);
            tvSetBluetooth.setEnabled(false);
            tvSetBluetooth.setText("No Set");
        }
        if (!cbEditTemp.isChecked()) {
            btnStartSetTemp.setEnabled(false);
            btnStartCancelTemp.setEnabled(false);
            tvSetTemp.setEnabled(false);
            tvSetTemp.setText("No Set");
        }
        if (!cbEditLight.isChecked()) {
            btnStartSetLight.setEnabled(false);
            btnStartCancelLight.setEnabled(false);
            tvSetLight.setEnabled(false);
            tvSetLight.setText("No Set");
        }
        if (!cbEditBluetooth.isChecked()) {
            btnStartSetBluetooth.setEnabled(false);
            btnStartCancelBluetooth.setEnabled(false);
            tvSetBluetooth.setEnabled(false);
            tvSetBluetooth.setText("No Set");
        }
        cbEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbEditTime.isChecked()) {
                    buttonstartSetTime.setEnabled(false);
                    buttonstartCancelTime.setEnabled(false);
                    tvSetTime.setText("No Set");
                    tvSetTime.setEnabled(false);

                    cbEditTemp.setEnabled(true);
                    cbEditLight.setEnabled(true);
                    cbEditBluetooth.setEnabled(true);
                } else {
                    buttonstartSetTime.setEnabled(true);
                    buttonstartCancelTime.setEnabled(true);
                    tvSetTime.setEnabled(true);

                    cbEditTemp.setEnabled(false);
                    cbEditTemp.setChecked(false);
                    btnStartSetTemp.setEnabled(false);
                    btnStartCancelTemp.setEnabled(false);
                    tvSetTemp.setEnabled(false);
                    tvSetTemp.setText("No Set");

                    cbEditLight.setEnabled(false);
                    cbEditLight.setChecked(false);
                    btnStartSetLight.setEnabled(false);
                    btnStartCancelLight.setEnabled(false);
                    tvSetLight.setEnabled(false);
                    tvSetLight.setText("No Set");

                    cbEditBluetooth.setEnabled(false);
                    cbEditBluetooth.setChecked(false);
                    btnStartSetBluetooth.setEnabled(false);
                    btnStartCancelBluetooth.setEnabled(false);
                    tvSetBluetooth.setEnabled(false);
                    tvSetBluetooth.setText("No Set");
                }
            }
        });
        cbEditTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbEditTemp.isChecked()) {
                    btnStartSetTemp.setEnabled(false);
                    btnStartCancelTemp.setEnabled(false);
                    tvSetTemp.setEnabled(false);
                    tvSetTemp.setText("No Set");
                } else {
                    btnStartSetTemp.setEnabled(true);
                    btnStartCancelTemp.setEnabled(true);
                    tvSetTemp.setEnabled(true);
                }
            }
        });
        cbEditLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbEditLight.isChecked()) {
                    btnStartSetLight.setEnabled(false);
                    btnStartCancelLight.setEnabled(false);
                    tvSetLight.setEnabled(false);
                    tvSetLight.setText("No Set");
                } else {
                    btnStartSetLight.setEnabled(true);
                    btnStartCancelLight.setEnabled(true);
                    tvSetLight.setEnabled(true);
                }
            }
        });
        cbEditBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cbEditBluetooth.isChecked()) {
                    btnStartSetBluetooth.setEnabled(false);
                    btnStartCancelBluetooth.setEnabled(false);
                    tvSetBluetooth.setEnabled(false);
                    tvSetBluetooth.setText("No Set");
                } else {
                    btnStartSetBluetooth.setEnabled(true);
                    btnStartCancelBluetooth.setEnabled(true);
                    tvSetBluetooth.setEnabled(true);
                }
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_submit_tool, menu);
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
        DialogFragment newFragment = EditTimeDialogFragment.newInstance(4);
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
        DialogFragment newFragment = EditTempDiaLogFragment.newInstance(4);
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
        DialogFragment newFragment = EditLightDialogFragment.newInstance(4);
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
        DialogFragment newFragment = EditBluetoothDialogFragment.newInstance(4);
        newFragment.show(ft, "dialog");
    }

    public void setTime(String strDay, int day, int hour, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(calendar.DAY_OF_WEEK, day);
        calendar.set(calendar.HOUR_OF_DAY, hour);
        calendar.set(calendar.MINUTE, minute);
        calendar.set(calendar.SECOND, 0);
        calendar.set(calendar.MILLISECOND, 0);
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
        tvSetLight.setText(choose+" "+this.light + " Lux");
    }
    public void setBluetooth(String choose){
        this.bluetooth = choose;
        tvSetBluetooth.setText(choose);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionSubmit) {
            id = buttonItemCollectionCms.getNumId();
            if (!tvSetTime.getText().equals("No Set")) {

//                for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
//                    Log.d("getT", buttonItemCollectionCms.getData().get(i).getstatus());
//                }
                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                intent.putExtra("id",id);
                intent.putExtra("sceneAlarm", buttonItemCollectionCms);
                intent.putExtra("mUser",mUser);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                buttonItemCollectionCms.setTime(this.time);
                buttonItemCollectionCms.setCheckTime("On");
            }
            else {
                Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), id, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(getContext().ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
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
            listener.onEditSceneButtonClicked(buttonItemCollectionCms);
        }
        return super.onOptionsItemSelected(item);
    }

}
