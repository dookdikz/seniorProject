package com.example.tanawat.eleccontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.example.tanawat.eleccontrol.fragment.AddSceneFragment;
import com.example.tanawat.eleccontrol.fragment.AddToolFragment;

/**
 * Created by Tanawat on 2/2/2560.
 */
public class AddSceneActivity extends AppCompatActivity implements AddSceneFragment.FragmentListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scene);
        initInstance();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, AddSceneFragment.newInstance()).commit();
        }
    }

    private void initInstance() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onAddSceneButtonClicked(ButtonItemCollectionCms buttonItemCollectionCms) {
        Intent intent = new Intent(AddSceneActivity.this, MainActivity.class);
        Log.d("testScene",buttonItemCollectionCms.getName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("scene", buttonItemCollectionCms);
        intent.putExtra("activity","addSceneActivity");
        startActivity(intent);
    }
}
