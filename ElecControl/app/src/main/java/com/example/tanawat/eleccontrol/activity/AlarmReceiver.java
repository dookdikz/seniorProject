package com.example.tanawat.eleccontrol.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tanawat.eleccontrol.activity.ShowEvent;

/**
 * Created by Tanawat on 8/2/2560.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ShowEvent.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
