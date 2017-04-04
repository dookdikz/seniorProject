package com.example.tanawat.eleccontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;


import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.tanawat.eleccontrol.R;

import java.util.Calendar;

/**
 * Created by Tanawat on 10/2/2560.
 */
public class SetTimeDialogFragment extends DialogFragment {
    int mNum;

    public static SetTimeDialogFragment newInstance(int num) {
        SetTimeDialogFragment f = new SetTimeDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum - 1) % 6) {
            case 1:
                style = DialogFragment.STYLE_NO_TITLE;
                break;
            case 2:
                style = DialogFragment.STYLE_NO_FRAME;
                break;
            case 3:
                style = DialogFragment.STYLE_NO_INPUT;
                break;
            case 4:
                style = DialogFragment.STYLE_NORMAL;
                break;
            case 5:
                style = DialogFragment.STYLE_NORMAL;
                break;
            case 6:
                style = DialogFragment.STYLE_NO_TITLE;
                break;
            case 7:
                style = DialogFragment.STYLE_NO_FRAME;
                break;
            case 8:
                style = DialogFragment.STYLE_NORMAL;
                break;
        }
        switch ((mNum - 1) % 6) {
            case 4:
                theme = android.R.style.Theme_Holo;
                break;
            case 5:
                theme = android.R.style.Theme_Holo_Light_Dialog;
                break;
            case 6:
                theme = android.R.style.Theme_Holo_Light;
                break;
            case 7:
                theme = android.R.style.Theme_Holo_Light_Panel;
                break;
            case 8:
                theme = android.R.style.Theme_Holo_Light;
                break;
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View thisDialog = inflater.inflate(R.layout.dialog_set_time, container, false);
        Button btnDateTimeOK = (Button) thisDialog.findViewById(R.id.btnDateTimeOK);

        Calendar calendar = Calendar.getInstance();
        int daySystem = calendar.get(Calendar.DAY_OF_WEEK);
        Button btnDateTimeCancel = (Button) thisDialog.findViewById(R.id.btnDateTimeCancel);
        final Spinner spinSelectedDay = (Spinner) thisDialog.findViewById(R.id.spinSelectedDay);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_day_picker, getResources().getStringArray(R.array.days_of_week));
        spinSelectedDay.setAdapter(adapter);
        spinSelectedDay.setSelection(daySystem - 1);
        final TimePicker tpSelectedTime = (TimePicker) thisDialog.findViewById(R.id.tpSelectedTime);
        tpSelectedTime.setIs24HourView(true);

        btnDateTimeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDialog().dismiss();
            }
        });

        btnDateTimeOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                String strTime = null, strDay = null, strHour = null, strMinute = null, strAM_PM = null;
                int dayOfWeek = 1;
                strDay = ((String) spinSelectedDay.getSelectedItem());

                switch (strDay) {
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

                strHour = tpSelectedTime.getCurrentHour().toString();
                strMinute = tpSelectedTime.getCurrentMinute().toString();
                strTime = strHour + ":" + strMinute;
//                strMinute = tpSelectedTime.getCurrentMinute().toString();
//                if (tpSelectedTime.getCurrentMinute() < 10){
//                    strMinute = "0"+strMinute;
//                }
//                if (tpSelectedTime.getCurrentHour() > 12){
//                    strHour = (tpSelectedTime.getCurrentHour() - 12)+"";
//                    strAM_PM = "PM";
//                }else if(tpSelectedTime.getCurrentHour() == 12){
//                    strHour = tpSelectedTime.getCurrentHour().toString();
//                    strAM_PM = "PM";
//                }else if(tpSelectedTime.getCurrentHour() < 12){
//                    strHour = tpSelectedTime.getCurrentHour().toString();
//                    strAM_PM = "AM";
//                }
//                if (strHour != null && strAM_PM != null){
//                    strTime = strHour + ":" + strMinute + " " + strAM_PM;
//                }


                calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

                calendar.set(Calendar.HOUR_OF_DAY, tpSelectedTime.getCurrentHour());
                calendar.set(Calendar.MINUTE, tpSelectedTime.getCurrentMinute());
                final int _id = 5;

                SetSceneOptionFragment setSceneOptionFragment =new SetSceneOptionFragment();
                setSceneOptionFragment.setTime(strDay,dayOfWeek,tpSelectedTime.getCurrentHour(),tpSelectedTime.getCurrentMinute());
                getDialog().dismiss();

            }
        });


        return thisDialog;
    }
}
