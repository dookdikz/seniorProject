package com.example.tanawat.eleccontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.fragment.AddCurtainFragment;
import com.example.tanawat.eleccontrol.fragment.AddRemoteFragment;
import com.example.tanawat.eleccontrol.fragment.AddSwitchFragment;
import com.example.tanawat.eleccontrol.fragment.AddToolFragment;

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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("cms", buttonItemCms);
        startActivity(intent);
    }
}
