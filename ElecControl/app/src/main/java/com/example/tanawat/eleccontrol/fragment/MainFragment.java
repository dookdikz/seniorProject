package com.example.tanawat.eleccontrol.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.adapter.ButtonListAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {
Button btnCommand;
    ListView listView;
    ButtonListAdapter listAdapter;
    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    @SuppressWarnings("UnusedParameters")
    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here

        SharedPreferences pref = getContext().getSharedPreferences("dummy", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("Hello","World");
        editor.apply();
        Toast.makeText(getContext(),pref.getString("Hello",null),Toast.LENGTH_SHORT).show();

    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState
        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new ButtonListAdapter();
        listView.setAdapter(listAdapter);

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
