package com.example.tanawat.eleccontrol.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.adapter.ButtonListAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.manager.ButtonItemManager;
import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {
    public interface FragmentListener{
        void onAddButtonClicked();
    }
Button btnCommand;
    ButtonItemManager buttonListManager;
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
        setHasOptionsMenu(true);

    }



    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        listView = (ListView) rootView.findViewById(R.id.listView);
        listAdapter = new ButtonListAdapter();

        ButtonItemCms buttonItemCms1 = new ButtonItemCms();
        buttonItemCms1.setId(1);
        buttonItemCms1.setName("Control Light");
        buttonItemCms1.setRoomName("Bed");
        buttonItemCms1.setType("Light");
        ButtonItemCms buttonItemCms2 = new ButtonItemCms();
        buttonItemCms2.setId(2);
        buttonItemCms2.setName("Control Air");
        buttonItemCms2.setRoomName("Bed2");
        buttonItemCms2.setType("Air");
        List<ButtonItemCms> listCms = new ArrayList<ButtonItemCms>();
        listCms.add(buttonItemCms1);
        listCms.add(buttonItemCms2);


        ButtonItemCollectionCms buttonItemCollectionCms = new ButtonItemCollectionCms(listCms);
        String json = new Gson().toJson(buttonItemCollectionCms);
        SharedPreferences pref = getContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("json",json);
        editor.apply();
        String jsonRead = pref.getString("json",null);
        buttonItemCollectionCms = new Gson().fromJson(jsonRead,ButtonItemCollectionCms.class);
        listAdapter.setButtonItemCollectionCms(buttonItemCollectionCms);







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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_add_command,menu);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId()==R.id.actionAdd){
                FragmentListener listener = (FragmentListener) getActivity();
                listener.onAddButtonClicked();




            }
        return super.onOptionsItemSelected(item);
    }
}
