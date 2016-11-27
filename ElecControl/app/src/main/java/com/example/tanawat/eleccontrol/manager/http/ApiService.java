package com.example.tanawat.eleccontrol.manager.http;

import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Tanawat on 27/11/2559.
 */
public interface ApiService {
    @GET("on_light")
    Call<ButtonItemCollectionCms> openLight();
    @GET("off_light")
    Call<ButtonItemCollectionCms> closeLight();
    @GET("on_air")
    Call<ButtonItemCollectionCms> openAir();
    @GET("off_air")
    Call<ButtonItemCollectionCms> closeAir();
}
