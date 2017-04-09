package com.example.tanawat.eleccontrol.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;

/**
 * Created by Tanawat on 8/2/2560.
 */
public class testBroad extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ShowEvent.class);
       String a = intent.getParcelableExtra("testB");
        Log.d("testBB", a);

    }
}
