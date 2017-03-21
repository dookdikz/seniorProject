package com.example.tanawat.eleccontrol.activity;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.fragment.AddCommandFragment;
import com.example.tanawat.eleccontrol.fragment.AddToolFragment;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.example.tanawat.eleccontrol.fragment.SceneFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener, SceneFragment.FragmentListener {
    Button btnGoScene;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonItemCms cms = getIntent().getParcelableExtra("cms");
        ButtonItemCollectionCms scene = getIntent().getParcelableExtra("scene");

        initInstance();
        if (savedInstanceState == null) {

            startService(new Intent(this, MyService.class));
            if (getIntent().getStringExtra("activity") != null) {
                if (getIntent().getStringExtra("activity").equals("addSceneActivity")) {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, SceneFragment.newInstance(scene)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance(cms)).commit();
                }

            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance(cms)).commit();
            }
        }
    }

    private void initInstance() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,drawerLayout,R.string.open_drawer,R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
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
}
