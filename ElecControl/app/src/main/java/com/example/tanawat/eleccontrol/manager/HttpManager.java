package com.example.tanawat.eleccontrol.manager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.tanawat.eleccontrol.manager.http.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HttpManager {
static String url = "http://158.108.122.48:5000/";

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        HttpManager.url = url;
    }

    public static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private Context mContext;
    private ApiService service;

    public HttpManager() {
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




    public  ApiService getService(){
        return service;
    }

}
