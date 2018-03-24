package com.rxmuhammadyoussef.anabeesh.store.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.core.schedulers.NetworkThreadSchedulers;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ApplicationScope
public class APIsUtil {

    public static final String BASE_URL = "http://anabeesh1-001-site1.atempurl.com/";
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
            anabeeshRxRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(new NetworkThreadSchedulers().workerThread()))
                    .build();
        }
        return anabeeshRxRetrofit;
    }
}
