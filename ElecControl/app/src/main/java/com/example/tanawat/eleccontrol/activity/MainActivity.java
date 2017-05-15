package com.example.tanawat.eleccontrol.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import com.example.tanawat.eleccontrol.cms.TestSendWeb;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.example.tanawat.eleccontrol.fragment.SceneFragment;
import com.example.tanawat.eleccontrol.fragment.SettingDialogFragment;
import com.example.tanawat.eleccontrol.manager.HttpManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener, SceneFragment.FragmentListener, GoogleApiClient.OnConnectionFailedListener {
    private static final String ANONYMOUS = "anonymous";
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
    LinearLayout layoutSpeak;
    LinearLayout layoutSignOut;
    ButtonItemCollectionCms allTool;
    static Call<TestSendWeb> call;
    String bluetoothCollection = null;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    protected static final int REQUEST_OK = 1;
    private GoogleApiClient mGoogleApiClient;
    final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    String pathListTool;
    Intent intentService;
    String mUsername;
    String mPhotoUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = ANONYMOUS;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity

            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getUid();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
            pathListTool = mUsername + "/listTool";
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        ButtonItemCms cms = getIntent().getParcelableExtra("cms");
        ButtonItemCollectionCms scene = getIntent().getParcelableExtra("scene");
        ButtonItemCollectionCms editScene = getIntent().getParcelableExtra("editScene");


        initInstance();
        if (savedInstanceState == null) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("mUserId", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            String json = new Gson().toJson(mUsername);
            editor.putString("json", json);
            editor.apply();
            intentService = new Intent(this, MyService.class);
            intentService.putExtra("mUser", mUsername);

            startService(intentService);
            if (getIntent().getStringExtra("activity") != null) {
                if (getIntent().getStringExtra("activity").equals("addSceneActivity")) {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, SceneFragment.newInstance(scene, mUsername)).commit();
                } else if (getIntent().getStringExtra("activity").equals("EditSceneActivity")) {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, SceneFragment.newInstance(editScene, "edit", mUsername)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance(cms, mUsername)).commit();
                }

            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance(cms, mUsername)).commit();
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
        layoutSpeak = (LinearLayout) findViewById(R.id.layoutSpeak);
        layoutSignOut = (LinearLayout) findViewById(R.id.layoutSignOut);
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        mRootRef.child("sensor/temp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvTempSensor.setText(dataSnapshot.getValue().toString() + " C");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("sensor/light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue(Long.class) != 0) {
                        Long light = dataSnapshot.getValue(Long.class);
                        if (light > 1023) {
                            light = Long.valueOf(1023);
                        }
                        light = ((1023 - light) * 100 / light);
                        tvLightSensor.setText(light.toString() + " Lux");
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child(pathListTool).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    allTool = dataSnapshot.getValue(ButtonItemCollectionCms.class);

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
                if (bluetoothCollection != null)
                {
                    Log.d("Mac1",macAddress);
                    Log.d("Mac2",bluetoothCollection);
                    if (macAddress.equals(bluetoothCollection)) {

                        checkRe = true;
                    }
                    if (checkRe == false)
                    {

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
                DialogFragment newFragment = SettingDialogFragment.newInstance(2, mUsername + "/ip");
                newFragment.show(ft, "dialog");
            }
        });
        layoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mUsername = ANONYMOUS;
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                stopService(intentService);
                startActivity(i);
            }
        });
        layoutSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "th-TH");
                try {
                    startActivityForResult(i, REQUEST_OK);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
                }
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
        intent.putExtra("mUser", mUsername);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OK && resultCode == RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.d("SpeckData", thingsYouSaid.toString());
            Toast.makeText(getApplicationContext(), thingsYouSaid.get(0), Toast.LENGTH_SHORT).show();
            if (thingsYouSaid.get(0).matches("(.*)เปิดแอร์(.*)")) {
                call = HttpManager.getInstance().getService().openAir();
                call.enqueue(new SentToServer(SentToServer.MODE_OPEN_AIR));
            }
            else if (thingsYouSaid.get(0).matches("(.*)ปิดแอร์(.*)")){
                call = HttpManager.getInstance().getService().closeAir();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
            }
            else if (thingsYouSaid.get(0).matches("(.*)เปิดไฟ(.*)")){
                call = HttpManager.getInstance().getService().openSwitch1();
                call.enqueue(new SentToServer(SentToServer.MODE_OPEN_SWITCH1));
            }
            else if (thingsYouSaid.get(0).matches("(.*)ปิดไฟ(.*)")){
                call = HttpManager.getInstance().getService().closeSwitch1();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_SWITCH1));
            }
            else if (thingsYouSaid.get(0).matches("(.*)เปิดม่าน(.*)")){
                call = HttpManager.getInstance().getService().openCurtain();
                call.enqueue(new SentToServer(SentToServer.MODE_OPEN_CURTAIN));
            }
            else if (thingsYouSaid.get(0).matches("(.*)ปิดม่าน(.*)")){
                call = HttpManager.getInstance().getService().closeCurtain();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_CURTAIN));
            }
            else if (thingsYouSaid.get(0).matches("(.*)เปิดทีวี(.*)")){
                call = HttpManager.getInstance().getService().openTv();
                call.enqueue(new SentToServer(SentToServer.MODE_OPEN_TV));
            }
            else if (thingsYouSaid.get(0).matches("(.*)ปิดทีวี(.*)")){
                call = HttpManager.getInstance().getService().closeTv();
                call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_TV));
            }

            else {
                call = null;
                Toast.makeText(getApplicationContext(), "ลองใหม่", Toast.LENGTH_SHORT).show();
            }

        }
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
        int mode;

        public SentToServer(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
            if(mode==1){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Air")){
                        if(allTool.getData().get(i).getstatus().equals("Off")){
                            allTool.getData().get(i).setstatus("On");
                        }
                    }
                }
            }
            else if(mode==2){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Air")){
                        if(allTool.getData().get(i).getstatus().equals("On")){
                            allTool.getData().get(i).setstatus("Off");
                        }
                    }
                }
            }
            else if(mode==3){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Tv")){
                        if(allTool.getData().get(i).getstatus().equals("Off")){
                            allTool.getData().get(i).setstatus("On");
                        }
                    }
                }
            }
            else if(mode==4){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Tv")){
                        if(allTool.getData().get(i).getstatus().equals("On")){
                            allTool.getData().get(i).setstatus("Off");
                        }
                    }
                }
            }
            else if(mode==5){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Switch1")){
                        if(allTool.getData().get(i).getstatus().equals("Off")){
                            allTool.getData().get(i).setstatus("On");
                        }
                    }
                }
            }
            else if(mode==6){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Switch1")){
                        if(allTool.getData().get(i).getstatus().equals("On")){
                            allTool.getData().get(i).setstatus("Off");
                        }
                    }
                }
            }
            else if(mode==7){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Curtain")){
                        if(allTool.getData().get(i).getstatus().equals("Off")){
                            allTool.getData().get(i).setstatus("On");
                        }
                    }
                }
            }
            else if(mode==8){
                for(int i =0;i<allTool.getData().size();i++){
                    if(allTool.getData().get(i).getType().equals("Curtain")){
                        if(allTool.getData().get(i).getstatus().equals("On")){
                            allTool.getData().get(i).setstatus("Off");
                        }
                    }
                }
            }
            mRootRef.child(pathListTool).setValue(allTool);
            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
        }

        @Override

        public void onFailure(Call<TestSendWeb> call, Throwable t) {
Toast.makeText(getApplicationContext(),String.valueOf(mode)+"fail",Toast.LENGTH_SHORT).show();

        }
}
}
