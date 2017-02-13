package com.example.tanawat.eleccontrol.manager.http;

import com.example.tanawat.eleccontrol.cms.ButtonItemCollectionCms;
import com.example.tanawat.eleccontrol.cms.TestSendWeb;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Tanawat on 27/11/2559.
 */
public interface ApiService {
    @GET("on_light")
    Call<TestSendWeb> openTv();
    @GET("off_light")
    Call<TestSendWeb> closeTv();
    @GET("on_air")
    Call<TestSendWeb> openAir();
    @GET("off_air")
    Call<TestSendWeb> closeAir();
    @GET("on_switch1")
    Call<TestSendWeb> openSwitch1();
    @GET("off_switch1")
    Call<TestSendWeb> closeSwitch1();
    @GET("on_switch2")
    Call<TestSendWeb> openSwitch2();
    @GET("off_switch2")
    Call<TestSendWeb> closeSwitch2();
    @GET("on_curtain")
    Call<TestSendWeb> openCurtain();
    @GET("off_curtain")
    Call<TestSendWeb> closeCurtain();
}
