package com.example.tanawat.eleccontrol.activity;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by dookdikz on 4/3/2560.
 */

public class MyService extends Service {
    ButtonItemCollectionCms buttonItemCollectionCms;
    ListScene listScene;
    String temp;
    String light;
    String macBlue;
    String statusBlue;

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

                    light = dataSnapshot.getValue(String.class);
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

                    temp = dataSnapshot.getValue(String.class);

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

                    statusBlue = dataSnapshot.getValue(String.class);
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
                Log.d("background", "eiei");


            }
        }, 10000);
        return super.onStartCommand(intent, flags, startId);
    }
}
