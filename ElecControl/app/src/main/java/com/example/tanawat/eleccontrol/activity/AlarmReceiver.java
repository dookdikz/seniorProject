package com.example.tanawat.eleccontrol.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.example.tanawat.eleccontrol.cms.TestSendWeb;
import com.example.tanawat.eleccontrol.manager.HttpManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.tanawat.eleccontrol.manager.HttpManager.url;

/**
 * Created by Tanawat on 8/2/2560.
 */
public class AlarmReceiver extends BroadcastReceiver {

    static ButtonItemCollectionCms buttonItemCollectionCms;
    ButtonItemCollectionCms allTool;
    ListScene allScene;
    ButtonItemCms buttonItemCms;
    String mUser;
    String pathListTool;
    String pathListScene;
    String pathIp;
    int check = 0;


    static Call<TestSendWeb> call;
    int id;

    @Override
    public void onReceive(final Context context, Intent intent) {
        FirebaseApp.initializeApp(context);
        mUser = intent.getStringExtra("mUser");
        pathListTool = mUser + "/listTool";
        pathListScene = mUser + "/listScene";
        pathIp = mUser + "/ip";
        id = intent.getIntExtra("id", 0);
        Log.d("getTInt", String.valueOf(id));
        check = 1;
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child(pathListScene).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    allScene = dataSnapshot.getValue(ListScene.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(pathListTool).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allTool = dataSnapshot.getValue(ButtonItemCollectionCms.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(pathIp).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    url = dataSnapshot.getValue(String.class);
                    HttpManager.instance = null;
                    HttpManager.setUrl(url);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run() {
                for (int j = 0; j < allScene.getData().size(); j++) {
                    Log.d("getTInt2", String.valueOf(allScene.getData().get(j).getNumId()));
                    if (allScene.getData().get(j).getNumId() == id) {
                        buttonItemCollectionCms = allScene.getData().get(j);
                        Log.d("getTTool", buttonItemCollectionCms.getName());
                    }

                }
                Log.d("getTName", buttonItemCollectionCms.getName());
                Log.d("getTSize", String.valueOf(allScene.getData().size()));
                for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {

                    buttonItemCms = buttonItemCollectionCms.getData().get(i);

                    if (buttonItemCms.getType().equals("Air")) {
                        if (buttonItemCms.getstatus().equals("On")) {
                            call = HttpManager.getInstance().getService().openAir();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        } else {
                            call = HttpManager.getInstance().getService().closeAir();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        }

                    } else if (buttonItemCms.getType().equals("Tv")) {

                        if (buttonItemCms.getstatus().equals("On")) {
                            call = HttpManager.getInstance().getService().openTv();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        } else {
                            call = HttpManager.getInstance().getService().closeTv();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        }
                    } else if (buttonItemCms.getType().equals("Switch1")) {
//                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                        if (buttonItemCms.getstatus().equals("On")) {
                            call = HttpManager.getInstance().getService().openSwitch1();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        } else {
                            call = HttpManager.getInstance().getService().closeSwitch1();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        }
                    } else if (buttonItemCms.getType().equals("Switch2")) {
//                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                        if (buttonItemCms.getstatus().equals("On")) {
                            call = HttpManager.getInstance().getService().openSwitch2();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        } else {
                            call = HttpManager.getInstance().getService().closeSwitch2();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        }
                    } else if (buttonItemCms.getType().equals("Curtain")) {
//                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                        if (buttonItemCms.getstatus().equals("On")) {
                            call = HttpManager.getInstance().getService().openCurtain();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        } else {
                            call = HttpManager.getInstance().getService().closeCurtain();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
                        }
                    } else {
                        Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }, 6000);


//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
//
//            buttonItemCms = buttonItemCollectionCms.getData().get(i);
//
//            if (buttonItemCms.getType().equals("Air")) {
//                if (buttonItemCms.getstatus().equals("On")) {
//                    call = HttpManager.getInstance().getService().openAir();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                } else {
//                    call = HttpManager.getInstance().getService().closeAir();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                }
//
//            } else if (buttonItemCms.getType().equals("Tv")) {
//
//                if (buttonItemCms.getstatus().equals("On")) {
//                    call = HttpManager.getInstance().getService().openTv();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                } else {
//                    call = HttpManager.getInstance().getService().closeTv();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                }
//            } else if (buttonItemCms.getType().equals("Switch1")) {
////                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
//                if (buttonItemCms.getstatus().equals("On")) {
//                    call = HttpManager.getInstance().getService().openSwitch1();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                } else {
//                    call = HttpManager.getInstance().getService().closeSwitch1();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                }
//            } else if (buttonItemCms.getType().equals("Switch2")) {
////                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
//                if (buttonItemCms.getstatus().equals("On")) {
//                    call = HttpManager.getInstance().getService().openSwitch2();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                } else {
//                    call = HttpManager.getInstance().getService().closeSwitch2();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                }
//            } else if (buttonItemCms.getType().equals("Curtain")) {
////                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
//                if (buttonItemCms.getstatus().equals("On")) {
//                    call = HttpManager.getInstance().getService().openCurtain();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                } else {
//                    call = HttpManager.getInstance().getService().closeCurtain();
//                    call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR, context, buttonItemCollectionCms));
//                }
//            } else {
//                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
//            }
//
//
//        }
//    }
//}, 3000);
//        SharedPreferences pref = context.getSharedPreferences("cms", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        String jsonRead = pref.getString("json", null);
//        allTool = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
//                    String idToolInScene = buttonItemCollectionCms.getData().get(i).getId();
//                    if (allTool != null) {
//                        for (int j = 0; j < allTool.getData().size(); j++) {
//                            String idTool = allTool.getData().get(j).getId();
//                            if (idTool.equals(idToolInScene)) {
//                                allTool.getData().get(j).setstatus(buttonItemCollectionCms.getData().get(i).getstatus());
//                            }
//                        }
//                    }
//
//                }
//
//
////                for (int i = 0; i < allScene.getData().size(); i++) {
////                    String idScene = buttonItemCollectionCms.getId();
////                    if (idScene.equals(allScene.getData().get(i).getId())) {
////                        allScene.getData().get(i).setCheckTime("Off");
////                    }
////                }
////                mRootRef.child(pathListScene).setValue(allScene);
//


//        Intent i = new Intent(context, ShowEvent.class);
//        ButtonItemCollectionCms buttonItemCollectionCms = intent.getParcelableExtra("sceneAlarm");
//        Log.d("SceneAlarmm", String.valueOf(buttonItemCollectionCms.getName()));
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.putExtra("sceneAlarm", buttonItemCollectionCms);
//        context.startActivity(i);
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
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        int mode;
        Context context;
        ButtonItemCollectionCms scene;

        public SentToServer(int mode, Context context, ButtonItemCollectionCms scene) {
            this.mode = mode;
            this.context = context;
            this.scene = scene;
        }

        @Override
        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {

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


            Notification notification =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.icon_app)
                            .setContentTitle("Notification")
                            .setContentText(scene.getName() + " have worked")
                            .setAutoCancel(true)
                            .build();

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(scene.getNumId(), notification);
            mRootRef.child(pathListTool).setValue(allTool);
            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
        }

        @Override

        public void onFailure(Call<TestSendWeb> call, Throwable t) {
//            for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
//                String idToolInScene = buttonItemCollectionCms.getData().get(i).getId();
//                if (allTool != null) {
//                    for (int j = 0; j < allTool.getData().size(); j++) {
//                        String idTool = allTool.getData().get(j).getId();
//                        if (idTool.equals(idToolInScene)) {
//                            allTool.getData().get(j).setstatus(buttonItemCollectionCms.getData().get(i).getstatus());
//                        }
//                    }
//                }
//
//            }


//                for (int i = 0; i < allScene.getData().size(); i++) {
//                    String idScene = buttonItemCollectionCms.getId();
//                    if (idScene.equals(allScene.getData().get(i).getId())) {
//                        allScene.getData().get(i).setCheckTime("Off");
//                    }
//                }
//                mRootRef.child(pathListScene).setValue(allScene);


            Notification notification =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.icon_app)
                            .setContentTitle("Notification")
                            .setContentText(scene.getName() + " have failed")
                            .setAutoCancel(true)
                            .build();

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(scene.getNumId(), notification);
//            mRootRef.child(pathListTool).setValue(allTool);
            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
