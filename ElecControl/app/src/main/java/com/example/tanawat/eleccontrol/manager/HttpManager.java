package com.example.tanawat.eleccontrol.manager;

import android.content.Context;
import android.util.Log;

import com.example.tanawat.eleccontrol.manager.http.ApiService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HttpManager {
    static public String url = "http://160.118.122.70:5000/";
    static public String mUser;


    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        HttpManager.url = url;
        Log.d("testSetIps",url);
    }

    public static HttpManager instance;
    public static HttpManager getInstance() {
        if(instance==null){
            instance = new HttpManager();
        }
        return instance;
    }

    private Context mContext;
    private ApiService service;

    public HttpManager() {
        final DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child(mUser+"/ip").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    url = dataSnapshot.getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mContext = Contextor.getInstance().getContext();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://google.com")
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiService.class);


    }


    public ApiService getService() {
        return service;
    }

}
