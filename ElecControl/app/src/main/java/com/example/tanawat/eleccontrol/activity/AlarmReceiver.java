package com.example.tanawat.eleccontrol.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.tanawat.eleccontrol.activity.ShowEvent;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;

/**
 * Created by Tanawat on 8/2/2560.
 */
public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ShowEvent.class);
        ButtonItemCollectionCms buttonItemCollectionCms = intent.getParcelableExtra("sceneAlarm");
        Log.d("SceneAlarmm", String.valueOf(buttonItemCollectionCms.getName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("sceneAlarm", buttonItemCollectionCms);
        context.startActivity(i);
    }
}
