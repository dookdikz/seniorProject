package com.example.tanawat.eleccontrol.activity;

import android.app.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tanawat.eleccontrol.R;

/**
 * Created by Tanawat on 8/2/2560.
 */
public class SetTimeOrSensor extends AppCompatActivity {


    Button buttonstartSetDialog;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_set_option_scene);



        buttonstartSetDialog = (Button)findViewById(R.id.startSetTime);
        buttonstartSetDialog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //openTimePickerDialog(true);
                showTimePickerDialog(buttonstartSetDialog);
            }});

    }

    public void showTimePickerDialog(View v) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
//        DialogFragment newFragment = SetTimeDialogFragment.newInstance(2);
//        newFragment.show(ft, "dialog");


    }




    @Override
    protected void onResume() {
        super.onResume();

    }
}
