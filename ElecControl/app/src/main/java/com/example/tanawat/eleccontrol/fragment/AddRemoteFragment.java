package com.example.tanawat.eleccontrol.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddRemoteFragment extends Fragment {
    public interface FragmentListener{
        void onAddToolCommandButtonClicked(ButtonItemCms buttonItemCms);
    }

    private Spinner spinChooseRemote;
//    private Spinner spinOnOrOff;
    EditText etNameRemote;
    String choosenRemote;
    String choosenOnOff;
    private Button btnAddTool;
    public AddRemoteFragment() {
        super();
    }

    public static AddRemoteFragment newInstance() {
        AddRemoteFragment fragment = new AddRemoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    public static void closeKeyboard(Context c, IBinder windowToken) {
        InputMethodManager mgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(windowToken, 0);
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
        View rootView = inflater.inflate(R.layout.fragment_add_remote, container, false);
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

        spinChooseRemote = (Spinner) rootView.findViewById(R.id.spinChooseRemote);
//        spinOnOrOff = (Spinner) rootView.findViewById(R.id.spinOnOrOff);
        etNameRemote = (EditText) rootView.findViewById(R.id.etNameRemote);
        final String[] chooseRemote = getResources().getStringArray(R.array.chooseRemote);
//        final String[] onOrOff = getResources().getStringArray(R.array.onOrOff);
        final ArrayAdapter<String> adapterChooseRemote = new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_dropdown_item_1line,chooseRemote);
        spinChooseRemote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choosenRemote = chooseRemote[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        final ArrayAdapter<String> adapterOnOrOff = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_dropdown_item_1line,onOrOff);
//        spinOnOrOff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                choosenOnOff = onOrOff[position];
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        spinChooseRemote.setAdapter(adapterChooseRemote);
//        spinOnOrOff.setAdapter(adapterOnOrOff);
        closeKeyboard(getActivity(), etNameRemote.getWindowToken());
        btnAddTool = (Button) rootView.findViewById(R.id.btnAddTool) ;
        btnAddTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ButtonItemCms buttonItemCms = new ButtonItemCms();
                buttonItemCms.setName(etNameRemote.getText().toString());
                buttonItemCms.setType(choosenRemote);
                buttonItemCms.setstatus("Off");
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onAddToolCommandButtonClicked(buttonItemCms);
            }
        });
        if(!(rootView instanceof EditText)) {

            rootView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.


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

}
