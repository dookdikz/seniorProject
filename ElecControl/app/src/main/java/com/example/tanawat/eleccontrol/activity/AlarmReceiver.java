package com.example.tanawat.eleccontrol.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.activity.ShowEvent;
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

/**
 * Created by Tanawat on 8/2/2560.
 */
public class AlarmReceiver extends BroadcastReceiver {

    ButtonItemCollectionCms buttonItemCollectionCms;
    ButtonItemCollectionCms allTool;
    ListScene allScene;
    ButtonItemCms buttonItemCms;

    @Override
    public void onReceive(final Context context, Intent intent) {
        FirebaseApp.initializeApp(context);
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        Uri data = intent.getData();
        Log.d("getTTT",data.toString());
        buttonItemCollectionCms = intent.getParcelableExtra("sceneAlarm");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
            Log.d("getTT", buttonItemCollectionCms.getData().get(i).getstatus());
            buttonItemCms = buttonItemCollectionCms.getData().get(i);

            if (buttonItemCms.getType().equals("Air")) {
//                    Toast.makeText(context, "Open", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openAir();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeAir();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            } else if (buttonItemCms.getType().equals("Tv")) {
//                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openTv();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeTv();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (buttonItemCms.getType().equals("Switch1")) {
//                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch1();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch1();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (buttonItemCms.getType().equals("Switch2")) {
//                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch2();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch2();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (buttonItemCms.getType().equals("Curtain")) {
//                    Toast.makeText(context, "Close", Toast.LENGTH_SHORT).show();
                if (buttonItemCms.getstatus().equals("On")) {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().openCurtain();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Call<TestSendWeb> call = HttpManager.getInstance().getService().closeCurtain();
                    call.enqueue(new Callback<TestSendWeb>() {
                        @Override
                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                            Toast.makeText(context, "Suscess + " + buttonItemCms.getName(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
                            Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            }


        }
//        SharedPreferences pref = context.getSharedPreferences("cms", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        String jsonRead = pref.getString("json", null);
//        allTool = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);

        mRootRef.child("listTool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allTool = dataSnapshot.getValue(ButtonItemCollectionCms.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("listScene").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allScene = dataSnapshot.getValue(ListScene.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
                    String idToolInScene = buttonItemCollectionCms.getData().get(i).getId();
                    if (allTool != null) {
                        for (int j = 0; j < allTool.getData().size(); j++) {
                            String idTool = allTool.getData().get(j).getId();
                            if (idTool.equals(idToolInScene)) {
                                Log.d("getStatus", buttonItemCollectionCms.getData().get(i).getstatus());
                                allTool.getData().get(j).setstatus(buttonItemCollectionCms.getData().get(i).getstatus());
                            }
                        }
                    }

                }
                mRootRef.child("listTool").setValue(allTool);

                for (int i = 0; i < allScene.getData().size(); i++) {
                    String idScene = buttonItemCollectionCms.getId();
                    if (idScene.equals(allScene.getData().get(i).getId())) {
                        allScene.getData().get(i).setCheckTime("Off");
                    }
                }
                mRootRef.child("listScene").setValue(allScene);

            }
        }, 3000);

//        Intent i = new Intent(context, ShowEvent.class);
//        ButtonItemCollectionCms buttonItemCollectionCms = intent.getParcelableExtra("sceneAlarm");
//        Log.d("SceneAlarmm", String.valueOf(buttonItemCollectionCms.getName()));
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.putExtra("sceneAlarm", buttonItemCollectionCms);
//        context.startActivity(i);
    }
}
