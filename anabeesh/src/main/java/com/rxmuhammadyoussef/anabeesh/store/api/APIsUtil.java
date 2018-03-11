package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.anabeesh.di.application.ApplicationScope;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ApplicationScope
public class APIsUtil {

    private static final String BASE_URL = "http://anabeesh1-001-site1.atempurl.com/";

    private static Retrofit anabeeshRetrofit;

    @Inject
    APIsUtil() {
    }

    public AnabeeshAPIService getAnabeeshAPIService() {
        return getAnabeeshClient().create(AnabeeshAPIService.class);
    }

    private Retrofit getAnabeeshClient() {
        if (anabeeshRetrofit == null) {
            anabeeshRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return anabeeshRetrofit;
    }

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.MINUTES)
            .connectTimeout(60, TimeUnit.MINUTES)
            .build();
}
