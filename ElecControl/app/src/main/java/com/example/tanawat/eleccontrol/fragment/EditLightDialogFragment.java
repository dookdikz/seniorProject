package com.example.tanawat.eleccontrol.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;

/**
 * Created by dookdikz on 26/3/2560.
 */

public class EditLightDialogFragment extends DialogFragment {
    int mNum;
String choose;
    public static EditLightDialogFragment newInstance(int num) {
        EditLightDialogFragment f = new EditLightDialogFragment();

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
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View thisDialog = inflater.inflate(R.layout.dialog_set_light, container, false);
        getDialog().setTitle("Set Brightness");
        Button btnLightOK = (Button) thisDialog.findViewById(R.id.btnLightOK);
        Button btnLightCancel = (Button) thisDialog.findViewById(R.id.btnLightDialogCancel);
        final EditText etSetLight = (EditText) thisDialog.findViewById(R.id.etSetLight);
        etSetLight.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
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
              EditSceneOptionFragment editSceneOptionFragment = new EditSceneOptionFragment();
                if(etSetLight.getText().toString().matches("")){
                    Toast.makeText(getContext(), "Please input data", Toast.LENGTH_SHORT).show();
                }
                else {
                    editSceneOptionFragment.setLight(choose,Integer.parseInt(etSetLight.getText().toString()));
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etSetLight.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    getDialog().dismiss();
                }


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
