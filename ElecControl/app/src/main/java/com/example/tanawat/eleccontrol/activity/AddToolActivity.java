package com.example.tanawat.eleccontrol.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.fragment.AddCurtainFragment;
import com.example.tanawat.eleccontrol.fragment.AddRemoteFragment;
import com.example.tanawat.eleccontrol.fragment.AddSwitchFragment;
import com.example.tanawat.eleccontrol.fragment.AddToolFragment;
import com.google.gson.Gson;

/**
 * Created by Tanawat on 26/1/2560.
 */
public class AddToolActivity extends AppCompatActivity implements AddRemoteFragment.FragmentListener,AddSwitchFragment.FragmentListener,AddCurtainFragment.FragmentListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tool);
        initInstance();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, AddToolFragment.newInstance()).commit();
        }
    }

    private void initInstance() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onAddToolCommandButtonClicked(ButtonItemCms buttonItemCms) {
        Intent intent = new Intent(AddToolActivity.this, MainActivity.class);
        Log.d("testAdd", buttonItemCms.getName());
        SharedPreferences prefKey = getApplicationContext().getSharedPreferences("keyTool", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorKey = prefKey.edit();
        String jsonRead = prefKey.getString("json",null);
        int id = new Gson().fromJson(jsonRead,Integer.class);

        id = id +1;
       buttonItemCms.setId(id);


        String json = new Gson().toJson(String.valueOf(id));
        editorKey.putString("json", json);
        editorKey.apply();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("cms", buttonItemCms);
        startActivity(intent);
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
