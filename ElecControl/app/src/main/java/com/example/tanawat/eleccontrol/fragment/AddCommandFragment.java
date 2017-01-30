package com.example.tanawat.eleccontrol.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddCommandFragment extends Fragment {
    public interface FragmentListener{
        void onAddCommandButtonClicked(ButtonItemCms buttonItemCms);
    }
    Button btnAddCommand;
    EditText editNameCommand;
    String typeOfAddComand;
    private Spinner spinAddCommand;

    public AddCommandFragment() {
        super();
    }

    public static AddCommandFragment newInstance() {
        AddCommandFragment fragment = new AddCommandFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_commannd, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
        editNameCommand = (EditText) rootView.findViewById(R.id.editNameCommand);
//        spinAddCommand = (Spinner) rootView.findViewById(R.id.spinAddCommand);
//        final String[] typeCommand = getResources().getStringArray(R.array.onOrOff);
//        final ArrayAdapter<String> adapterType = new ArrayAdapter<String>(rootView.getContext(),
//                android.R.layout.simple_dropdown_item_1line,typeCommand);
//        spinAddCommand.setAdapter(adapterType);
//        spinAddCommand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                typeOfAddComand = typeCommand[position];
//                Toast.makeText(getContext(),typeOfAddComand,Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        btnAddCommand = (Button) rootView.findViewById(R.id.btnAddCommand) ;
        btnAddCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonItemCms buttonItemCms = new ButtonItemCms();
                buttonItemCms.setName(editNameCommand.getText().toString());
                buttonItemCms.setType(typeOfAddComand);
                buttonItemCms.setstatus("off");
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onAddCommandButtonClicked(buttonItemCms);
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance (Fragment level's variables) State here
    }

    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance (Fragment level's variables) State here
    }

}
