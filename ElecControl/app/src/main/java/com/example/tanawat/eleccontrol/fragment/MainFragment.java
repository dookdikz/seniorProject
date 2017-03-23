package com.example.tanawat.eleccontrol.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.activity.CrawlAlarm;
import com.example.tanawat.eleccontrol.activity.MainActivity;
import com.example.tanawat.eleccontrol.adapter.ButtonListAdapter;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.TestSendWeb;
import com.example.tanawat.eleccontrol.manager.ButtonItemManager;
import com.example.tanawat.eleccontrol.manager.HttpManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {
    public interface FragmentListener {
        void onAddButtonClicked();
    }

    Button btnCommand;
    ButtonItemManager buttonListManager;
    static ListView listView;
    Button btnChangeUrl;
    Button btnDelete;
    static TextView tvCountTool;
    EditText editUrl;
    static ButtonListAdapter listAdapter;
    ButtonItemCms cms;
    ButtonItemCollectionCms buttonItemCollectionCms;
    ProgressBar pgbLoad;
    LinearLayout layoutListView;


    Button btnGoScene;
    static String url = "http://158.108.122.70:5000/";

    public ButtonItemCollectionCms getButtonItemCollectionCms() {
        return buttonItemCollectionCms;
    }

    public void setButtonItemCollectionCms(ButtonItemCollectionCms buttonItemCollectionCms) {
        this.buttonItemCollectionCms = buttonItemCollectionCms;

    }

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance(ButtonItemCms cms) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable("cms", cms);

        fragment.setArguments(args);
        return fragment;
    }

    public static void setUrl(String url) {
        MainFragment.url = url;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        cms = getArguments().getParcelable("cms");
        getActivity().setTitle("Tool");
        if (savedInstanceState != null) {

            onRestoreInstanceState(savedInstanceState);
        }


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
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        listView = (ListView) rootView.findViewById(R.id.listView);
        pgbLoad = (ProgressBar) rootView.findViewById(R.id.pgbLoad);

        layoutListView = (LinearLayout) rootView.findViewById(R.id.layoutListView);

        pgbLoad.setVisibility(View.VISIBLE);
        layoutListView.setVisibility(View.GONE);
        tvCountTool = (TextView) rootView.findViewById(R.id.tvCountTool);
        btnGoScene = (Button) rootView.findViewById(R.id.btnGoScene);
        btnGoScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.contentContainer, SceneFragment.newInstance(null)).commit();
            }
        });
//        editUrl = (EditText) rootView.findViewById(R.id.editUrl);
//        btnChangeUrl = (Button) rootView.findViewById(R.id.btnChangeUrl);

        final ButtonItemCms buttonItemCms1 = new ButtonItemCms();
//        ButtonItemCms buttonItemCms2 = new ButtonItemCms();

        buttonItemCms1.setName("Control Air");
        buttonItemCms1.setstatus("Off");
        buttonItemCms1.setType("Air");
//        buttonItemCms2.setId(2);
//        buttonItemCms2.setName("Control Air");
//        buttonItemCms2.setRoomName("Bed2");
//        buttonItemCms2.setType("Air");
        List<ButtonItemCms> listCms = new ArrayList<ButtonItemCms>();
        listCms.add(buttonItemCms1);

//        listCms.add(buttonItemCms2);

        mRootRef.child("numId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    mRootRef.child("numId").setValue(1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        SharedPreferences pref = getContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//
//        String jsonRead = pref.getString("json", null);
//        buttonItemCollectionCms = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);

        mRootRef.child("listTool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    buttonItemCollectionCms = dataSnapshot.getValue(ButtonItemCollectionCms.class);
                }


//                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                    Log.d("aa", childSnapshot.getRef().toString());
//
//
//
//
//                }


                listAdapter = new ButtonListAdapter(buttonItemCollectionCms, getActivity());
                listAdapter.setButtonItemCollectionCms(buttonItemCollectionCms);
                tvCountTool.setText("All Tool" + "(" + listAdapter.getCount() + ")");
                listView.setAdapter(listAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                if (buttonItemCollectionCms != null) {

                    if (cms != null) {

                        buttonItemCollectionCms.addData(cms);
                        mRootRef.child("listTool").setValue(buttonItemCollectionCms);
                    }
                } else {

                    buttonItemCollectionCms = new ButtonItemCollectionCms();
                    if (cms != null) {

                        buttonItemCollectionCms.addData(cms);
                        mRootRef.child("listTool").setValue(buttonItemCollectionCms);
                    }
                }
                pgbLoad.setVisibility(View.GONE);
                layoutListView.setVisibility(View.VISIBLE);
            }
        }, 2000);
//        Log.d("aaa", getButtonItemCollectionCms().getData().get(0).getName());



        listAdapter = new ButtonListAdapter(buttonItemCollectionCms, getActivity());
        listAdapter.setButtonItemCollectionCms(buttonItemCollectionCms);
        tvCountTool.setText("All Tool" + "(" + listAdapter.getCount() + ")");
        listView.setAdapter(listAdapter);

//        btnDelete = (Button)getView().findViewById(R.id.btnDeleted);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


//
//                SharedPreferences pref = getContext().getSharedPreferences("cms", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
                HttpManager.setUrl(url);
                if (buttonItemCollectionCms.getData().get(position).getType().equals("Air")) {
//                    Toast.makeText(getContext(), "Open", Toast.LENGTH_SHORT).show();


                    if (buttonItemCollectionCms.getData().get(position).getstatus().equals("On")) {

                        Call<TestSendWeb> call = HttpManager.getInstance().getService().closeAir();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().openAir();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else if (buttonItemCollectionCms.getData().get(position).getType().equals("Tv")) {
//                    Toast.makeText(getContext(), "Close", Toast.LENGTH_SHORT).show();
                    if (buttonItemCollectionCms.getData().get(position).getstatus().equals("On")) {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().closeTv();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().openTv();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else if (buttonItemCollectionCms.getData().get(position).getType().equals("Switch1")) {
//                    Toast.makeText(getContext(), "Close", Toast.LENGTH_SHORT).show();
                    if (buttonItemCollectionCms.getData().get(position).getstatus().equals("On")) {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch1();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch1();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else if (buttonItemCollectionCms.getData().get(position).getType().equals("Switch2")) {
//                    Toast.makeText(getContext(), "Close", Toast.LENGTH_SHORT).show();
                    if (buttonItemCollectionCms.getData().get(position).getstatus().equals("On")) {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().closeSwitch2();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().openSwitch2();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else if (buttonItemCollectionCms.getData().get(position).getType().equals("Curtain")) {
//                    Toast.makeText(getContext(), "Close", Toast.LENGTH_SHORT).show();
                    if (buttonItemCollectionCms.getData().get(position).getstatus().equals("On")) {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().closeCurtain();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Call<TestSendWeb> call = HttpManager.getInstance().getService().openCurtain();
                        call.enqueue(new Callback<TestSendWeb>() {
                            @Override
                            public void onResponse(Call<TestSendWeb> call, Response<TestSendWeb> response) {
                                Toast.makeText(getContext(), "Suscess + " + buttonItemCollectionCms.getData().get(position).getName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<TestSendWeb> call, Throwable t) {
                                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                }
                if (buttonItemCollectionCms.getData().get(position).getstatus().equals("Off")) {
                    buttonItemCollectionCms.getData().get(position).setstatus("On");
                } else if (buttonItemCollectionCms.getData().get(position).getstatus().equals("On")) {
                    buttonItemCollectionCms.getData().get(position).setstatus("Off");
                }

//                String json = new Gson().toJson(buttonItemCollectionCms);
//                editor.putString("json", json);
//                editor.apply();
//
//                String jsonRead = pref.getString("json", null);
//                buttonItemCollectionCms = new Gson().fromJson(jsonRead, ButtonItemCollectionCms.class);
                mRootRef.child("listTool").setValue(buttonItemCollectionCms);
                listAdapter.setButtonItemCollectionCms(buttonItemCollectionCms);

                Toast.makeText(getContext(), buttonItemCollectionCms.getData().get(position).getName() + " " + buttonItemCollectionCms.getData().get(position).getstatus() + " " + buttonItemCollectionCms.getData().get(position).getId(), Toast.LENGTH_SHORT).show();

                Log.d("count", String.valueOf(listAdapter.getCount()));
                listView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();


            }
        });
        listAdapter.notifyDataSetChanged();
//        String json = new Gson().toJson(buttonItemCollectionCms);
//        editor.putString("json", json);
//        editor.apply();

//        final AlarmManager am=(AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
//        Intent i = new Intent(getContext(), CrawlAlarm.class);
//        final PendingIntent pi = PendingIntent.getBroadcast(getContext(), 0, i, 0);
//        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 5, pi); // Millisec * Second * Minute
//
//        Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                am.cancel(pi);
//            }
//
//        }, 60 * 1000);

    }

    public static void update() {

        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        tvCountTool.setText("All Tool" + "(" + listAdapter.getCount() + ")");

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

        inflater.inflate(R.menu.menu_add_command, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionAdd) {
            FragmentListener listener = (FragmentListener) getActivity();
            listener.onAddButtonClicked();


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
//private class MyListAdap extends ArrayAdapter<String>{
//    private int layout;
//    public MyListAdap(Context context, int resource, List<String> objects) {
//        super(context, resource, objects);
//        layout = resource;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//      ViewHolder mainViewholder = null;
//        if(convertView==null){
//            LayoutInflater  inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(layout,parent,false);
//            ViewHolder viewHolder = new ViewHolder();
//            viewHolder.btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
//            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getContext(),"fdfs", Toast.LENGTH_SHORT).show();
//                }
//            });
//            convertView.setTag(viewHolder);
//        }
//        else {
//            mainViewholder = (ViewHolder) convertView.getTag();
//        }
//
//return convertView;
//    }
//}
//    public class ViewHolder{
//        ImageView btnDelete;
//    }
}
