package com.example.tanawat.eleccontrol.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tanawat.eleccontrol.R;
import com.example.tanawat.eleccontrol.cms.ButtonItemCms;
import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.ListScene;
import com.example.tanawat.eleccontrol.fragment.MainFragment;
import com.example.tanawat.eleccontrol.fragment.SceneFragment;
import com.example.tanawat.eleccontrol.view.ButtonListItem;
import com.google.gson.Gson;

/**
 * Created by Tanawat on 31/1/2560.
 */
public class SceneListAdapter extends BaseAdapter {
    ListScene listScene ;
    Button btnDelete;
    private Context activity;


    public SceneListAdapter(ListScene listScene, Context context) {
        this.listScene = listScene;
        this.activity = context;
    }

    public ListScene getListScene() {
        return listScene;
    }

    public void setListScene(ListScene listScene) {
        this.listScene = listScene;
    }

    @Override
    public int getCount() {
        if(listScene != null){
            return listScene.getData().size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return listScene.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
private class ViewHolder{
    TextView tvNameScene;
}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ButtonListItem item;
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//        if (convertView != null) {
//            item = (ButtonListItem) convertView;
//
//        } else {


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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_scene, null);
            holder = new ViewHolder();
            holder.tvNameScene = (TextView) convertView.findViewById(R.id.tvNameScene);

            convertView.setTag(holder);
            //     item = new ButtonListItem(parent.getContext());
        }


        btnDelete = (Button) convertView.findViewById(R.id.btnDeleted);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder adb=new AlertDialog.Builder(activity);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + listScene.getData().get(position).getName());

                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("listScene", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        String jsonRead = pref.getString("json", null);
                        listScene = new Gson().fromJson(jsonRead, ListScene.class);

                        listScene.deleteData(positionToRemove);

                        String json = new Gson().toJson(listScene);
                        editor.putString("json", json);
                        editor.apply();

                        SceneFragment sceneFragment = new SceneFragment();
                        sceneFragment.update();
                        notifyDataSetChanged();

                    }});
                adb.show();

            }
        });



        ButtonItemCollectionCms buttonItemCollectionCms = (ButtonItemCollectionCms) getItem(position);
        if (buttonItemCollectionCms != null) {
            Log.d("testD",String.valueOf(position));
            if(buttonItemCollectionCms.getName() !=null){
                if(holder!=null){
                    holder.tvNameScene.setText(buttonItemCollectionCms.getName());

                }

            }
            else{

            }
            // item.setTvNameText(buttonItemCms.getName());
            //  item.setTvTypeText(buttonItemCms.getType());

        }

        return convertView;
    }
}
