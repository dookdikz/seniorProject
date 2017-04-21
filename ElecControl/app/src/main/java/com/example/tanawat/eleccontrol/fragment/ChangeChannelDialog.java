package com.example.tanawat.eleccontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ChangeChannelDialog extends DialogFragment {
    int mNum;
    String choose;
    Button btnC1;
    Button btnC2;
    Button btnC3;
    Button btnC4;
    Button btnC5;
    Button btnC6;
    Button btnC7;
    Button btnC8;
    Button btnC9;
    Button btnC0;

    static Call<TestSendWeb> call;
    static String url = "http://158.108.122.70:5000/";

    public static ChangeChannelDialog newInstance(int num) {
        ChangeChannelDialog f = new ChangeChannelDialog();

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
        View thisDialog = inflater.inflate(R.layout.dialog_channel, container, false);

       //TODO:
        HttpManager.setUrl(url);
        btnC1 = (Button) thisDialog.findViewById(R.id.btnTv1);
        btnC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "1";
                call = HttpManager.getInstance().getService().tv1();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC2 = (Button) thisDialog.findViewById(R.id.btnTv2);
        btnC2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "2";
                call = HttpManager.getInstance().getService().tv2();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC3 = (Button) thisDialog.findViewById(R.id.btnTv3);
        btnC3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "3";
                call = HttpManager.getInstance().getService().tv3();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC4 = (Button) thisDialog.findViewById(R.id.btnTv4);
        btnC4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "4";
                call = HttpManager.getInstance().getService().tv4();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC5 = (Button) thisDialog.findViewById(R.id.btnTv5);
        btnC5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "5";
                call = HttpManager.getInstance().getService().tv5();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC6 = (Button) thisDialog.findViewById(R.id.btnTv6);
        btnC6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "6";
                call = HttpManager.getInstance().getService().tv6();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC7 = (Button) thisDialog.findViewById(R.id.btnTv7);
        btnC7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "7";
                call = HttpManager.getInstance().getService().tv7();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC8 = (Button) thisDialog.findViewById(R.id.btnTv8);
        btnC8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "8";
                call = HttpManager.getInstance().getService().tv8();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC9 = (Button) thisDialog.findViewById(R.id.btnTv9);
        btnC9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "9";
                call = HttpManager.getInstance().getService().tv9();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }

        });
        btnC0 = (Button) thisDialog.findViewById(R.id.btnTv0);
        btnC0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose = "0";
                call = HttpManager.getInstance().getService().tv0();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
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

    class SentToServer implements Callback<TestSendWeb> {
        public static final int MODE_OPEN_AIR = 1;
        public static final int MODE_CLOSE_AIR = 2;
        public static final int MODE_OPEN_TV = 3;
        public static final int MODE_CLOSE_TV = 4;
        public static final int MODE_OPEN_SWITCH1 = 5;
        public static final int MODE_CLOSE_SWITCH1 = 6;
        public static final int MODE_OPEN_SWITCH2 = 7;
        public static final int MODE_CLOSE_SWITCH2 = 8;
        public static final int MODE_OPEN_CURTAIN = 9;
        public static final int MODE_CLOSE_CURTAIN = 10;
        int mode;

        public SentToServer(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
            Toast.makeText(getContext(),choose,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<TestSendWeb> call, Throwable t) {
            Toast.makeText(getContext(),choose,Toast.LENGTH_SHORT).show();
        }
    }
}
