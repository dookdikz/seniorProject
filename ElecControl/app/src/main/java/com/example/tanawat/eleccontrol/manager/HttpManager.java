package com.example.tanawat.eleccontrol.manager;

import android.content.Context;

import com.example.tanawat.eleccontrol.manager.http.ApiService;
import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance() {
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private Context mContext;
    private ApiService service;
    private String baseUrl = "192.168.1.247";
    private HttpManager() {
        mContext = Contextor.getInstance().getContext();
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("http://"+baseUrl+":5000/").build();
        service = retrofit.create(ApiService.class);
    }


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public  ApiService getService(){
        return service;
    }

}
