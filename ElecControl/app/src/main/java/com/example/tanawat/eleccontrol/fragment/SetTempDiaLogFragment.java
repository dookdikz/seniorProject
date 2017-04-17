package com.example.tanawat.eleccontrol.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;

import java.util.Calendar;

/**
 * Created by dookdikz on 26/3/2560.
 */

public class SetTempDiaLogFragment extends DialogFragment {
    int mNum;
    EditText etSetTemp;
    static String choose="less than";

    public static SetTempDiaLogFragment newInstance(int num) {
        SetTempDiaLogFragment f = new SetTempDiaLogFragment();

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
        View thisDialog = inflater.inflate(R.layout.dialog_set_temp, container, false);
        Button btnTempOK = (Button) thisDialog.findViewById(R.id.btnTempDialogOK);
        Button btnTempCancel = (Button) thisDialog.findViewById(R.id.btnTempDialogCancel);
        etSetTemp = (EditText) thisDialog.findViewById(R.id.etSetTemp);
        etSetTemp.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
//        Button btnTempCancel = (Button) thisDialog.findViewById(R.id.btnTempCancel);
        final Spinner spinSelectedTemp = (Spinner) thisDialog.findViewById(R.id.spinSelectedTemp);
        final String[] chooseTemp = getResources().getStringArray(R.array.choose_temp_option);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_day_picker, chooseTemp);

        spinSelectedTemp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose = chooseTemp[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinSelectedTemp.setAdapter(adapter);

//        final TimePicker tpSelectedTime = (TimePicker) thisDialog.findViewById(R.id.tpSelectedTime);
//        tpSelectedTime.setIs24HourView(true);
//
//        btnTempCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                getDialog().dismiss();
//            }
//        });

        btnTempOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SetSceneOptionFragment setSceneOptionFragment = new SetSceneOptionFragment();
                if(etSetTemp.getText().toString().matches("")){
                    Toast.makeText(getContext(), "Please input data", Toast.LENGTH_SHORT).show();
                }
                else {
                    setSceneOptionFragment.setTemp(choose, Integer.parseInt(etSetTemp.getText().toString()));
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etSetTemp.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    getDialog().dismiss();
                }

            }
        });
        btnTempCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return thisDialog;
    }
}
