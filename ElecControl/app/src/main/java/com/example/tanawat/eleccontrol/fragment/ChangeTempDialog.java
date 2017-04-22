package com.example.tanawat.eleccontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.TestSendWeb;
import com.example.tanawat.eleccontrol.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dookdikz on 26/3/2560.
 */

public class ChangeTempDialog extends DialogFragment {
    int mNum;
    String choose;
    Button btnChangeTemp;
    EditText etChangeTemp;
    static Call<TestSendWeb> call;
    static String url = "http://158.108.122.70:5000/";

    public static ChangeTempDialog newInstance(int num) {
        ChangeTempDialog f = new ChangeTempDialog();

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
        View thisDialog = inflater.inflate(R.layout.dialog_remote, container, false);
        getDialog().setTitle("Set Air Temperature");
        etChangeTemp = (EditText) thisDialog.findViewById(R.id.etChangeTemp);
        btnChangeTemp = (Button) thisDialog.findViewById(R.id.btnChangeTemp);

        //TODO:
//        HttpManager.setUrl(url);
        btnChangeTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                HttpManager.setUrl(url);
                if (etChangeTemp.equals("18")) {

                    call = HttpManager.getInstance().getService().temp18();
                } else if (etChangeTemp.getText().toString().equals("19")) {
                    call = HttpManager.getInstance().getService().temp19();
                } else if (etChangeTemp.getText().toString().equals("20")) {
                    call = HttpManager.getInstance().getService().temp20();
                } else if (etChangeTemp.getText().toString().equals("21")) {
                    call = HttpManager.getInstance().getService().temp21();
                } else if (etChangeTemp.getText().toString().equals("22")) {
                    call = HttpManager.getInstance().getService().temp22();
                } else if (etChangeTemp.getText().toString().equals("23")) {
                    call = HttpManager.getInstance().getService().temp23();
                } else if (etChangeTemp.getText().toString().equals("24")) {
                    call = HttpManager.getInstance().getService().temp24();
                } else if (etChangeTemp.getText().toString().equals("25")) {
                    Log.d("testChange", "eiei");
                    call = HttpManager.getInstance().getService().temp25();
                } else if (etChangeTemp.getText().toString().equals("26")) {
                    call = HttpManager.getInstance().getService().temp26();
                } else if (etChangeTemp.getText().toString().equals("27")) {
                    call = HttpManager.getInstance().getService().temp27();
                } else if (etChangeTemp.getText().toString().equals("28")) {
                    call = HttpManager.getInstance().getService().temp28();
                } else {
                    call = null;
                }

                Log.d("testChange", etChangeTemp.getText().toString());
                if (call != null) {
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getContext(),"pass "+ etChangeTemp.getText().toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getContext(), "fail "+etChangeTemp.getText().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
//        Button btnDateTimeCancel = (Button) thisDialog.findViewById(R.id.btnDateTimeCancel);


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


        return thisDialog;
    }
}
