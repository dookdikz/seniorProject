package com.example.tanawat.eleccontrol.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.fragment.EditSceneFragment;
import com.example.tanawat.eleccontrol.fragment.EditSceneOptionFragment;
import com.example.tanawat.eleccontrol.fragment.SetSceneOptionFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Tanawat on 2/2/2560.
 */
public class EditSceneActivity extends AppCompatActivity implements EditSceneOptionFragment.FragmentListener {
    Toolbar toolbar;
    ArrayList<Boolean> checkEdit;
    ButtonItemCollectionCms buttonItemCollectionCms;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_scene);
        buttonItemCollectionCms = getIntent().getParcelableExtra("editScene");
        checkEdit = new ArrayList<>();
        ButtonItemCollectionCms listTool = getIntent().getParcelableExtra("listTool");
        for(int i=0;i<listTool.getData().size();i++){
            checkEdit.add(false);
        }
        Log.d("checkEditt",checkEdit.toString());
        initInstance();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.contentContainer, EditSceneFragment.newInstance(buttonItemCollectionCms,checkEdit)).commit();
        }
    }

    private void initInstance() {

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onEditSceneButtonClicked(ButtonItemCollectionCms buttonItemCollectionCms) {
        Intent intent = new Intent(EditSceneActivity.this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("editScene", buttonItemCollectionCms);

        intent.putExtra("activity","EditSceneActivity");
        startActivity(intent);
//                Intent intent = new Intent(EditSceneActivity.this, SetTimeOrSensor.class);
//                startActivity(intent);

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }
}
