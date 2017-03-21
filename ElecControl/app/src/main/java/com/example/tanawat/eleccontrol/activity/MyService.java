package com.example.tanawat.eleccontrol.activity;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.tanawat.eleccontrol.adapter.ButtonListAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by dookdikz on 4/3/2560.
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("listTool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot!=null){
//                    Log.d("background",dataSnapshot.getValue(ButtonItemCollectionCms.class).getData().get(0).getName());
//                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               Log.d("background","eiei");


            }
        }, 10000);
        return super.onStartCommand(intent, flags, startId);
    }
}
