package com.example.tanawat.eleccontrol.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.fragment.AddCommandFragment;
import com.example.tanawat.eleccontrol.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance()).commit();
        }
    }


    @Override
    public void onAddButtonClicked() {
        Intent intent = new Intent(MainActivity.this,AddCommandActivity.class);
        startActivity(intent);
    }
}
