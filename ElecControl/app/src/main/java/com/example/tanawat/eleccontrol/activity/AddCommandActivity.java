package com.example.tanawat.eleccontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.fragment.AddCommandFragment;
import com.example.tanawat.eleccontrol.fragment.MainFragment;

/**
 * Created by Tanawat on 25/11/2559.
 */
public class AddCommandActivity extends AppCompatActivity implements AddCommandFragment.FragmentListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);

        initInstance();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer,AddCommandFragment.newInstance()).commit();
        }

    }

    private void initInstance() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onAddCommandButtonClicked(ButtonItemCms buttonItemCms) {
        Intent intent = new Intent(AddCommandActivity.this,MainActivity.class);
        Log.d("testAdd",buttonItemCms.getName());
        intent.putExtra("cms",buttonItemCms);
        startActivity(intent);

    }
}
