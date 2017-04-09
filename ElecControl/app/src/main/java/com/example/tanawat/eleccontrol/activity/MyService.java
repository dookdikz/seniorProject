package com.example.tanawat.eleccontrol.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        buttonItemCollectionCms = new ButtonItemCollectionCms();
        listScene = new ListScene();

        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("listTool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    buttonItemCollectionCms = dataSnapshot.getValue(ButtonItemCollectionCms.class);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("listScene").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    listScene = dataSnapshot.getValue(ListScene.class);

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
                    for(int i=0;i<listScene.getData().size();i++){
                        if(!listScene.getData().get(i).getCheckTempSen().equals("Off")){
                            if(listScene.getData().get(i).getCheckTempSen().equals("more than")){
                                if(Long.parseLong(listScene.getData().get(i).getTemp())>temp);
                                {
                                    for(int j=0;j<listScene.getData().get(i).getData().size();j++){
                                        if(listScene.getData().get(i).getData().get(j).getType().equals("Air")){

                                        }
                                        else if(listScene.getData().get(i).getData().get(j).getType().equals("Tv")){

                                        }
                                        else if(listScene.getData().get(i).getData().get(j).getType().equals("Switch1")){

                                        }
                                        else if(listScene.getData().get(i).getData().get(j).getType().equals("Curtain")){

                                        }
                                        else{
                                            
                                        }
                                    }
                                }
                            }

                        }

                    }
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
                if(temp !=null){
                    if(temp<25){
                        Log.d("background", "7893");
                    }

                }



            }
        }, 3000);
        return super.onStartCommand(intent, flags, startId);
    }
}
