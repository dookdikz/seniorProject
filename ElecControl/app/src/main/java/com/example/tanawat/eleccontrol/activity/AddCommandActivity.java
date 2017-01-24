package com.example.tanawat.eleccontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.adapter.ButtonListAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.fragment.AddCommandFragment;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.google.gson.Gson;

/**
 * Created by Tanawat on 25/11/2559.
 */
public class AddCommandActivity extends AppCompatActivity implements AddCommandFragment.FragmentListener {
    ButtonItemCollectionCms buttonItemCollectionCms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);

        initInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, AddCommandFragment.newInstance()).commit();
        }


    }

    private void initInstance() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onAddCommandButtonClicked(ButtonItemCms buttonItemCms) {
        Intent intent = new Intent(AddCommandActivity.this, MainActivity.class);
        Log.d("testAdd", buttonItemCms.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("cms", buttonItemCms);
        startActivity(intent);
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        String jsonRead = pref.getString("json", null);
//        buttonItemCollectionCms = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
//        buttonItemCollectionCms.addData(buttonItemCms);
//        String json = new Gson().toJson(buttonItemCollectionCms);
//        editor.putString("json", json);
//        Log.d("saveAdd", buttonItemCollectionCms.getData().toString());
//        editor.apply();
//        MainFragment mainFragment= new MainFragment();
//        ButtonListAdapter listAdapter ;
//        listAdapter = new ButtonListAdapter(buttonItemCollectionCms,getApplicationContext());
//        listAdapter.setButtonItemCollectionCms(buttonItemCollectionCms);
//        mainFragment.update();
//
//        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
