package com.example.tanawat.eleccontrol.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.TestSendWeb;
import com.example.tanawat.eleccontrol.manager.HttpManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowEvent extends Activity implements OnClickListener {

    PowerManager pm;
    WakeLock wl;
    KeyguardManager km;
    KeyguardLock kl;
    Ringtone r;
    ButtonItemCollectionCms buttonItemCollectionCms;
    ButtonItemCollectionCms allTool;
    ButtonItemCms buttonItemCms;
    TextView tvAlarm;
    Button btnStop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        buttonItemCollectionCms = getIntent().getParcelableExtra("sceneAlarm");

        Log.i("ShowEvent", "onCreate() in DismissLock");
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("ShowEvent");
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.ON_AFTER_RELEASE, "ShowEvent");
        wl.acquire(); //wake up the screen
        kl.disableKeyguard();

        setContentView(R.layout.sec);

        tvAlarm = (TextView) findViewById(R.id.tvAlarm);
        tvAlarm.setText(buttonItemCollectionCms.getName() + " is working");
        for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {


            buttonItemCms = buttonItemCollectionCms.getData().get(i);

            if (buttonItemCms.getType().equals("Air")) {
//                    Toast.makeText(getApplicationContext(), "Open", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openAir();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeAir();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else if (buttonItemCms.getType().equals("Tv")) {
//                    Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openTv();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeTv();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (buttonItemCms.getType().equals("Switch1")) {
//                    Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch1();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch1();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (buttonItemCms.getType().equals("Switch2")) {
//                    Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch2();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch2();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (buttonItemCms.getType().equals("Curtain")) {
//                    Toast.makeText(getApplicationContext(), "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openCurtain();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeCurtain();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(getApplicationContext(), "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }


        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String jsonRead = pref.getString("json", null);
        allTool = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
        for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
            String idToolInScene = buttonItemCollectionCms.getData().get(i).getId();
            if (allTool != null) {
                for (int j = 0; j < allTool.getData().size(); j++) {
                    String idTool = allTool.getData().get(j).getId();
                    if (idTool.equals(idToolInScene)) {
                        allTool.getData().get(j).setstatus(buttonItemCollectionCms.getData().get(i).getstatus());
                    }
                }
            }

        }
        String json = new Gson().toJson(allTool);
        editor.putString("json", json);
        editor.apply();
        btnStop = (Button) findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnStop) {

            this.finish();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        wl.acquire();//must call this!
        Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if (notif == null) {
            notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (notif == null) {
                notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
        r = RingtoneManager.getRingtone(getApplicationContext(), notif);
        r.play();


    }

    @Override
    public void onPause() {
        super.onPause();
        wl.release();
        if (r.isPlaying()) {
            r.stop();
        }
    }

}

