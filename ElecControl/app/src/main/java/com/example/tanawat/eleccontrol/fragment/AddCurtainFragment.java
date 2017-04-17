package com.example.tanawat.eleccontrol.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddCurtainFragment extends Fragment {
    private Button btnAddTool;
    EditText etNameCurtain;


    public interface FragmentListener{
        void onAddToolCommandButtonClicked(ButtonItemCms buttonItemCms);
    }
    private Spinner spinChooseCurtain;
    private Spinner spinOnOrOff;

    public AddCurtainFragment() {
        super();
    }

    public static AddCurtainFragment newInstance() {
        AddCurtainFragment fragment = new AddCurtainFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_curtain, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true);
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {

        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
//        spinChooseCurtain = (Spinner) rootView.findViewById(R.id.spinChooseCurtain);
//        spinOnOrOff = (Spinner) rootView.findViewById(R.id.spinOnOrOff);
        etNameCurtain = (EditText) rootView.findViewById(R.id.etNameCurtain);
//        final String[] chooseCurtain = getResources().getStringArray(R.array.chooseCurtain);
        final String[] onOrOff = getResources().getStringArray(R.array.onOrOff);
//        final ArrayAdapter<String> adapterChooseCurtain = new ArrayAdapter<String>(rootView.getContext(),
//                android.R.layout.simple_dropdown_item_1line,chooseCurtain);
//        spinChooseCurtain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        final ArrayAdapter<String> adapterOnOrOff = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_dropdown_item_1line,onOrOff);
//        spinChooseCurtain.setAdapter(adapterChooseCurtain);
//        spinOnOrOff.setAdapter(adapterOnOrOff);
//        btnAddTool = (Button) rootView.findViewById(R.id.btnAddTool) ;
//        btnAddTool.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ButtonItemCms buttonItemCms = new ButtonItemCms();
//                buttonItemCms.setName(etNameCurtain.getText().toString());
//                buttonItemCms.setType("Curtain");
//                buttonItemCms.setstatus("Off");
//                FragmentListener listener = (FragmentListener) getActivity();
//                listener.onAddToolCommandButtonClicked(buttonItemCms);
//            }
//        });
        if(!(rootView instanceof EditText)) {

            rootView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }
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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_submit_tool,menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionSubmit) {
            if (etNameCurtain.getText().toString().matches("")) {
                Toast.makeText(getContext(), "Please input name", Toast.LENGTH_SHORT).show();
            }
            else {
                ButtonItemCms buttonItemCms = new ButtonItemCms();
                buttonItemCms.setName(etNameCurtain.getText().toString());
                buttonItemCms.setType("Curtain");
                buttonItemCms.setstatus("Off");
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onAddToolCommandButtonClicked(buttonItemCms);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
