package com.example.tanawat.eleccontrol.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.example.tanawat.eleccontrol.cms.TestSendWeb;
import com.example.tanawat.eleccontrol.manager.HttpManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dookdikz on 4/3/2560.
 */

public class MyService extends Service {
    ButtonItemCollectionCms buttonItemCollectionCms;
    ListScene listScene;
    Long temp;
    Long light;
    String macBlue;
    Long statusBlue;
    String pathListTool;
    String pathListScene;
    String mUser;
    static Call<TestSendWeb> call;
    final DatabaseReference mRootRef  = FirebaseDatabase.getInstance().getReference();
    NotificationCompat notification;
    int numNotifi = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mUser = intent.getStringExtra("mUser");
        pathListScene = mUser+"/listScene";
        pathListTool = mUser+"/listTool";
        buttonItemCollectionCms = new ButtonItemCollectionCms();

        listScene = new ListScene();
        final String macAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");

        mRootRef.child(pathListTool).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    buttonItemCollectionCms = dataSnapshot.getValue(ButtonItemCollectionCms.class);
                    onBackground(macAddress, mRootRef);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(pathListScene).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    listScene = dataSnapshot.getValue(ListScene.class);
                    onBackground(macAddress, mRootRef);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("sensor/light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    light = dataSnapshot.getValue(Long.class);
                    if(light!=null){
                        light = ((1023-light)*50/light);
                    }
                    onBackground(macAddress, mRootRef);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("sensor/temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {

                    temp = dataSnapshot.getValue(Long.class);
                    onBackground(macAddress, mRootRef);
//                    if (listScene != null)
//                    {
//                        for (int i = 0; i < listScene.getData().size(); i++) {
//                            if (!listScene.getData().get(i).getCheckTempSen().equals("No Set")) {
//                                if (listScene.getData().get(i).getCheckTempSen().equals("more than")) {
//                                    if (temp >= Long.parseLong(listScene.getData().get(i).getTemp())) {
//                                        for (int j = 0; j < listScene.getData().get(i).getData().size(); j++) {
//                                            onCheck(listScene.getData().get(i).getData().get(j).getType(), i, j, mRootRef);
//                                        }
//                                    }
//                                } else if (listScene.getData().get(i).getCheckTempSen().equals("less than")) {
//                                    if (temp < Long.parseLong(listScene.getData().get(i).getTemp())) {
//                                        for (int j = 0; j < listScene.getData().get(i).getData().size(); j++) {
//                                            onCheck(listScene.getData().get(i).getData().get(j).getType(), i, j, mRootRef);
//                                        }
//                                    }
//                                }
//
//
//                            }
//
//                        }
//                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("MacBlue/data").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    macBlue = dataSnapshot.getValue(String.class);
                    onBackground(macAddress, mRootRef);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("MacBlue/status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {

                    statusBlue = dataSnapshot.getValue(Long.class);
                    onBackground(macAddress, mRootRef);
//                    if (listScene != null)
//                    {
//                        if (macBlue.equals(macAddress)) {
//                            for (int i = 0; i < listScene.getData().size(); i++) {
//                                if (!listScene.getData().get(i).getCheckBluetooth().equals("Off")) {
//                                    if (listScene.getData().get(i).getBluetooth().equals("On")) {
//                                        if (statusBlue==1) {
//                                            for (int j = 0; j < listScene.getData().get(i).getData().size(); j++) {
//                                                onCheck(listScene.getData().get(i).getData().get(j).getType(), i, j, mRootRef);
//                                            }
//                                        }
//                                    } else if (listScene.getData().get(i).getBluetooth().equals("Off")) {
//                                        if (statusBlue==0) {
//                                            for (int j = 0; j < listScene.getData().get(i).getData().size(); j++) {
//                                                onCheck(listScene.getData().get(i).getData().get(j).getType(), i, j, mRootRef);
//                                            }
//                                        }
//                                    }
//
//
//                                }
//
//                            }
//                        }
//
//                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



            }
        }, 3000);
        return super.onStartCommand(intent, flags, startId);
    }

    private void onBackground(String macAddress, DatabaseReference mRootRef) {
        if (listScene != null &&statusBlue!=null &&light!=null &&buttonItemCollectionCms!=null)
        {
            for (int i = 0; i < listScene.getData().size(); i++)
            {
                int count = 0;
                int checkCount = 0;
                if (!listScene.getData().get(i).getCheckTempSen().equals("Off")) {
                    count += 1;
                }
                if (!listScene.getData().get(i).getCheckLightSen().equals("Off")) {
                    count += 1;
                }
                if (!listScene.getData().get(i).getCheckBluetooth().equals("Off")) {
                    count += 1;
                }


                if (!listScene.getData().get(i).getCheckLightSen().equals("No Set")) {
                    if (listScene.getData().get(i).getCheckLightSen().equals("more than")) {
//                                    Log.d("testBack1", String.valueOf(light));
//                                    Log.d("testBack2", listScene.getData().get(i).getLight());
                        if (light >= Long.parseLong(listScene.getData().get(i).getLight())) {
                            checkCount += 1;

                        }
                    } else if (listScene.getData().get(i).getCheckLightSen().equals("less than or equal")) {
//                                    Log.d("testBack1", String.valueOf(light));
//                                    Log.d("testBack2", listScene.getData().get(i).getLight());
                        if (light <= Long.parseLong(listScene.getData().get(i).getLight())) {
                            checkCount += 1;

                        }
                    }


                }

                if (!listScene.getData().get(i).getCheckTempSen().equals("No Set")) {

                    if (listScene.getData().get(i).getCheckTempSen().equals("more than")) {
                        if (temp >= Long.parseLong(listScene.getData().get(i).getTemp())) {
                            checkCount += 1;

                        }
                    } else if (listScene.getData().get(i).getCheckTempSen().equals("less than or equal")) {
                        if (temp <= Long.parseLong(listScene.getData().get(i).getTemp())) {
                            checkCount += 1;

                        }
                    }


                }
                if (macBlue.equals(macAddress))
                {
                    if (!listScene.getData().get(i).getCheckBluetooth().equals("Off")) {
                        if (listScene.getData().get(i).getBluetooth().equals("On")) {
                            if (statusBlue == 1) {
                                checkCount += 1;

                            }
                        } else if (listScene.getData().get(i).getBluetooth().equals("Off")) {
                            if (statusBlue == 0) {
                                checkCount += 1;
                            }
                        }


                    }
                }

                if(count!=0 &&checkCount!=0){
                    if(count==checkCount){
                        for (int j = 0; j < listScene.getData().get(i).getData().size(); j++) {
                            onCheck(listScene.getData().get(i).getData().get(j).getType(), i, j, mRootRef);
                        }
                    }
                }
                Log.d("temp",String.valueOf(count));
                Log.d("tempC",String.valueOf(checkCount));
            }

        }
    }

    private void onCheck(String type, int i, int j, DatabaseReference mRootRef) {
        if (listScene.getData().get(i).getData().get(j).getstatus().equals("On")) {
            for (int k = 0; k < buttonItemCollectionCms.getData().size(); k++) {
//                Log.d("testBack3", buttonItemCollectionCms.getData().get(k).getId());
//                Log.d("testBack4", listScene.getData().get(i).getData().get(j).getId());
                if (buttonItemCollectionCms.getData().get(k).getId().equals(listScene.getData().get(i).getData().get(j).getId())) {
                    if (buttonItemCollectionCms.getData().get(k).getstatus().equals("Off")) {
                        buttonItemCollectionCms.getData().get(k).setstatus("On");
                        if (type.equals("Air")) {
                            call = HttpManager.getInstance().getService().openAir();
                        } else if (type.equals("Tv")) {
                            call = HttpManager.getInstance().getService().openTv();
                        } else if (type.equals("Switch1")) {
                            call = HttpManager.getInstance().getService().openSwitch1();
                        } else if (type.equals("Switch2")) {
                            call = HttpManager.getInstance().getService().openSwitch2();
                        } else {
                            call = HttpManager.getInstance().getService().openCurtain();
                        }
                        call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR,listScene.getData().get(i)));
                        if (type.equals("Air")) {
                            if(listScene.getData().get(i).getData().get(j).getValue()!=null){
                                sentTempAir(i, j);
//                                call = HttpManager.getInstance().getService().openAir();
                            }

                        }
                        mRootRef.child(pathListTool).setValue(buttonItemCollectionCms);
                    }
                    else{
                        if (type.equals("Air")) {
                            if(listScene.getData().get(i).getData().get(j).getValue()!=null){
                                sentTempAir(i, j);
//                                call = HttpManager.getInstance().getService().openAir();
                            }

                        }
                    }
                }
            }
        } else if (listScene.getData().get(i).getData().get(j).getstatus().equals("Off")) {
            for (int k = 0; k < buttonItemCollectionCms.getData().size(); k++) {
                if (buttonItemCollectionCms.getData().get(k).getId().equals(listScene.getData().get(i).getData().get(j).getId())) {
                    if (buttonItemCollectionCms.getData().get(k).getstatus().equals("On")) {
                        buttonItemCollectionCms.getData().get(k).setstatus("Off");
                        if (type.equals("Air")) {
                            call = HttpManager.getInstance().getService().closeAir();
                        } else if (type.equals("Tv")) {
                            call = HttpManager.getInstance().getService().closeTv();
                        } else if (type.equals("Switch1")) {
                            call = HttpManager.getInstance().getService().closeSwitch1();
                        } else if (type.equals("Switch2")) {
                            call = HttpManager.getInstance().getService().closeSwitch2();
                        } else {
                            call = HttpManager.getInstance().getService().closeCurtain();
                        }
                        call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR,listScene.getData().get(i)));

                    }
                }
            }
        }
    }

    private void sentTempAir(int i, int j) {
        if(listScene.getData().get(i).getData().get(j).getValue().equals("18")){
            call = HttpManager.getInstance().getService().temp18();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("19")){
            call = HttpManager.getInstance().getService().temp19();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("20")){
            call = HttpManager.getInstance().getService().temp20();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("21")){
            call = HttpManager.getInstance().getService().temp21();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("22")){
            call = HttpManager.getInstance().getService().temp22();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("23")){
            call = HttpManager.getInstance().getService().temp23();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("24")){
            call = HttpManager.getInstance().getService().temp24();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("25")){
            call = HttpManager.getInstance().getService().temp25();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("26")){
            call = HttpManager.getInstance().getService().temp26();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("27")){
            call = HttpManager.getInstance().getService().temp27();
        }
        else if(listScene.getData().get(i).getData().get(j).getValue().equals("28")){
            call = HttpManager.getInstance().getService().temp28();
        }
        else{

        }
        call.enqueue(new Callback<TestSendWeb>() {
            @Override
            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {

            }

            @Override
            public void onFailure(Call<TestSendWeb> call, Throwable t) {

            }
        });
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
ButtonItemCollectionCms scene;
        public SentToServer(int mode,ButtonItemCollectionCms buttonItemCollectionCms) {
            this.mode = mode;
            scene = buttonItemCollectionCms;
        }

        @Override
        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
//
            mRootRef.child(pathListTool).setValue(buttonItemCollectionCms);
            Notification notification =
                    new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.icon_app)
                            .setContentTitle("Notification")
                            .setContentText(scene.getName()+" have worked")
                            .setAutoCancel(true)
                            .build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(scene.getNumId(), notification);

        }

        @Override
        public void onFailure(Call<TestSendWeb> call, Throwable t) {

//            mRootRef.child(pathListTool).setValue(buttonItemCollectionCms);
            Notification notification =
                    new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.icon_app)
                            .setContentTitle("Notification")
                            .setContentText(scene.getName()+" have failed")
                            .setAutoCancel(true)
                            .build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(scene.getNumId(), notification);



        }
    }
}
