package com.example.tanawat.eleccontrol.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, MainFragment.newInstance()).commit();
        }
    }
}
