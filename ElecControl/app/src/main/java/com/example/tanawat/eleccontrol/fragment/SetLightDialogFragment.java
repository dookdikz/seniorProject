package com.example.tanawat.eleccontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tanawat.eleccontrol.R;

import java.util.Calendar;

/**
 * Created by dookdikz on 26/3/2560.
 */

public class SetLightDialogFragment extends DialogFragment {
    int mNum;
String choose;
    public static SetLightDialogFragment newInstance(int num) {
        SetLightDialogFragment f = new SetLightDialogFragment();

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
        View thisDialog = inflater.inflate(R.layout.dialog_set_light, container, false);
        Button btnLightOK = (Button) thisDialog.findViewById(R.id.btnLightOK);
        Button btnLightCancel = (Button) thisDialog.findViewById(R.id.btnLightDialogCancel);
        final EditText etSetLight = (EditText) thisDialog.findViewById(R.id.etSetLight);
//        Button btnDateTimeCancel = (Button) thisDialog.findViewById(R.id.btnDateTimeCancel);
        final Spinner spinSelectedLight = (Spinner) thisDialog.findViewById(R.id.spinSelectedLight);
        final String[] chooseLight = getResources().getStringArray(R.array.choose_light_option);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_day_picker, chooseLight);
        spinSelectedLight.setAdapter(adapter);
        spinSelectedLight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose = chooseLight[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        final TimePicker tpSelectedTime = (TimePicker) thisDialog.findViewById(R.id.tpSelectedTime);
//        tpSelectedTime.setIs24HourView(true);
//
//        btnDateTimeCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                getDialog().dismiss();
//            }
//        });

        btnLightOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              SetSceneOptionFragment setSceneOptionFragment = new SetSceneOptionFragment();
                setSceneOptionFragment.setLight(choose,Integer.parseInt(etSetLight.getText().toString()));
                getDialog().dismiss();

            }
        });
        btnLightCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return thisDialog;
    }
}
