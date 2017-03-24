package com.example.tanawat.eleccontrol.fragment;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.adapter.SceneListAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.example.tanawat.eleccontrol.cms.TestSendWeb;
import com.example.tanawat.eleccontrol.manager.HttpManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    ImageView btnGoTool;
    ListScene listScene;
    ButtonItemCollectionCms scene;
    static String url;
    ButtonItemCms toolInScene;
    ButtonItemCollectionCms allTool;
    ProgressBar pgbLoad;
    LinearLayout layoutListView;

    public static void setUrl(String url) {
        SceneFragment.url = url;
    }

    public SceneFragment() {
        super();
    }

    public static SceneFragment newInstance(ButtonItemCollectionCms scene) {
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
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) rootView.findViewById(R.id.listView);
        tvCountScene = (TextView) rootView.findViewById(R.id.tvCountScene);
        pgbLoad = (ProgressBar) rootView.findViewById(R.id.pgbLoad);

        layoutListView = (LinearLayout) rootView.findViewById(R.id.layoutListView);

        pgbLoad.setVisibility(View.VISIBLE);
        layoutListView.setVisibility(View.GONE);
        btnGoTool = (ImageView) rootView.findViewById(R.id.btnGoTool);
        btnGoTool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer, MainFragment.newInstance(null)).commit();
            }
        });

        ButtonItemCms buttonItemCms1 = new ButtonItemCms();


//        buttonItemCms1.setName("Control Air");
//        buttonItemCms1.setstatus("Off");
//        buttonItemCms1.setType("Air");
//
//        ButtonItemCms buttonItemCms2 = new ButtonItemCms();
//        buttonItemCms2.setName("Control TV");
//        buttonItemCms2.setstatus("Off");
//        buttonItemCms2.setType("TV");
//        List<ButtonItemCms> listCmsTest = new ArrayList<>();
//        listCmsTest.add(buttonItemCms1);
//        listCmsTest.add(buttonItemCms2);


//        buttonItemCollectionCms = new ButtonItemCollectionCms();
//        buttonItemCollectionCms.addData(buttonItemCms1);
//        buttonItemCollectionCms.addData(buttonItemCms2);

//        buttonItemCollectionCms.setName("Bedroom");
//        List<ButtonItemCollectionCms> listSceneTest = new ArrayList<>();
//        listSceneTest.add(buttonItemCollectionCms);

        mRootRef.child("listScene").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    listScene = dataSnapshot.getValue(ListScene.class);

                }
                listAdapter = new SceneListAdapter(listScene, getActivity());
                listAdapter.setListScene(listScene);
                tvCountScene.setText("All Scene" + "(" + listAdapter.getCount() + ")");
                listView.setAdapter(listAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mRootRef.child("listTool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allTool = dataSnapshot.getValue(ButtonItemCollectionCms.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        SharedPreferences pref = getContext().getSharedPreferences("listScene", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        String jsonRead = pref.getString("json", null);
//        listScene = new Gson().fromJson(jsonRead, ListScene.class);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listScene != null) {

                    if (scene != null) {
                        Log.d("eiei", "eieiei");
                        listScene.addData(scene);
                        mRootRef.child("listScene").setValue(listScene);
                    }
                } else {
//            SharedPreferences prefKey = getContext().getSharedPreferences("keyScene", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editorKey = prefKey.edit();
//            int id = 1;
//            String json = new Gson().toJson(id);
//            editorKey.putString("json", json);
//            editorKey.apply();
                    listScene = new ListScene();
                    if (scene != null) {
                        Log.d("eiei", "eieiei");
                        listScene.addData(scene);
                        mRootRef.child("listScene").setValue(listScene);

                    }
                }
                pgbLoad.setVisibility(View.GONE);
                layoutListView.setVisibility(View.VISIBLE);
            }
        }, 2000);


        listAdapter = new SceneListAdapter(listScene, getActivity());
        listAdapter.setListScene(listScene);
        //tvCountScene.setText("All Tool" + "(" + listAdapter.getCount() + ")");
        tvCountScene.setText("All Scene" + "(" + listAdapter.getCount() + ")");
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                HttpManager.setUrl(url);
                for (int i = 0; i < listScene.getData().get(position).getData().size(); i++) {
                    Log.d("checkI", "eiei");
                    toolInScene = listScene.getData().get(position).getData().get(i);
                    if (toolInScene.getType().equals("Air")) {
                        if (toolInScene.getstatus().equals("On")) {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().openAir();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        } else {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().closeAir();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        }
                    } else if (toolInScene.getType().equals("Tv")) {
                        if (toolInScene.getstatus().equals("On")) {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().openTv();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        } else {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().closeTv();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        }
                    } else if (toolInScene.getType().equals("Switch1")) {
                        if (toolInScene.getstatus().equals("On")) {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch1();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        } else {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch1();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        }
                    } else if (toolInScene.getType().equals("Switch2")) {
                        if (toolInScene.getstatus().equals("On")) {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch2();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        } else {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch2();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        }
                    } else if (toolInScene.getType().equals("Curtain")) {
                        if (toolInScene.getstatus().equals("On")) {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().openCurtain();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        } else {
                            Call<TestSendWeb> call = HttpManager.getInstance().getService().closeCurtain();
                            call.enqueue(new SentToServer(SentToServer.MODE_CLOSE_AIR));
                        }
                    } else {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                }

////                SharedPreferences pref = getContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
////                SharedPreferences.Editor editor = pref.edit();
////                String jsonRead = pref.getString("json", null);
////                allTool = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
//
                for (int i = 0; i < listScene.getData().get(position).getData().size(); i++) {
                    Toast.makeText(getContext(), listScene.getData().get(position).getData().get(i).getName() + " " + listScene.getData().get(position).getData().get(i).getstatus() + "+" + listScene.getData().get(position).getName() + "+" + String.valueOf(listScene.getData().get(position).getId() + "," + listScene.getData().get(position).getTime()), Toast.LENGTH_SHORT).show();
                    String idToolInScene = listScene.getData().get(position).getData().get(i).getId();
                    if (allTool != null) {
                        for (int j = 0; j < allTool.getData().size(); j++) {
                            String idTool = allTool.getData().get(j).getId();
                            if (idTool.equals(idToolInScene)) {
                                allTool.getData().get(j).setstatus(listScene.getData().get(position).getData().get(i).getstatus());
                            }
                        }
                    }

                }
////                String json = new Gson().toJson(allTool);
////                editor.putString("json", json);
////                editor.apply();
//
//

                mRootRef.child("listTool").setValue(allTool);

            }

        });


        listAdapter.notifyDataSetChanged();
//        String json = new Gson().toJson(listScene);
//        editor.putString("json", json);
//        editor.apply();


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


        } else if (item.getItemId() == R.id.actionSetting) {
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

    class SentToServer implements Callback<TestSendWeb> {
        public static final int MODE_OPEN_AIR = 1;
        public static final int MODE_CLOSE_AIR = 2;
        public static final int MODE_OPEN_TV = 3;
        public static final int MODE_CLOSE_TV = 4;
        public static final int MODE_OPEN_SWITCH1 = 5;
        public static final int MODE_CLOSE_SWITCH1 = 6;
        public static final int MODE_OPEN_SWITCH2 = 7;
        public static final int MODE_CLOSE_SWITCH2 = 8;
        public static final int MODE_OPEN_CURTAIN = 9;
        public static final int MODE_CLOSE_CURTAIN = 10;
        int mode;

        public SentToServer(int mode) {
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
            Toast.makeText(getContext(), "Suscess + " + toolInScene.getName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<TestSendWeb> call, Throwable t) {
            Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
