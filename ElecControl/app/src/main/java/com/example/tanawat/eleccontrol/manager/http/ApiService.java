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
    @GET("on_tv")
    Call<TestSendWeb> openTv();
    @GET("off_tv")
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

    @GET("temp_18")
    Call<TestSendWeb> temp18();
    @GET("temp_19")
    Call<TestSendWeb> temp19();
    @GET("temp_20")
    Call<TestSendWeb> temp20();
    @GET("temp_21")
    Call<TestSendWeb> temp21();
    @GET("temp_22")
    Call<TestSendWeb> temp22();
    @GET("temp_23")
    Call<TestSendWeb> temp23();
    @GET("temp_24")
    Call<TestSendWeb> temp24();
    @GET("temp_25")
    Call<TestSendWeb> temp25();
    @GET("temp_26")
    Call<TestSendWeb> temp26();
    @GET("temp_27")
    Call<TestSendWeb> temp27();
    @GET("temp_28")
    Call<TestSendWeb> temp28();

    @GET("tv_1")
    Call<TestSendWeb> tv1();
    @GET("tv_2")
    Call<TestSendWeb> tv2();
    @GET("tv_3")
    Call<TestSendWeb> tv3();
    @GET("tv_4")
    Call<TestSendWeb> tv4();
    @GET("tv_5")
    Call<TestSendWeb> tv5();
    @GET("tv_6")
    Call<TestSendWeb> tv6();
    @GET("tv_7")
    Call<TestSendWeb> tv7();
    @GET("tv_8")
    Call<TestSendWeb> tv8();
    @GET("tv_9")
    Call<TestSendWeb> tv9();
    @GET("tv_0")
    Call<TestSendWeb> tv0();

}
