package com.example.tanawat.eleccontrol.activity;


import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.example.tanawat.eleccontrol.fragment.SceneFragment;
import com.example.tanawat.eleccontrol.fragment.SettingDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener, SceneFragment.FragmentListener {
    Button btnGoScene;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    TextView tvTempSensor;
    TextView tvLightSensor;
    LinearLayout layoutSetBluetooth;
    LinearLayout layoutAddTool;
    LinearLayout layoutAddScene;
    LinearLayout layoutSetIp;
    String bluetoothCollection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonItemCms cms = getIntent().getParcelableExtra("cms");
        ButtonItemCollectionCms scene = getIntent().getParcelableExtra("scene");
        ButtonItemCollectionCms editScene = getIntent().getParcelableExtra("editScene");


        initInstance();
        if (savedInstanceState == null) {

            startService(new Intent(this, MyService.class));
            if (getIntent().getStringExtra("activity") != null) {
                if (getIntent().getStringExtra("activity").equals("addSceneActivity")) {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, SceneFragment.newInstance(scene)).commit();
                } else if (getIntent().getStringExtra("activity").equals("EditSceneActivity")) {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, SceneFragment.newInstance(editScene, "edit")).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance(cms)).commit();
                }

            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance(cms)).commit();
            }
        }
    }

    private void initInstance() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tvTempSensor = (TextView) findViewById(R.id.tvTempSensor);
        tvLightSensor = (TextView) findViewById(R.id.tvLightSensor);
        layoutSetBluetooth = (LinearLayout) findViewById(R.id.layoutSetBluetooth);
        layoutAddTool = (LinearLayout) findViewById(R.id.layoutAddTool);
        layoutAddScene = (LinearLayout) findViewById(R.id.layoutAddScene);
        layoutSetIp = (LinearLayout) findViewById(R.id.layoutSetIp);
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("sensor/temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvTempSensor.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("sensor/light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    Long light = dataSnapshot.getValue(Long.class);
                    light = ((1023-light)*  10/light);
                    tvLightSensor.setText(light.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("MacBlue/data/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    bluetoothCollection = dataSnapshot.getValue(String.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        layoutAddTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClicked();
            }
        });
        layoutAddScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddSceneButtonClicked();
            }
        });
        layoutSetBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String macAddress = android.provider.Settings.Secure.getString(getContentResolver(), "bluetooth_address");
                boolean checkRe = false;
                if (bluetoothCollection != null) {
                    if (macAddress.equals(bluetoothCollection)) {

                        checkRe = true;
                    }
                    if (checkRe == false) {

                        AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                        adb.setTitle("Change Bluetooth");
                        adb.setMessage("Are you sure you want to change Bluetooth Address from" + bluetoothCollection + " to " + macAddress);


                        adb.setNegativeButton("Cancel", null);
                        adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                bluetoothCollection = macAddress;


                            }
                        });
                        adb.show();


                    } else {
                        Toast.makeText(getApplicationContext(), "Add Bluetooth Address Already", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    bluetoothCollection = macAddress;
                    Toast.makeText(getApplicationContext(), "Add Bluetooth Success", Toast.LENGTH_SHORT).show();
                }


                mRootRef.child("MacBlue/data").setValue(bluetoothCollection);


            }
        });
        layoutSetIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            DialogFragment newFragment = SettingDialogFragment.newInstance(2);
            newFragment.show(ft, "dialog");
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void onAddButtonClicked() {
        Intent intent = new Intent(MainActivity.this, AddToolActivity.class);
        startActivity(intent);

    }


    @Override
    public void onAddSceneButtonClicked() {

        Intent intent = new Intent(MainActivity.this, AddSceneActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
