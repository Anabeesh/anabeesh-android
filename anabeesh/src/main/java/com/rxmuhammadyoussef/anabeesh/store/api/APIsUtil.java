package com.rxmuhammadyoussef.anabeesh.store.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.core.schedulers.NetworkThreadSchedulers;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ApplicationScope
public class APIsUtil {

    public static final String BASE_URL = "http://anabeesh2-001-site1.itempurl.com/";
    private static Retrofit anabeeshRetrofit;
    private static Retrofit anabeeshRxRetrofit;

    @Inject
    APIsUtil() {
        //No extra logic needed
    }

    public AnabeeshAPIService getAnabeeshAPIService() {
        return getAnabeeshClient().create(AnabeeshAPIService.class);
    }

    private Retrofit getAnabeeshClient() {
        if (anabeeshRetrofit == null) {
            anabeeshRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return anabeeshRetrofit;
    }

    public AnabeeshRxAPIService getAnabeeshRxAPIService() {
        return getAnabeeshRxClient().create(AnabeeshRxAPIService.class);
    }

    private Retrofit getAnabeeshRxClient() {
        if (anabeeshRxRetrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            anabeeshRxRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(new NetworkThreadSchedulers().workerThread()))
                    .build();
        }
        return anabeeshRxRetrofit;
    }
}
