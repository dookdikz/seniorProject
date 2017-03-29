package com.example.tanawat.eleccontrol.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tanawat on 13/2/2560.
 */
public class CrawlAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test5s","eiei");
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("testBack").setValue("eeieiie");
    }
}
