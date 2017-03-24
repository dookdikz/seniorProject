package com.example.tanawat.eleccontrol.fragment;

import android.app.Activity;
import android.content.Context;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.adapter.AddSceneAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class AddSceneFragment extends Fragment {

    static ListView listView;
    static TextView tvCountTool;
    static AddSceneAdapter listAdapter;
    ButtonItemCollectionCms buttonItemCollectionCms;
    EditText etNameScene;
    CheckBox cbAddScene;
    ListScene listScene;


    public AddSceneFragment() {
        super();
    }

    public static AddSceneFragment newInstance() {
        AddSceneFragment fragment = new AddSceneFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_add_scene, container, false);
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
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) rootView.findViewById(R.id.listView);
        etNameScene = (EditText) rootView.findViewById(R.id.etNameScene);

        tvCountTool = (TextView) rootView.findViewById(R.id.tvCountTool);
        cbAddScene = (CheckBox) rootView.findViewById(R.id.cbAddScene);
        if(!(rootView instanceof EditText)) {

            rootView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }

            });
        }
//        SharedPreferences pref = getContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();

//        String jsonRead = pref.getString("json", null);
//        buttonItemCollectionCms = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
        mRootRef.child("listTool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                buttonItemCollectionCms = dataSnapshot.getValue(ButtonItemCollectionCms.class);
                listAdapter = new AddSceneAdapter(buttonItemCollectionCms, getActivity());
                listAdapter.setButtonItemCollectionCms(buttonItemCollectionCms);
                tvCountTool.setText("All Tool" + "(" + listAdapter.getCount() + ")");
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listAdapter = new AddSceneAdapter(buttonItemCollectionCms, getActivity());
        listAdapter.setButtonItemCollectionCms(buttonItemCollectionCms);
        tvCountTool.setText("All Tool" + "(" + listAdapter.getCount() + ")");
        listView.setAdapter(listAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                listAdapter.checkBoxClicked(position);
//            }
//        });



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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_scene, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionNext) {

            ButtonItemCollectionCms chooseTool = new ButtonItemCollectionCms();
            ArrayList<Boolean> checkOnOrOff = listAdapter.getCheckOnOrOff();
            for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
                if (checkOnOrOff.get(i) == true) {
                    buttonItemCollectionCms.getData().get(i).setstatus("On");
                } else {
                    buttonItemCollectionCms.getData().get(i).setstatus("Off");
                }

            }
            ArrayList<Boolean> checkAdd = listAdapter.getCheckAdd();
            for (int i = 0; i < buttonItemCollectionCms.getData().size(); i++) {
                if (checkAdd.get(i) == true) {
                    chooseTool.addData(buttonItemCollectionCms.getData().get(i));
                }
            }

//            SharedPreferences prefKey = getContext().getSharedPreferences("keyScene", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editorKey = prefKey.edit();
//            String jsonRead = prefKey.getString("json", null);
//            int id = new Gson().fromJson(jsonRead, Integer.class);
//
//            id = id + 1;
//            chooseTool.setId(id);
            DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
            String key = mRootRef.push().getKey();
            chooseTool.setId(key);


            chooseTool.setName(etNameScene.getText().toString());


//            String json = new Gson().toJson(String.valueOf(id));
//            editorKey.putString("json", json);
//            editorKey.apply();
            getFragmentManager().beginTransaction().replace(R.id.contentContainer, SetTimeOrSensorFragment.newInstance(chooseTool)).commit();


        }
        return super.onOptionsItemSelected(item);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
