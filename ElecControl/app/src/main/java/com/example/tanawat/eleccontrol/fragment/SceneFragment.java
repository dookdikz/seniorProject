package com.example.tanawat.eleccontrol.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.adapter.ButtonListAdapter;
import com.example.tanawat.eleccontrol.adapter.SceneListAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SceneFragment extends Fragment {


    public interface FragmentListener {
        void onAddSceneButtonClicked();
    }

    static ListView listView;
    static TextView tvCountScene;
    static SceneListAdapter listAdapter;
    ButtonItemCollectionCms buttonItemCollectionCms;
    Button btnGoTool;
    ListScene listScene;
    ButtonItemCollectionCms scene;

    public SceneFragment() {
        super();
    }

    public static SceneFragment newInstance(ButtonItemCollectionCms scene ) {
        SceneFragment fragment = new SceneFragment();
        Bundle args = new Bundle();
        args.putParcelable("scene", scene);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
getActivity().setTitle("Scene");
        scene = getArguments().getParcelable("scene");
        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scene, container, false);

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
        tvCountScene = (TextView) rootView.findViewById(R.id.tvCountScene);

        btnGoTool = (Button) rootView.findViewById(R.id.btnGoTool);
        btnGoTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer, MainFragment.newInstance(null)).commit();
            }
        });

        ButtonItemCms buttonItemCms1 = new ButtonItemCms();


        buttonItemCms1.setName("Control Air");
        buttonItemCms1.setstatus("Off");
        buttonItemCms1.setType("Air");

        ButtonItemCms buttonItemCms2 = new ButtonItemCms();
        buttonItemCms2.setName("Control TV");
        buttonItemCms2.setstatus("Off");
        buttonItemCms2.setType("TV");
        List<ButtonItemCms> listCmsTest =new ArrayList<>();
        listCmsTest.add(buttonItemCms1);
        listCmsTest.add(buttonItemCms2);


        buttonItemCollectionCms = new ButtonItemCollectionCms();
buttonItemCollectionCms.addData(buttonItemCms1);
        buttonItemCollectionCms.addData(buttonItemCms2);

        buttonItemCollectionCms.setName("Bedroom");
        List<ButtonItemCollectionCms> listSceneTest =new ArrayList<>();
        listSceneTest.add(buttonItemCollectionCms);

        SharedPreferences pref = getContext().getSharedPreferences("listScene", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String jsonRead = pref.getString("json", null);
        listScene = new Gson().fromJson(jsonRead, ListScene.class);

        if (listScene != null ) {

            if (scene != null) {

                listScene.addData(scene);
            }
        } else {
            SharedPreferences prefKey = getContext().getSharedPreferences("keyScene", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorKey = prefKey.edit();
            int id=1;
            String json = new Gson().toJson(id);
            editorKey.putString("json", json);
            editorKey.apply();
            listScene = new ListScene();


        }



        listAdapter = new SceneListAdapter(listScene, getActivity());
        listAdapter.setListScene(listScene);
        //tvCountScene.setText("All Tool" + "(" + listAdapter.getCount() + ")");
        listView.setAdapter(listAdapter);
        tvCountScene.setText("All Scene" + "(" + listAdapter.getCount() + ")");
//        btnDelete = (Button)getView().findViewById(R.id.btnDeleted);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ButtonItemCollectionCms allTool ;
                SharedPreferences pref = getContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                String jsonRead = pref.getString("json", null);
                allTool = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
                for(int i=0;i<listScene.getData().get(position).getData().size();i++){
                    Toast.makeText(getContext(),listScene.getData().get(position).getData().get(i).getName()+" "+listScene.getData().get(position).getData().get(i).getstatus()+"+"+listScene.getData().get(position).getName()+"+"+String.valueOf(listScene.getData().get(position).getId()+","+listScene.getData().get(position).getTime()),Toast.LENGTH_SHORT).show();
                    int idToolInScene = listScene.getData().get(position).getData().get(i).getId();
                    if(allTool!=null){
                        for(int j=0;j<allTool.getData().size();j++){
                            int idTool = allTool.getData().get(j).getId();
                            if(idTool == idToolInScene){
                                allTool.getData().get(j).setstatus(listScene.getData().get(position).getData().get(i).getstatus());
                            }
                        }
                    }

                }
                String json = new Gson().toJson(allTool);
                editor.putString("json", json);
                editor.apply();


//                if (buttonItemCollectionCms.getData().get(position).getName().equals("Control Light")) {
////                    Toast.makeText(getContext(), "Open", Toast.LENGTH_SHORT).show();
//                   Call<TestSendWeb> call = HttpManager.getInstance().getService().openLight();
//                    call.enqueue(new Callback<TestSendWeb>() {
//                        @Override
//                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
//                            Toast.makeText(getContext(),"Suscess OpenLight", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
//                            Toast.makeText(getContext(),t.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else if (buttonItemCollectionCms.getData().get(position).getName().equals("CloseLight")) {
////                    Toast.makeText(getContext(), "Close", Toast.LENGTH_SHORT).show();
//                   Call<TestSendWeb> call = HttpManager.getInstance().getService().closeLight();
//                    call.enqueue(new Callback<TestSendWeb>() {
//                        @Override
//                        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
//                            Toast.makeText(getContext(),"Suscess CloseLight", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<TestSendWeb> call, Throwable t) {
//                            Toast.makeText(getContext(),t.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        listAdapter.notifyDataSetChanged();
        String json = new Gson().toJson(listScene);
        editor.putString("json", json);
        editor.apply();


    }
    public static void update() {

        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        tvCountScene.setText("All Tool" + "(" + listAdapter.getCount() + ")");

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
        inflater.inflate(R.menu.menu_add_command, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionAdd) {
            FragmentListener listener = (FragmentListener) getActivity();
            listener.onAddSceneButtonClicked();


        }
        else if (item.getItemId() == R.id.actionSetting){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialog");
            if (prev != null) {
                ft.remove(prev);
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            DialogFragment newFragment = SettingDialogFragment.newInstance(2);
            newFragment.show(ft, "dialog");
        }
        return super.onOptionsItemSelected(item);
    }
}
